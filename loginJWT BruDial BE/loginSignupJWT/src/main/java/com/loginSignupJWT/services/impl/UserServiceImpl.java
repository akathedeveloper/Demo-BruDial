package com.loginSignupJWT.services.impl;

import com.loginSignupJWT.dto.UserDTO;
import com.loginSignupJWT.entities.User;
import com.loginSignupJWT.repository.UserRepository;
import com.loginSignupJWT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username){
                return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public User updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent email change
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
            throw new IllegalArgumentException("Email cannot be changed");
        }

        if (userDTO.getFirstName() != null) {
            existingUser.setFirstname(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            existingUser.setLastName(userDTO.getLastName());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            existingUser.setPassword(hashedPassword);
        }
        if (userDTO.getGender() != null) {
            existingUser.setGender(userDTO.getGender());
        }
        if (userDTO.getCountry() != null) {
            existingUser.setCountry(userDTO.getCountry());
        }

        return userRepository.save(existingUser);
    }
}
