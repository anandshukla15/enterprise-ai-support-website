package com.aidesk.company.controller;


import com.aidesk.common.dto.ApiResponse;
import com.aidesk.company.dto.CompanyResponse;
import com.aidesk.company.dto.CreateCompanyRequest;
import com.aidesk.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
            @Valid @RequestBody CreateCompanyRequest request) {

        CompanyResponse response =
                companyService.createCompany(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Company created successfully")
                        .data(response)
                        .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompany(
            @PathVariable Long id) {

        CompanyResponse response =
                companyService.getCompanyById(id);

        return ResponseEntity.ok(
                ApiResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Company fetched successfully")
                        .data(response)
                        .build()
        );
    }



    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getAllCompanies() {

        List<CompanyResponse> companies =
                companyService.getAllCompanies();

        return ResponseEntity.ok(
                ApiResponse.<List<CompanyResponse>>builder()
                        .success(true)
                        .message("Companies fetched successfully")
                        .data(companies)
                        .build()
        );
    }
}
