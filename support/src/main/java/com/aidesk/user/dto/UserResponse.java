package com.aidesk.user.dto;

import com.aidesk.common.enums.Role;

public record UserResponse (
        Long id,

        String name,

        String email,

        Role role,

        boolean enabled,

        Long companyId
){
}
