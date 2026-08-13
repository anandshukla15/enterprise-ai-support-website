package com.aidesk.auth.controller;

import com.aidesk.auth.dto.AuthResponse;
import com.aidesk.auth.dto.LoginRequest;
import com.aidesk.auth.dto.RefreshTokenRequest;
import com.aidesk.auth.dto.RegisterRequest;
import com.aidesk.auth.service.AuthService;
import com.aidesk.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response =
                authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<AuthResponse>builder()
                                .success(true)
                                .message("Registration successful")
                                .data(response)
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response =
                authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        AuthResponse response =
                authService.refresh(request);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Token refreshed successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody RefreshTokenRequest request) {

        authService.logout(request.refreshToken());

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Logout successful")
                        .data(null)
                        .build()
        );
    }
}
