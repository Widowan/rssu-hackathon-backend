package com.hypnotoad.users;

import com.hypnotoad.auth.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findByPasswordHash(String passwordHash);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User u set u.avatar = :avatar where u.id = :id")
    boolean updateAvatarById(int id, String avatar);

    User getUserByTokenAndToken_ExpiryTimeBefore(Token token, LocalDateTime now);
}