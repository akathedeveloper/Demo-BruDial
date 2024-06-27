package com.loginSignupJWT.controller;

import com.loginSignupJWT.dto.JwtAuthenticationResponse;
import com.loginSignupJWT.dto.RefreshTokenRequest;
import com.loginSignupJWT.dto.SigninRequest;
import com.loginSignupJWT.entities.CustomResponse;
import com.loginSignupJWT.repository.services.AuthenticationService;
import com.loginSignupJWT.utils.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<CustomResponse<JwtAuthenticationResponse>> login(@RequestBody SigninRequest signinRequest) {
        try {
            JwtAuthenticationResponse jwtResponse = authenticationService.signin(signinRequest);
            return CustomResponseUtil.createTokenResponse(jwtResponse, "Login successful", jwtResponse.getToken());
        } catch (BadCredentialsException e) {
            JwtAuthenticationResponse emptyResponse = new JwtAuthenticationResponse();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomResponse<>("Unauthorized access", emptyResponse));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<CustomResponse<JwtAuthenticationResponse>> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtAuthenticationResponse jwtResponse = authenticationService.refreshToken(refreshTokenRequest);
        return CustomResponseUtil.createTokenResponse(jwtResponse, "Token refreshed successfully", jwtResponse.getToken());
    }
}
