package com.hypnotoad.responses.game;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.games.Game;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGameResponse extends Response {
    abstract public Game getGame();
}
