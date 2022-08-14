package com.hypnotoad.gameresults;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameTotalResultRaw {
    private Integer userId;
    private Integer gameId;
    private Long    score;
}
