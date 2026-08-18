package com.aidesk.user.controller;


import com.aidesk.common.dto.ApiResponse;
import com.aidesk.common.enums.Role;
import com.aidesk.security.service.CurrentUserService;
import com.aidesk.user.dto.CreateUserRequest;
import com.aidesk.user.dto.UserResponse;
import com.aidesk.user.entity.User;
import com.aidesk.user.mapper.UserMapper;
import com.aidesk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final UserMapper userMapper;

    @PostMapping("/company/{companyId}")
    @PreAuthorize(
            "hasRole('COMPANY_ADMIN') && " +
                    "@tenantSecurityService.hasAccessToCompany(#companyId, authentication)"
    )
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
    @PreAuthorize(
            "hasRole('COMPANY_ADMIN') && " +
                    "@tenantSecurityService.hasAccessToCompany(#companyId, authentication)"
    )
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
    @PreAuthorize(
            "hasRole('COMPANY_ADMIN') && " +
                    "@tenantSecurityService.hasAccessToUser(#id, authentication)"
    )
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
    @PreAuthorize(
            "hasRole('COMPANY_ADMIN') && " +
                    "@tenantSecurityService.hasAccessToUser(#id, authentication)"
    )
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
    @PreAuthorize(
            "hasRole('COMPANY_ADMIN') && " +
                    "@tenantSecurityService.hasAccessToCompany(#companyId, authentication)"
    )
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


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            Authentication authentication) {

        User user =
                currentUserService.getCurrentUser(authentication);

        UserResponse response =
                userMapper.toResponse(user);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Current user fetched successfully")
                        .data(response)
                        .build()
        );

    }

}
