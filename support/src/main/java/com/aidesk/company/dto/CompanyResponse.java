package com.aidesk.company.dto;

import java.time.LocalDateTime;

public record CompanyResponse (
        Long id,
        String name,
        String email,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
