package com.aidesk.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest (
        @NotBlank(message = "Customer name is required")
        @Size(max = 150, message = "Customer name must not exceed 150 characters")
        String name,

        @NotBlank(message = "Customer email is required")
        @Email(message = "Customer email must be valid")
        String email,

        @Size(max = 30, message = "Phone must not exceed 30 characters")
        String phone
){
}
