package com.aidesk.common.controller;

import com.aidesk.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ApiResponse<String> health() {

        return ApiResponse.<String>builder()
                .success(true)
                .message("Backend Running")
                .data("AIDesk Backend Started Successfully")
                .build();
    }
}
