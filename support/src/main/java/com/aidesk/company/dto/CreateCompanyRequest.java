package com.aidesk.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCompanyRequest(
        @NotBlank(message = "Company name is required")
        @Size(
                min = 2,
                max = 200,
                message = "Company name must be between 2 and 200 characters"
        )
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email
) {
}
