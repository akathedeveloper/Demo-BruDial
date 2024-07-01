package com.loginSignupJWT.controller;

import com.loginSignupJWT.dto.UserDTO;
import com.loginSignupJWT.entities.APICustomResponse;
import com.loginSignupJWT.entities.User;
import com.loginSignupJWT.repository.UserRepository;
import com.loginSignupJWT.services.AuthenticationService;
import com.loginSignupJWT.services.JWTService;
import com.loginSignupJWT.services.UserService;
import com.loginSignupJWT.utils.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<APICustomResponse<UserDTO>> getUserDetails() {
        // Retrieve authenticated user's email from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Retrieve user details from the authentication service
        UserDTO userDTO = authenticationService.getUserDetails(userEmail);

        // Prepare the response
        APICustomResponse<UserDTO> response = new APICustomResponse<>();
        response.setData(userDTO);
        response.setMessage("User details retrieved successfully");
        response.setStatus("success");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<APICustomResponse<User>> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UserDTO userDTO
    ) {

        String jwtToken = token.substring(7);
        String userEmail = jwtService.extractUserName(jwtToken);
        User existingUser = userRepository.findByEmail(userEmail).orElse(null);

        if (existingUser == null) {
            return CustomResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "User not found", "Please provide correct credentials");
        }

        // Validate current password
        if (!passwordEncoder.matches(userDTO.getCurrentPassword(), existingUser.getPassword())) {
            return CustomResponseUtil.createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid current password", "The provided current password is incorrect");
        }

        try {
            User updatedUser = userService.updateUser(existingUser.getId(), userDTO);
            return CustomResponseUtil.createSuccessResponse(updatedUser, "User updated successfully");
        } catch (Exception e) {
            return CustomResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Exception occurred", "User details cannot be updated");
        }
    }

}
