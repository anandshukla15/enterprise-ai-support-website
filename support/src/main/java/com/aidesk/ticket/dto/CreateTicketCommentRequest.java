package com.aidesk.ticket.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTicketCommentRequest (
        @NotBlank(message = "Comment content is required")
        String content,

        boolean internalNote
){
}
