package com.aidesk.common.controller;

import com.aidesk.common.dto.ApiResponse;
import com.aidesk.company.dto.CreateCompanyRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping
    public ApiResponse<CreateCompanyRequest> test(
            @Valid @RequestBody CreateCompanyRequest request) {

        return ApiResponse.<CreateCompanyRequest>builder()
                .success(true)
                .message("Validation successful")
                .data(request)
                .build();
    }
}
