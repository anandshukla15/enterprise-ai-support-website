package com.aidesk.company.service;


import com.aidesk.company.dto.CompanyResponse;
import com.aidesk.company.dto.CreateCompanyRequest;
import com.aidesk.company.entity.Company;
import com.aidesk.company.mapper.CompanyMapper;
import com.aidesk.company.repository.CompanyRepository;
import com.aidesk.exception.custom.DuplicateResourceException;
import com.aidesk.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional
    public CompanyResponse createCompany(CreateCompanyRequest request) {

        if (companyRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Company with email already exists"
            );
        }

        Company company = new Company();

        company.setName(request.name());
        company.setEmail(request.email());
        company.setStatus("ACTIVE");

        Company savedCompany = companyRepository.save(company);

        return companyMapper.toResponse(savedCompany);
    }


    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found with id: " + id
                        )
                );

        return companyMapper.toResponse(company);
    }

}
