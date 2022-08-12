package com.hypnotoad.auth.token;

import com.hypnotoad.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    // TODO: Automatic purging
    boolean existsByTokenAndExpiryTimeBefore(String token, LocalDateTime now);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteAllByUser(User user);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Token ut set ut.expiryTime = :time where ut.user = :user")
    void setExpiryTimeByUser(User user, LocalDateTime time);
}