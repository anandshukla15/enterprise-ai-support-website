package com.aidesk.user.dto;

import com.aidesk.common.enums.Role;
import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100)
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
