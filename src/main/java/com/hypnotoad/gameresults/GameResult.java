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