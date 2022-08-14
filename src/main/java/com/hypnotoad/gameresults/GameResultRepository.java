package com.hypnotoad.gameresults;

import com.hypnotoad.games.Game;
import com.hypnotoad.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResult, Integer> {
    List<GameResult> findAllByUser(User user);

    List<GameResult> findAllByUserAndGame(User user, Game game);

    @Query(nativeQuery = true)
    GameTotalResultRaw getGameTotalResultByUserAndGameAndDateTimestampIsAfter(User user, Game game, int intEpoch);

    // Hibernate is bugged and doesn't parse jpql query correctly :-(
    // See HHH-1615 and HHH-11513, supposedly (HHH-2407) fixed in
    // Hibernate 6.0.0.Alpha9, which is only in Spring-boot v3.0.0.M5,
    // which is alpha too
    //
    //    select g.user, g.game, sum(g.score) as s from GameResult g
    //    where g.game = :game and g.dateTimestamp > :dateTime
    //    group by g.user.id, g.game.id order by s desc
    @Query(nativeQuery = true)
    List<GameTotalResultRaw> getLeaderboardByGameIdAndAfterRawIntTimestamp(Game game, int intEpoch);
}