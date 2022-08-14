package com.hypnotoad.auth;

import com.hypnotoad.configurations.validators.SpelAssert;
import com.hypnotoad.users.Username;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserCredentialsSignUp(
    @SpelAssert(message = "Username must be unique", expr = "!@userRepository.existsByUsername(#this.username())")
    Username username,

    @NotBlank(message = "Password must not be empty")
    @Size(max = 128, message = "Password must not exceed 128 characters")
    String password
) {
}
