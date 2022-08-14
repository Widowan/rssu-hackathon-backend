package com.hypnotoad.gameresults;

import com.hypnotoad.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GameTotalResultDto {
    private UserDto user;
    private Integer gameId;
    private Long score;
}
