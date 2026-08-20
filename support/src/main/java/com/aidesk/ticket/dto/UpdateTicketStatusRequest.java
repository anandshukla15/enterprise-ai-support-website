package com.aidesk.ticket.dto;

import com.aidesk.ticket.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateTicketStatusRequest(
        @NotNull(message = "Ticket status is required")
        TicketStatus status

) {
}
