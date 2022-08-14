package com.hypnotoad.configurations;

import com.hypnotoad.responses.FailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(BindException.class)
    public ResponseEntity<FailResponse> handler(BindException ex, Errors errors) {
        if (errors.hasFieldErrors())
            return ResponseEntity.badRequest().body(
                new FailResponse(errors.getFieldError().getDefaultMessage()));
        return ResponseEntity.badRequest().body(
            new FailResponse(errors.getGlobalError().getDefaultMessage()));
    }

    @ExceptionHandler({NoSuchElementException.class, NullPointerException.class})
    public ResponseEntity<FailResponse> nullHandler(Exception ex) {
        return ResponseEntity.badRequest().body(new FailResponse("Bad Request"));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<FailResponse> handler(ValidationException ex) {
        log.error("Validation Exception:", ex);

        return ResponseEntity.badRequest().body(
            new FailResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailResponse> handler(Exception ex) {
        log.error("Exception caught by controller advice:", ex);

        return ResponseEntity.internalServerError().body(
            new FailResponse("Internal Server Error"));
    }
}
