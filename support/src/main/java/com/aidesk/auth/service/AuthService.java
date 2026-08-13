package com.aidesk.auth.service;

import org.springframework.stereotype.Service;


import com.aidesk.auth.dto.AuthResponse;
import com.aidesk.auth.dto.LoginRequest;
import com.aidesk.auth.dto.RefreshTokenRequest;
import com.aidesk.auth.dto.RegisterRequest;
import com.aidesk.auth.entity.RefreshToken;
import com.aidesk.auth.repository.RefreshTokenRepository;
import com.aidesk.common.enums.Role;
import com.aidesk.company.entity.Company;
import com.aidesk.company.repository.CompanyRepository;
import com.aidesk.exception.custom.DuplicateResourceException;
import com.aidesk.exception.custom.ResourceNotFoundException;
import com.aidesk.security.jwt.JwtService;
import com.aidesk.user.entity.User;
import com.aidesk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService  {


    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {

            throw new DuplicateResourceException(
                    "User with this email already exists"
            );
        }

        if (companyRepository.existsByEmail(
                request.companyEmail())) {

            throw new DuplicateResourceException(
                    "Company with this email already exists"
            );
        }

        Company company = new Company();

        company.setName(request.companyName());
        company.setEmail(request.companyEmail());
        company.setStatus("ACTIVE");

        Company savedCompany =
                companyRepository.save(company);

        User user = new User();

        user.setCompany(savedCompany);
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setRole(Role.COMPANY_ADMIN);
        user.setEnabled(true);

        User savedUser =
                userRepository.save(user);

        return createAuthResponse(savedUser);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid email or password"
                        )
                );

        if (!user.isEnabled()) {

            throw new IllegalStateException(
                    "User account is disabled"
            );
        }

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new ResourceNotFoundException(
                    "Invalid email or password"
            );
        }

        return createAuthResponse(user);
    }

    @Transactional
    public AuthResponse refresh(
            RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(request.refreshToken())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Invalid refresh token"
                                )
                        );

        if (refreshToken.isRevoked()) {

            throw new ResourceNotFoundException(
                    "Refresh token has been revoked"
            );
        }

        if (refreshToken.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            refreshToken.setRevoked(true);

            throw new ResourceNotFoundException(
                    "Refresh token has expired"
            );
        }

        User user = refreshToken.getUser();

        refreshToken.setRevoked(true);

        return createAuthResponse(user);
    }

    @Transactional
    public void logout(String refreshTokenValue) {

        refreshTokenRepository
                .findByToken(refreshTokenValue)
                .ifPresent(token -> {

                    token.setRevoked(true);

                    refreshTokenRepository.save(token);
                });
    }

    private AuthResponse createAuthResponse(User user) {

        String accessToken =
                jwtService.generateToken(user);

        String refreshTokenValue =
                UUID.randomUUID().toString();

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiresAt(
                LocalDateTime.now()
                        .plus(java.time.Duration.ofMillis(refreshExpiration))
        );
        refreshToken.setCreatedAt(
                LocalDateTime.now()
        );

        refreshTokenRepository.save(refreshToken);

        Long companyId =
                user.getCompany() != null
                        ? user.getCompany().getId()
                        : null;

        return new AuthResponse(
                accessToken,
                refreshTokenValue,
                "Bearer",
                900000,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                companyId
        );
    }
}
