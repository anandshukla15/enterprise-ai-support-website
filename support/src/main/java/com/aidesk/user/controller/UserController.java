package com.aidesk.user.controller;


import com.aidesk.common.dto.ApiResponse;
import com.aidesk.common.enums.Role;
import com.aidesk.user.dto.CreateUserRequest;
import com.aidesk.user.dto.UserResponse;
import com.aidesk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/company/{companyId}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @PathVariable Long companyId,
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse response =
                userService.createUser(
                        companyId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<UserResponse>builder()
                                .success(true)
                                .message("User created successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>>
    getCompanyUsers(
            @PathVariable Long companyId) {

        List<UserResponse> users =
                userService.getCompanyUsers(companyId);

        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Users fetched successfully")
                        .data(users)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>>
    getUser(@PathVariable Long id) {

        UserResponse response =
                userService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>>
    updateRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        UserResponse response =
                userService.updateUserRole(id, role);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User role updated successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>>
    updateStatus(
            @PathVariable Long id,
            @RequestParam boolean enabled) {

        UserResponse response =
                userService.updateUserStatus(
                        id,
                        enabled
                );

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User status updated successfully")
                        .data(response)
                        .build()
        );
    }

}
