package com.hypnotoad.auth.password;

public interface PasswordHasher {
    String hash(String password);
}
