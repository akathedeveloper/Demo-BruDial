package com.loginSignupJWT.services;

import com.loginSignupJWT.dto.UserDTO;
import com.loginSignupJWT.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    User updateUser(Long userId, UserDTO userDTO);
}
