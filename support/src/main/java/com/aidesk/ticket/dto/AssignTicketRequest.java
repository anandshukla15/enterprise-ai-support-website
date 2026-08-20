package com.aidesk.ticket.dto;

import jakarta.validation.constraints.NotNull;
public record AssignTicketRequest (

        @NotNull(message = "Agent id is required")
        Long agentId

){
}
