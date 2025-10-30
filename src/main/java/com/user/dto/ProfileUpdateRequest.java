package com.user.dto;

import jakarta.validation.constraints.*;

public class ProfileUpdateRequest {
    @NotBlank @Size(min=2, max=80)
    private String fullName;

    @NotBlank @Email @Size(max=120)
    private String email;

    public String getFullName() {
        return "";
    }

    public String getEmail() {
        return "";
    }
}