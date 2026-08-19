package com.aidesk.customer.dto;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        Long companyId,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)  {
}
