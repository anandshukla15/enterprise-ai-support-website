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

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'SUPPORT_AGENT')")
public class TicketController {
}
