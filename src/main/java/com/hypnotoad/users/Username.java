package com.hypnotoad.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record Username(
    @NotBlank(message = "Username must not be empty")
    @Size(max = 64, message = "Username must not exceed 64 characters")
    String username
) {
}
