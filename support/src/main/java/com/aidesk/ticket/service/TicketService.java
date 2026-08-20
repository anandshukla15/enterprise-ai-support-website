package com.aidesk.ticket.service;


import com.aidesk.common.enums.Role;
import com.aidesk.company.entity.Company;
import com.aidesk.customer.entity.Customer;
import com.aidesk.customer.repository.CustomerRepository;
import com.aidesk.exception.custom.BadRequestException;
import com.aidesk.exception.custom.ResourceNotFoundException;
import com.aidesk.security.service.CurrentUserService;
import com.aidesk.ticket.dto.*;
import com.aidesk.ticket.entity.Ticket;
import com.aidesk.ticket.enums.TicketPriority;
import com.aidesk.ticket.enums.TicketStatus;
import com.aidesk.ticket.mapper.TicketMapper;
import com.aidesk.ticket.repository.TicketRepository;
import com.aidesk.user.entity.User;
import com.aidesk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public TicketResponse createTicket(
            CreateTicketRequest request,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        Customer customer = customerRepository
                .findByIdAndCompanyId(request.customerId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                ));

        Ticket ticket = new Ticket();
        ticket.setCompany(company);
        ticket.setCustomer(customer);
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setCategory(request.category());
        ticket.setPriority(request.priority() == null
                ? TicketPriority.MEDIUM
                : request.priority());
        ticket.setStatus(TicketStatus.OPEN);

        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getTickets(
            TicketStatus status,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        List<Ticket> tickets = status == null
                ? ticketRepository.findByCompanyId(company.getId())
                : ticketRepository.findByCompanyIdAndStatus(
                company.getId(),
                status
        );

        return tickets.stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicket(
            Long ticketId,
            Authentication authentication) {

        return ticketMapper.toResponse(
                getTicketForCurrentCompany(ticketId, authentication)
        );
    }

    @Transactional
    public TicketResponse assignTicket(
            Long ticketId,
            AssignTicketRequest request,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        Ticket ticket = getTicketForCurrentCompany(
                ticketId,
                authentication
        );

        User agent = userRepository.findById(request.agentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Support agent not found"
                ));

        if (agent.getCompany() == null
                || !agent.getCompany().getId().equals(company.getId())
                || agent.getRole() != Role.SUPPORT_AGENT
                || !agent.isEnabled()) {

            throw new BadRequestException(
                    "Selected user is not an active support agent in your company"
            );
        }

        ticket.setAssignedAgent(agent);

        if (ticket.getStatus() == TicketStatus.OPEN) {
            ticket.setStatus(TicketStatus.IN_PROGRESS);
        }

        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketResponse updateStatus(
            Long ticketId,
            UpdateTicketStatusRequest request,
            Authentication authentication) {

        Ticket ticket = getTicketForCurrentCompany(
                ticketId,
                authentication
        );

        ticket.setStatus(request.status());

        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketResponse updatePriority(
            Long ticketId,
            UpdateTicketPriorityRequest request,
            Authentication authentication) {

        Ticket ticket = getTicketForCurrentCompany(
                ticketId,
                authentication
        );

        ticket.setPriority(request.priority());

        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    private Ticket getTicketForCurrentCompany(
            Long ticketId,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        return ticketRepository.findByIdAndCompanyId(
                        ticketId,
                        company.getId()
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found"
                ));
    }

    private Company getCurrentCompany(Authentication authentication) {
        User user = currentUserService.getCurrentUser(authentication);

        if (user.getCompany() == null) {
            throw new ResourceNotFoundException(
                    "Authenticated user is not assigned to a company"
            );
        }

        return user.getCompany();
    }
}
