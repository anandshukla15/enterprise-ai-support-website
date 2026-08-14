package com.aidesk.user.mapper;


import com.aidesk.user.dto.UserResponse;
import com.aidesk.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getCompany() != null
                        ? user.getCompany().getId()
                        : null
        );
    }
}
