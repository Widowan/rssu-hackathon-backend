package com.hypnotoad.responses.game;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.games.Game;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ResponseStyle
abstract public class AbstractAllGamesResponse extends Response {
    abstract public List<Game> getGames();
}
