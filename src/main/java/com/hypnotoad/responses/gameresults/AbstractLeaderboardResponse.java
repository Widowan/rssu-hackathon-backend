package com.hypnotoad.responses.gameresults;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.gameresults.LeaderboardRow;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ResponseStyle
abstract public class AbstractLeaderboardResponse extends Response {
    abstract public List<LeaderboardRow> getLeaderboard();
}
