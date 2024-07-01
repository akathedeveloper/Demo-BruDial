package com.loginSignupJWT.utils;

import com.loginSignupJWT.entities.APICustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponseUtil {
    public static <T> ResponseEntity<APICustomResponse<T>> createResponse(HttpStatus status, T data, String message, String error, String token){
        APICustomResponse<T> response = new APICustomResponse<>();
        response.setStatus(status.getReasonPhrase());
        response.setStatusCode(status.value());
        response.setData(data);
        response.setMessage(message);
        response.setError(error);
        response.setToken(token);

        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<APICustomResponse<T>> createSuccessResponse(T data, String message) {
        return createResponse(HttpStatus.OK, data, message, null, null);
    }

    public static <T> ResponseEntity<APICustomResponse<T>> createErrorResponse(HttpStatus status, String error, String message) {
        return createResponse(status, null, message, error, null);
    }

    public static <T> ResponseEntity<APICustomResponse<T>> createTokenResponse(T data, String message, String accessToken) {
        return createResponse(HttpStatus.OK, data, message, null, accessToken);
    }

}
