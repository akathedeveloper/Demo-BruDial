package com.loginSignupJWT.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APICustomResponse<T> {
    private String status;
    private int statusCode;
    private T data;
    private String message;
    private String error;
    private String token;

    public APICustomResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
