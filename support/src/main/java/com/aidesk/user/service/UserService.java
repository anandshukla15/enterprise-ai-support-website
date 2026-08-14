package com.aidesk.user.service;

import com.aidesk.common.enums.Role;
import com.aidesk.company.entity.Company;
import com.aidesk.company.repository.CompanyRepository;
import com.aidesk.exception.custom.BadRequestException;
import com.aidesk.exception.custom.DuplicateResourceException;
import com.aidesk.exception.custom.ResourceNotFoundException;
import com.aidesk.user.dto.CreateUserRequest;
import com.aidesk.user.dto.UserResponse;
import com.aidesk.user.entity.User;
import com.aidesk.user.mapper.UserMapper;
import com.aidesk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse createUser(
            Long companyId,
            CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {

            throw new DuplicateResourceException(
                    "User with this email already exists"
            );
        }

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"
                                )
                        );

        if (request.role() == Role.SUPER_ADMIN) {

            throw new BadRequestException(
                    "Company administrators cannot create SUPER_ADMIN users"
            );
        }

        User user = new User();

        user.setCompany(company);
        user.setName(request.name());
        user.setEmail(request.email());

        user.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        user.setRole(request.role());
        user.setEnabled(true);

        return userMapper.toResponse(
                userRepository.save(user)
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getCompanyUsers(
            Long companyId) {

        return userRepository
                .findByCompanyId(companyId)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found with id: " + id
                                )
                        );

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateUserRole(
            Long userId,
            Role role) {

        if (role == Role.SUPER_ADMIN) {

            throw new BadRequestException(
                    "Cannot assign SUPER_ADMIN role"
            );
        }

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        user.setRole(role);

        return userMapper.toResponse(
                userRepository.save(user)
        );
    }

    @Transactional
    public UserResponse updateUserStatus(
            Long userId,
            boolean enabled) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        user.setEnabled(enabled);

        return userMapper.toResponse(
                userRepository.save(user)
        );
    }



}
