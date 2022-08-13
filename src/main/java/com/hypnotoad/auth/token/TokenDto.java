package com.hypnotoad.auth.token;

import com.hypnotoad.configurations.validators.SpelAssert;

import javax.validation.constraints.Size;

public record TokenDto(
    @Size(max = 32, min = 32, message = "Token must be 32 characters long")
    @SpelAssert(message = "Token is invalid", expr = "@tokenService.isStringTokenValid(#this)")
    String token
) { }
