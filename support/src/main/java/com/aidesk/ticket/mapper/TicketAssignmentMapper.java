package com.aidesk.ticket.mapper;

import com.aidesk.ticket.dto.TicketAssignmentResponse;
import com.aidesk.ticket.entity.TicketAssignment;
import org.springframework.stereotype.Component;

@Component
public class TicketAssignmentMapper {

    public TicketAssignmentResponse toResponse(
            TicketAssignment assignment) {

        return new TicketAssignmentResponse(
                assignment.getId(),
                assignment.getTicket().getId(),
                assignment.getAssignedAgent().getId(),
                assignment.getAssignedAgent().getName(),
                assignment.getAssignedBy().getId(),
                assignment.getAssignedBy().getName(),
                assignment.getCreatedAt()
        );
    }
}
