package com.aidesk.ticket.dto;

import com.aidesk.common.enums.Role;

import java.time.LocalDateTime;

public record TicketCommentResponse (
        Long id,
        Long ticketId,
        Long authorId,
        String authorName,
        Role authorRole,
        String content,
        boolean internalNote,
        LocalDateTime createdAt
){
}
