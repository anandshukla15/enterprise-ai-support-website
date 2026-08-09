package com.aidesk.exception.handler;

import com.aidesk.common.dto.ApiResponse;
import com.aidesk.exception.custom.DuplicateResourceException;
import com.aidesk.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>>handleResourceNotFound(
            ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.<Void>builder()
                        .success(false)
                        .message(exception.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>>handleDuplicateResponse(
            DuplicateResourceException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.<Void>builder()
                        .success(false)
                        .message(exception.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ApiResponse<Map<String,String>>>handleValidation(
            MethodArgumentNotValidException exception
    ){
        Map<String,String>errors=new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error->
                        errors.put(error.getField(),error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Map<String,String>>builder()
                        .success(false)
                        .message("Validation failsed")
                        .data(null).build());

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>>handleGenericException(
            Exception exception
    ){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.<Void>builder()
                        .success(false)
                        .message("Something went wrong")
                        .data(null)
                        .build());
    }


}
