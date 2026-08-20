package com.aidesk.ticket.dto;

import com.aidesk.ticket.enums.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotBlank(message = "Ticket title is required")
        @Size(max = 255, message = "Ticket title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Ticket description is required")
        String description,

        TicketPriority priority,

        @Size(max = 100, message = "Category must not exceed 100 characters")
        String category
) {
}
