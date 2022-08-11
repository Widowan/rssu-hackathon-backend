package com.hypnotoad.auth.token;

import com.hypnotoad.users.OldUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
class PrimitiveTokenProvider {
    private final JdbcTemplate jdbc;

    static final Logger log = LoggerFactory.getLogger(PrimitiveTokenProvider.class);

    public PrimitiveTokenProvider(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean isValid(String token) {
        var sql = "SELECT COUNT(*) " +
                "FROM  UserTokens " +
                "WHERE token = ? " +
                "   AND expiry_time > extract(epoch FROM now())";

        try {
            //noinspection ConstantConditions
            return jdbc.queryForObject(sql, Integer.class, token) > 0;
        } catch (DataAccessException e) {
            log.error("Couldn't fetch tokens from database: ", e);
        }

        return false;
    }

    public String create() {
        return randomToken(32);
    }

    final static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String randomToken(int len) {
        var sr = new SecureRandom();
        var sb = new StringBuilder();

        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(sr.nextInt(chars.length())));

        return sb.toString();
    }

    public boolean expire(OldUser user) {
        var sql = "UPDATE UserTokens " +
                "SET   expiry_time = extract(epoch FROM now()) - 1 " +
                "WHERE user_id = ? " +
                "  AND expiry_time > extract(epoch FROM now())";
        //var sql = "UPDATE UserTokens SET expiry_time = extract(epoch FROM now()) WHERE user_id = ?";

        try {
            return jdbc.update(sql, user.getId()) > 0;
        } catch (DataAccessException e) {
            log.debug("Couldn't expire token: ", e);
        }

        return false;
    }
}
