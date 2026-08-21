package com.aidesk.ticket.service;



import com.aidesk.company.entity.Company;
import com.aidesk.exception.custom.ResourceNotFoundException;
import com.aidesk.security.service.CurrentUserService;
import com.aidesk.ticket.dto.CreateTicketCommentRequest;
import com.aidesk.ticket.dto.TicketCommentResponse;
import com.aidesk.ticket.entity.Ticket;
import com.aidesk.ticket.entity.TicketComment;
import com.aidesk.ticket.mapper.TicketCommentMapper;
import com.aidesk.ticket.repository.TicketCommentRepository;
import com.aidesk.ticket.repository.TicketRepository;
import com.aidesk.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketCommentService {

    private final TicketRepository ticketRepository;
    private final TicketCommentRepository ticketCommentRepository;
    private final TicketCommentMapper ticketCommentMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public TicketCommentResponse createComment(
            Long ticketId,
            CreateTicketCommentRequest request,
            Authentication authentication) {

        User author = currentUserService.getCurrentUser(authentication);
        Ticket ticket = getTicketForCurrentCompany(ticketId, author);

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setAuthor(author);
        comment.setContent(request.content());
        comment.setInternalNote(request.internalNote());

        return ticketCommentMapper.toResponse(
                ticketCommentRepository.save(comment)
        );
    }

    @Transactional(readOnly = true)
    public List<TicketCommentResponse> getComments(
            Long ticketId,
            Authentication authentication) {

        User user = currentUserService.getCurrentUser(authentication);
        Ticket ticket = getTicketForCurrentCompany(ticketId, user);

        return ticketCommentRepository
                .findByTicketIdOrderByCreatedAtAsc(ticket.getId())
                .stream()
                .map(ticketCommentMapper::toResponse)
                .toList();
    }

    private Ticket getTicketForCurrentCompany(
            Long ticketId,
            User user) {

        Company company = user.getCompany();

        if (company == null) {
            throw new ResourceNotFoundException(
                    "Authenticated user is not assigned to a company"
            );
        }

        return ticketRepository.findByIdAndCompanyId(
                        ticketId,
                        company.getId()
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found"
                ));
    }
}
