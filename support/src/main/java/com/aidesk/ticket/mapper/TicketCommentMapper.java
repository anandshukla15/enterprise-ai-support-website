package com.aidesk.ticket.mapper;

import com.aidesk.ticket.dto.TicketCommentResponse;
import com.aidesk.ticket.entity.TicketComment;
import org.springframework.stereotype.Component;

@Component
public class TicketCommentMapper {

    public TicketCommentResponse toResponse(TicketComment comment) {
        return new TicketCommentResponse(
                comment.getId(),
                comment.getTicket().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getAuthor().getRole(),
                comment.getContent(),
                comment.isInternalNote(),
                comment.getCreatedAt()
        );
    }
}
