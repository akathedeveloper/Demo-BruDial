package com.loginSignupJWT.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwilioCustomResponse<T> {
    private String status;
    private int statusCode;
    private T data;
    private String message;
    private String error;
}

