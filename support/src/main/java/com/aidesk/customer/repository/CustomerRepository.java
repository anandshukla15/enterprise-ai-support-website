package com.aidesk.customer.repository;

import com.aidesk.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByCompanyIdAndEmail(Long companyId, String email);

    Optional<Customer> findByIdAndCompanyId(Long id, Long companyId);

    List<Customer> findByCompanyId(Long companyId);
    Optional<Customer> findByUserIdAndCompanyId(
            Long userId,
            Long companyId
    );

    boolean existsByUserId(Long userId);
}
