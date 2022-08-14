package com.hypnotoad.gameresults;

import com.hypnotoad.games.Game;
import com.hypnotoad.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GameTotalResult {
    private User user;
    private Game game;
    private Long score;
}
