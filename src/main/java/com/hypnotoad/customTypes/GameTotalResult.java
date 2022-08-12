package com.hypnotoad.customTypes;

import com.hypnotoad.games.Game;
import com.hypnotoad.users.User;

public interface GameTotalResult {
    User getUser();
    Game getGame();
    Long getScore();
}
