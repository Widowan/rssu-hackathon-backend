package com.hypnotoad.gameresults;

import com.hypnotoad.configurations.customTypes.GameTotalResult;
import com.hypnotoad.games.Game;
import com.hypnotoad.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResult, Integer> {
    List<GameResult> findAllByUser(User user);

    GameTotalResult findGameTotalResultByUserAndGameAndDateTimestampIsAfter(User user, Game game, LocalDateTime date);

    @Query("""
        select g.user, g.game, sum(g.score) as s from GameResult g
        where g.game = :game and g.dateTimestamp > :dateTime
        group by g.user order by s desc""")
    List<GameTotalResult> findLeaderboardByGameAndDateTimestampAfter(Game game, LocalDateTime dateTime, Pageable pageable);
}