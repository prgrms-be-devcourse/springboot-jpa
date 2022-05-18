package com.example.demo.advice;

import com.example.demo.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {
    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<String> handleException(Exception e) {
        return ApiResponse.fail(e.getMessage());
    }
}
