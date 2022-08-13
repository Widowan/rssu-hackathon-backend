package com.hypnotoad.auth;

import com.hypnotoad.auth.token.TokenDto;
import com.hypnotoad.responses.FailResponse;
import com.hypnotoad.responses.Response;
import com.hypnotoad.responses.auth.AuthResponse;
import com.hypnotoad.responses.auth.GetMeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@Slf4j
public class AuthController {
    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v2/auth/signUp")
    public ResponseEntity<Response> signUp(@Valid UserCredentialsSignUp userCredentialsSignUp) {
        var token = authService.signUpUser(userCredentialsSignUp);
        return ResponseEntity.ok(new AuthResponse(token.getToken()));
    }

    @PostMapping("/api/v2/auth/signIn")
    public ResponseEntity<Response> signIn(@Valid UserCredentialsSignIn userCredentialsSignIn) {
        var token = authService.signInUser(userCredentialsSignIn);
        return ResponseEntity.ok(new AuthResponse(token.getToken()));
    }

    @GetMapping("/api/v2/auth/token/{token}/setAvatar/{avatarPath}")
    public ResponseEntity<Response> setAvatar(@Valid TokenDto token,
            @Valid
            @NotBlank(message = "Avatar cannot be blank")
            @Size(max = 32, message = "Avatar path must not exceed 32 characters")
            @PathVariable
            String avatarPath) {
        return ResponseEntity.badRequest().body(new FailResponse("Not implemented"));
    }

    @GetMapping("/api/v2/auth/token/{token}/whoAmI")
    public ResponseEntity<Response> whoAmI(@Valid TokenDto token) {
        var userDto = authService.whoAmI(token);
        return ResponseEntity.ok().body(new GetMeResponse(userDto));
    }
}
