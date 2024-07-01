package com.loginSignupJWT.repository.services.impl;

import com.loginSignupJWT.dto.*;
import com.loginSignupJWT.entities.Role;
import com.loginSignupJWT.entities.User;
import com.loginSignupJWT.repository.UserRepository;
import com.loginSignupJWT.repository.services.AuthenticationService;
import com.loginSignupJWT.repository.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Override
    public User signup(SignUpRequest signUpRequest, String registeredBy) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // Encode password
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setGender(signUpRequest.getGender());
        user.setCountry(signUpRequest.getCountry());
        user.setRole(Role.USER);
        user.setRegisterBy(registeredBy);
        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Determine requiredRole based on user's role fetched from database
        Role requiredRole = user.getRole();

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public UserDTO getUserDetails(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstname());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setCountry(user.getCountry());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }
}
