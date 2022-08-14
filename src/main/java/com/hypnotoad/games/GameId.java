package com.hypnotoad.games;

import com.hypnotoad.configurations.validators.SpelAssert;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record GameId(
    @Min(value = 1, message = "Id can only be positive")
    @NotNull
    @SpelAssert(message = "Incorrect gameId", expr = "@gameRepository.existsById(#this)")
    Integer gameId
) {
    private static Integer cast(String id) {
        try {
            return Integer.valueOf(id);
        }
        catch (Exception e) {
            throw new ValidationException("Incorrect id");
        }
    }

    public GameId(String id) {
        this(cast(id));
    }
}
