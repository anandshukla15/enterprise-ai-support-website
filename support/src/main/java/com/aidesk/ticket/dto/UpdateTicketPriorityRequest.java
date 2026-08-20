package com.aidesk.ticket.dto;

import com.aidesk.ticket.enums.TicketPriority;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketPriorityRequest (
        @NotNull(message = "Ticket priority is required")
        TicketPriority priority

){
}
