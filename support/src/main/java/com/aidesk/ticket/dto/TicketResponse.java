package com.aidesk.ticket.dto;


import com.aidesk.ticket.enums.TicketPriority;
import com.aidesk.ticket.enums.TicketStatus;

import java.time.LocalDateTime;
public record TicketResponse(

        Long id,
        Long companyId,
        Long customerId,
        String customerName,
        Long assignedAgentId,
        String assignedAgentName,
        String title,
        String description,
        TicketPriority priority,
        TicketStatus status,
        String category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
