package com.loginSignupJWT.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private String password;
    private String currentPassword;
    private String profilePicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;  // Add this field
    //have to add password
}
