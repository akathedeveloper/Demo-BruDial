package com.loginSignupJWT.utils;

import com.loginSignupJWT.entities.APICustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APICustomResponse<String>> handleAllExceptions(Exception ex, WebRequest request) {
        return CustomResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An unexpected error occurred");
    }
}
