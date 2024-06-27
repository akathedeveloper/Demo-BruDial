package com.loginSignupJWT.controller;

import com.loginSignupJWT.dto.UserDTO;
import com.loginSignupJWT.entities.CustomResponse;
import com.loginSignupJWT.repository.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<CustomResponse<UserDTO>> getUserDetails() {
        // Retrieve authenticated user's email from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Retrieve user details from the authentication service
        UserDTO userDTO = authenticationService.getUserDetails(userEmail);

        // Prepare the response
        CustomResponse<UserDTO> response = new CustomResponse<>();
        response.setData(userDTO);
        response.setMessage("User details retrieved successfully");
        response.setStatus("success");

        return ResponseEntity.ok(response);
    }

}
