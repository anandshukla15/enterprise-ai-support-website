package com.aidesk.security.handler;



import com.aidesk.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements  AccessDeniedHandler {

    private final JsonMapper jsonMapper;

    public RestAccessDeniedHandler(
            JsonMapper jsonMapper) {

        this.jsonMapper = jsonMapper;
    }



    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {

        response.setStatus(
                HttpServletResponse.SC_FORBIDDEN
        );

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE
        );

        ApiResponse<Void> body =
                ApiResponse.<Void>builder()
                        .success(false)
                        .message("Access denied")
                        .data(null)
                        .build();

        response.getWriter().write(
                jsonMapper.writeValueAsString(body)
        );
    }
}
