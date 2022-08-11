package com.hypnotoad.auth.password;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA512PasswordHasher implements PasswordHasher {
    @Override
    public String hash(String password) {
        try {
            var md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            // Interpreting digest as one big positive number and getting its hex
            var hashStringBuilder = new StringBuilder(new BigInteger(1, hash).toString(16));
            // Pad it, just in case...
            while (hashStringBuilder.length() < 128)
                hashStringBuilder.insert(0, "0");

            return hashStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
