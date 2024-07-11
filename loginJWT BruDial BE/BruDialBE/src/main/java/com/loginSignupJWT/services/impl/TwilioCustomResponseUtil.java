package com.loginSignupJWT.services.impl;


import com.loginSignupJWT.entities.TwilioCustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class TwilioCustomResponseUtil {
    public static <T> ResponseEntity<TwilioCustomResponse<T>> createResponse(HttpStatus status, T data, String message, String error){
        TwilioCustomResponse<T> response = new TwilioCustomResponse<>();
        response.setStatus(status.getReasonPhrase());
        response.setStatusCode(status.value());
        response.setData(data);
        response.setMessage(message);
        response.setError(error);

        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<TwilioCustomResponse<T>> createSuccessResponse(T data, String message) {
        return createResponse(HttpStatus.OK, data, message, null);
    }

    public static <T> ResponseEntity<TwilioCustomResponse<T>> createErrorResponse(HttpStatus status, String error, String message) {
        return createResponse(status, null, message, error);
    }

}

