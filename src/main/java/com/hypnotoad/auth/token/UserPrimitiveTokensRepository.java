package com.hypnotoad.auth.token;

import com.hypnotoad.users.OldUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class UserPrimitiveTokensRepository {
    private final JdbcTemplate jdbc;
    private final PrimitiveTokenProvider primitiveTokenProvider;
    @Value("${auth.tokenLifespanSeconds}")
    private long tokenLifespanSeconds;

    static final Logger log = LoggerFactory.getLogger(UserPrimitiveTokensRepository.class);

    private final RowMapper<PrimitiveToken> tokenRowMapper = (rs, rowNum) -> ImmutablePrimitiveToken.builder()
            .userId(rs.getInt("user_id"))
            .token(rs.getString("token"))
            .expiryTimestamp(rs.getInt("timestamp"))
            .build();

    public PrimitiveToken findByUser(OldUser user) {
        var sql = "SELECT * FROM UserTokens WHERE user_id = ?";

        try {
            return jdbc.queryForObject(sql, tokenRowMapper, user.getId());
        } catch (DataAccessException ignored) {}

        return null;
    }

    public PrimitiveToken findByToken(String token) {
        var sql = "SELECT * FROM UserTokens WHERE token = ?";

        try {
            return jdbc.queryForObject(sql, tokenRowMapper, token);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public PrimitiveToken createToken(OldUser user) {
        log.debug("Creating token for user: {}", user);
        deleteTokenByUser(user);

        var token = primitiveTokenProvider.create();
        while (validateTokenNoProlong(token)) token = primitiveTokenProvider.create();

        var pt = ImmutablePrimitiveToken.builder()
                .userId(user.getId())
                .token(token)
                .expiryTimestamp(Instant.now().getEpochSecond() + tokenLifespanSeconds)
                .build();

        var sql = "INSERT INTO UserTokens VALUES (?, ?, ?)";

        try {
            jdbc.update(sql, pt.getUserId(), pt.getToken(), pt.getExpiryTimestamp());
        } catch (DataAccessException e) {
            log.error("Couldn't insert token into DB", e);
        }

        return pt;
    }

    public boolean deleteTokenByUser(OldUser user) {
        return primitiveTokenProvider.expire(user);
    }

    public boolean validateToken(String token) {
        var isValid = primitiveTokenProvider.isValid(token);
        if (!isValid) return false;

        var sql = "UPDATE UserTokens " +
                "SET   expiry_time = extract(epoch FROM now()) + ? " +
                "WHERE token = ?";
        try {
            return jdbc.update(sql, tokenLifespanSeconds, token) > 0;
        } catch (DataAccessException ignored) {}

        return true;
    }

    public boolean validateTokenNoProlong(String token) {
        return primitiveTokenProvider.isValid(token);
    }

    public UserPrimitiveTokensRepository(
            JdbcTemplate jdbc,
            PrimitiveTokenProvider primitiveTokenProvider
    ) {
        this.jdbc = jdbc;
        this.primitiveTokenProvider = primitiveTokenProvider;
    }
}
