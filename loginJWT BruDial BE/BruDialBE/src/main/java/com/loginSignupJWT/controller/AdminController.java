package com.loginSignupJWT.controller;

import com.loginSignupJWT.dto.SignUpRequest;

import com.loginSignupJWT.entities.APICustomResponse;
import com.loginSignupJWT.entities.User;
import com.loginSignupJWT.services.AuthenticationService;

import com.loginSignupJWT.utils.CustomResponseUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<APICustomResponse<User>> adminRegister(@RequestBody SignUpRequest signUpRequest) {

        User user = authenticationService.signup(signUpRequest, "admin");
        return CustomResponseUtil.createSuccessResponse(user, "User registered successfully");
    }
}
