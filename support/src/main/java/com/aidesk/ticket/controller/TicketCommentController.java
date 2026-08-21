package com.aidesk.ticket.controller;


import com.aidesk.common.dto.ApiResponse;
import com.aidesk.ticket.dto.CreateTicketCommentRequest;
import com.aidesk.ticket.dto.TicketCommentResponse;
import com.aidesk.ticket.service.TicketCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'SUPPORT_AGENT')")
public class TicketCommentController {

    private final TicketCommentService ticketCommentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TicketCommentResponse>> createComment(
            @PathVariable Long ticketId,
            @Valid @RequestBody CreateTicketCommentRequest request,
            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TicketCommentResponse>builder()
                        .success(true)
                        .message("Ticket comment created successfully")
                        .data(ticketCommentService.createComment(
                                ticketId,
                                request,
                                authentication
                        ))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketCommentResponse>>> getComments(
            @PathVariable Long ticketId,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<List<TicketCommentResponse>>builder()
                        .success(true)
                        .message("Ticket comments fetched successfully")
                        .data(ticketCommentService.getComments(
                                ticketId,
                                authentication
                        ))
                        .build()
        );
    }
}
