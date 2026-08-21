package com.aidesk.customer.dto;

import jakarta.validation.constraints.NotNull;

public record LinkCustomerUserRequest(
        @NotNull(message = "User id is required")
        Long userId
) {
}
