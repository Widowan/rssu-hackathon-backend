package com.hypnotoad.auth.token;

import com.hypnotoad.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsByTokenAndExpiryTimeIsAfter(String token, LocalDateTime now);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteAllByUser(User user);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Token ut set ut.expiryTime = :time where ut.user = :user")
    void updateExpiryTimeByUser(User user, LocalDateTime time);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteAllByExpiryTimeBefore(LocalDateTime now);

    Token findByTokenAndExpiryTimeIsAfter(String token, LocalDateTime now);
}