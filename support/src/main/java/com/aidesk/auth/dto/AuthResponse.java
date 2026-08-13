package com.aidesk.auth.dto;

public record AuthResponse(

        String accessToken,

        String refreshToken,

        String tokenType,

        long expiresIn,

        Long userId,

        String name,

        String email,

        String role,

        Long companyId
) {
}
