package com.user.dto;

import jakarta.validation.constraints.*;

public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    // getters/setters
}