package com.aidesk.ticket.dto;

import java.time.LocalDateTime;

public record TicketAssignmentResponse(
        Long id,
        Long ticketId,
        Long assignedAgentId,
        String assignedAgentName,
        Long assignedByUserId,
        String assignedByUserName,
        LocalDateTime createdAt
) {
}
