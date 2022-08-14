package com.hypnotoad.gameresults;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResultDto implements Serializable {
    private Integer id;
    private Boolean result;
    private Integer score;
    private Float timeElapsed;
    private LocalDateTime dateTimestamp;
}
