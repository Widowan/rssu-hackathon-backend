package com.hypnotoad.auth;

import com.hypnotoad.auth.password.PasswordHasher;
import com.hypnotoad.auth.token.Token;
import com.hypnotoad.auth.token.TokenDto;
import com.hypnotoad.auth.token.TokenRepository;
import com.hypnotoad.auth.token.TokenService;
import com.hypnotoad.users.User;
import com.hypnotoad.users.UserDto;
import com.hypnotoad.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
@Slf4j
public class AuthService {
    final UserRepository userRepository;
    final TokenService tokenService;
    final TokenRepository tokenRepository;
    final PasswordHasher passwordHasher;
    final ModelMapper modelMapper;

    public AuthService(UserRepository userRepository, TokenService tokenService,
            PasswordHasher passwordHasher, TokenRepository tokenRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordHasher = passwordHasher;
        this.tokenRepository = tokenRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Token signUpUser(UserCredentialsSignUp creds) {
        var user = User.builder()
            .username(creds.username().username())
            .passwordHash(passwordHasher.hash(creds.password()))
            .build();
        userRepository.save(user);
        var token = tokenService.createOrUpdateTokenForUser(user);
        log.info("Signup issued by user {}", creds.username());
        return token;
    }

    @Transactional
    public Token signInUser(UserCredentialsSignIn creds) {
        var passwordHash = passwordHasher.hash(creds.password());

        var userWithThisUsername = userRepository.findByUsername(creds.username().username());
        if (!passwordHash.equals(userWithThisUsername.getPasswordHash()))
            throw new ValidationException("Incorrect password");

        var newToken = tokenService.createOrUpdateTokenForUser(userWithThisUsername);
        log.info("SignIn issued by user {}", creds.username());
        return newToken;
    }

    public UserDto whoAmI(TokenDto token) {
         var user = tokenService.getByStringToken(token.token()).getUser();
         return modelMapper.map(user, UserDto.class);
    }
}
