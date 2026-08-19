package com.aidesk.customer.service;

import com.aidesk.customer.mapper.CustomerMapper;
import com.aidesk.customer.repository.CustomerRepository;
import com.aidesk.security.service.CurrentUserService;
import com.aidesk.company.entity.Company;
import com.aidesk.customer.dto.CreateCustomerRequest;
import com.aidesk.customer.dto.CustomerResponse;
import com.aidesk.customer.dto.UpdateCustomerRequest;
import com.aidesk.customer.entity.Customer;
import com.aidesk.exception.custom.DuplicateResourceException;
import com.aidesk.exception.custom.ResourceNotFoundException;
import com.aidesk.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public CustomerResponse createCustomer(
            CreateCustomerRequest request,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        if (customerRepository.existsByCompanyIdAndEmail(
                company.getId(),
                request.email())) {

            throw new DuplicateResourceException(
                    "Customer with this email already exists in your company"
            );
        }

        Customer customer = new Customer();
        customer.setCompany(company);
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        return customerMapper.toResponse(
                customerRepository.save(customer)
        );
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomers(
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        return customerRepository.findByCompanyId(company.getId())
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(
            Long customerId,
            Authentication authentication) {

        return customerMapper.toResponse(
                getCustomerForCurrentCompany(customerId, authentication)
        );
    }

    @Transactional
    public CustomerResponse updateCustomer(
            Long customerId,
            UpdateCustomerRequest request,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        Customer customer = getCustomerForCurrentCompany(
                customerId,
                authentication
        );

        if (!customer.getEmail().equalsIgnoreCase(request.email())
                && customerRepository.existsByCompanyIdAndEmail(
                company.getId(),
                request.email())) {

            throw new DuplicateResourceException(
                    "Customer with this email already exists in your company"
            );
        }

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        return customerMapper.toResponse(
                customerRepository.save(customer)
        );
    }

    private Customer getCustomerForCurrentCompany(
            Long customerId,
            Authentication authentication) {

        Company company = getCurrentCompany(authentication);

        return customerRepository.findByIdAndCompanyId(
                        customerId,
                        company.getId()
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                ));
    }

    private Company getCurrentCompany(Authentication authentication) {
        User user = currentUserService.getCurrentUser(authentication);

        if (user.getCompany() == null) {
            throw new ResourceNotFoundException(
                    "Authenticated user is not assigned to a company"
            );
        }

        return user.getCompany();
    }

}
