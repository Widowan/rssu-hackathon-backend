package com.hypnotoad.gameresults;

import com.hypnotoad.configurations.customTypes.IntegerTimestampConverter;
import com.hypnotoad.games.Game;
import com.hypnotoad.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gameresults")
// Q: What the fuck is this?
// A: Hibernate bug, see GameResultRepository.java
@NamedNativeQuery(query = """
    SELECT g.user_id, g.game_id, sum(g.score) AS score
    FROM gameresults g WHERE g.game_id = :game AND g.date_timestamp > :intEpoch
    GROUP BY g.user_id, g.game_id ORDER BY score DESC""",
    name = "GameResult.getLeaderboardByGameIdAndAfterRawIntTimestamp",
    resultSetMapping = "Mapping.GameTotalResult")
@NamedNativeQuery(query = """
    SELECT g.user_id, g.game_id, sum(g.score) AS score FROM gameresults g
    WHERE g.user_id = :user AND g.game_id = :game AND g.date_timestamp > :intEpoch
    GROUP BY g.game_id, g.user_id""",
    name = "GameResult.getGameTotalResultByUserAndGameAndDateTimestampIsAfter",
    resultSetMapping = "Mapping.GameTotalResult")
@SqlResultSetMapping(
    name = "Mapping.GameTotalResult",
    classes = @ConstructorResult(
        targetClass = GameTotalResultRaw.class,
        columns = {
            @ColumnResult(name = "user_id", type = Integer.class),
            @ColumnResult(name = "game_id", type = Integer.class),
            @ColumnResult(name = "score",   type = Long.class)
        }))
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameresults_id_seq")
    @SequenceGenerator(
        name = "gameresults_id_seq",
        sequenceName = "gameresults_id_seq",
        allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column
    private Boolean result;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false, name = "time_elapsed")
    private Float timeElapsed;

    @Column(nullable = false, name = "date_timestamp")
    @Convert(converter = IntegerTimestampConverter.class)
    private LocalDateTime dateTimestamp;
}