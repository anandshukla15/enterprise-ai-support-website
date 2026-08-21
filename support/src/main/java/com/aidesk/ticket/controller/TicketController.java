package com.aidesk.ticket.controller;


import com.aidesk.common.dto.ApiResponse;
import com.aidesk.ticket.dto.*;
import com.aidesk.ticket.enums.TicketStatus;
import com.aidesk.ticket.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.aidesk.ticket.dto.TicketAssignmentResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'SUPPORT_AGENT')")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TicketResponse>builder()
                        .success(true)
                        .message("Ticket created successfully")
                        .data(ticketService.createTicket(
                                request,
                                authentication
                        ))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getTickets(
            @RequestParam(required = false) TicketStatus status,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<List<TicketResponse>>builder()
                        .success(true)
                        .message("Tickets fetched successfully")
                        .data(ticketService.getTickets(
                                status,
                                authentication
                        ))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicket(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<TicketResponse>builder()
                        .success(true)
                        .message("Ticket fetched successfully")
                        .data(ticketService.getTicket(id, authentication))
                        .build()
        );
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<ApiResponse<TicketResponse>> assignTicket(
            @PathVariable Long id,
            @Valid @RequestBody AssignTicketRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<TicketResponse>builder()
                        .success(true)
                        .message("Ticket assigned successfully")
                        .data(ticketService.assignTicket(
                                id,
                                request,
                                authentication
                        ))
                        .build()
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TicketResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketStatusRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<TicketResponse>builder()
                        .success(true)
                        .message("Ticket status updated successfully")
                        .data(ticketService.updateStatus(
                                id,
                                request,
                                authentication
                        ))
                        .build()
        );
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<ApiResponse<TicketResponse>> updatePriority(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketPriorityRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<TicketResponse>builder()
                        .success(true)
                        .message("Ticket priority updated successfully")
                        .data(ticketService.updatePriority(
                                id,
                                request,
                                authentication
                        ))
                        .build()
        );
    }

    @GetMapping("/{id}/assignments")
    public ResponseEntity<ApiResponse<List<TicketAssignmentResponse>>>
    getAssignmentHistory(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<List<TicketAssignmentResponse>>builder()
                        .success(true)
                        .message("Ticket assignment history fetched successfully")
                        .data(ticketService.getAssignmentHistory(
                                id,
                                authentication
                        ))
                        .build()
        );
    }
}
