package com.hypnotoad.auth;

import com.hypnotoad.configurations.validators.SpelAssert;
import com.hypnotoad.users.Username;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserCredentialsSignIn(
    @SpelAssert(message = "User with this username doesn't exists",
        expr = "@userRepository.existsByUsername(#this.username())")
    Username username,

    @NotBlank(message = "Password must not be empty")
    @Size(max = 128, message = "Password must not exceed 128 characters")
    String password
) {
}
