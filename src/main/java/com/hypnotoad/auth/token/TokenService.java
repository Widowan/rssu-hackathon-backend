package com.hypnotoad.auth.token;

import com.hypnotoad.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TokenService {
    final TokenRepository tokenRepository;
    final Clock clock;
    @Value("${security.token.length}") int tokenLength;
    @Value("${security.token.chars}") String tokenChars;
    @Value("${security.token.lifespanSeconds}") long lifespanSeconds;

    public TokenService(TokenRepository tokenRepository, Clock clock) {
        this.tokenRepository = tokenRepository;
        this.clock = clock;
    }

    @Transactional
    public Token createOrUpdateTokenForUser(User user) {
        var token = Token.builder()
            .token(createTokenString())
            .user(user)
            .expiryTime(LocalDateTime.now(clock).plus(Duration.ofSeconds(lifespanSeconds)))
            .build();
        tokenRepository.deleteAllByUser(user);
        return tokenRepository.save(token);
    }

    public boolean isTokenValid(Token token) {
        return tokenRepository.existsByTokenAndExpiryTimeIsAfter(
            token.getToken(), LocalDateTime.now(clock));
    }

    public boolean isStringTokenValid(String token) {
        return getByStringToken(token) != null;
    }

    public Token getByStringToken(String token) {
        return tokenRepository.findByTokenAndExpiryTimeIsAfter(
            token, LocalDateTime.now(clock));
    }

    private String createTokenString() {
        var sr = new SecureRandom();
        var sb = new StringBuilder();

        for (int i = 0; i < tokenLength; i++)
            sb.append(tokenChars.charAt(sr.nextInt(tokenChars.length())));

        return sb.toString();
    }

    @Scheduled(fixedDelay = 6 * 60 * 60 * 1000) // Every 6 hours
    @Transactional
    public void purgeExpiredTokens() {
        tokenRepository.deleteAllByExpiryTimeBefore(LocalDateTime.now(clock));
    }
}
