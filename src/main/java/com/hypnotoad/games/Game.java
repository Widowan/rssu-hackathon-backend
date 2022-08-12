package com.hypnotoad.games;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_seq")
    @SequenceGenerator(
        name = "game_id_seq",
        sequenceName = "game_id_seq",
        allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String rules;

    @Column(nullable = false)
    private String icon;
}