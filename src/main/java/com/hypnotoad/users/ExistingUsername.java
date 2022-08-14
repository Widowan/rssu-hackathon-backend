package com.hypnotoad.users;

import com.hypnotoad.configurations.validators.SpelAssert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ExistingUsername(
    @NotBlank(message = "Username must not be empty")
    @Size(max = 64, message = "Username must not exceed 64 characters")
    @SpelAssert(message = "Username doesn't exists", expr = "@userRepository.existsByUsername(#this)")
    String username
) {
}
