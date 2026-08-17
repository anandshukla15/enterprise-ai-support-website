package com.aidesk.security.service;

import com.aidesk.user.entity.User;
import com.aidesk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantSecurityService {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    public boolean hasAccessToCompany(
            Long companyId,
            Authentication authentication) {

        User user =
                currentUserService.getCurrentUser(authentication);

        if (user.getCompany() == null) {
            return false;
        }

        return user.getCompany()
                .getId()
                .equals(companyId);
    }
}