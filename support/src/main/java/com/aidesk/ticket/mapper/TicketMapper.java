package com.aidesk.ticket.mapper;

import com.aidesk.ticket.dto.TicketResponse;
import com.aidesk.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getCompany().getId(),
                ticket.getCustomer().getId(),
                ticket.getCustomer().getName(),
                ticket.getAssignedAgent() != null
                        ? ticket.getAssignedAgent().getId()
                        : null,
                ticket.getAssignedAgent() != null
                        ? ticket.getAssignedAgent().getName()
                        : null,
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getCategory(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}
