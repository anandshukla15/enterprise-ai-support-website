package com.aidesk.customer.controller;

import com.aidesk.common.dto.ApiResponse;
import com.aidesk.customer.dto.CreateCustomerRequest;
import com.aidesk.customer.dto.CustomerResponse;
import com.aidesk.customer.dto.UpdateCustomerRequest;
import com.aidesk.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'SUPPORT_AGENT')")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request,
            Authentication authentication) {

        CustomerResponse response =
                customerService.createCustomer(request, authentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer created successfully")
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getCustomers(
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerResponse>>builder()
                        .success(true)
                        .message("Customers fetched successfully")
                        .data(customerService.getCustomers(authentication))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer fetched successfully")
                        .data(customerService.getCustomer(id, authentication))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer updated successfully")
                        .data(customerService.updateCustomer(
                                id,
                                request,
                                authentication
                        ))
                        .build()
        );
    }
}
