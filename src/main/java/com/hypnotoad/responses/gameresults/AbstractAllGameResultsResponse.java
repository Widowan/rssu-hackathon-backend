package com.hypnotoad.responses.gameresults;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.gameresults.GameResultDto;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ResponseStyle
abstract public class AbstractAllGameResultsResponse extends Response {
    abstract public List<GameResultDto> getGameResults();
}
