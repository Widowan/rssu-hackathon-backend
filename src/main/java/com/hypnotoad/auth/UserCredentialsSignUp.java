package com.hypnotoad.auth;

import com.hypnotoad.configurations.validators.SpelAssert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserCredentialsSignUp(
    @NotBlank(message = "Username must not be empty")
    @Size(max = 64, message = "Username must not exceed 64 characters")
    @SpelAssert(message = "Username must be unique", expr = "!@userRepository.existsByUsername(#this)")
    String username,

    @NotBlank(message = "Password must not be empty")
    @Size(max = 128, message = "Password must not exceed 128 characters")
    String password
) {
}
