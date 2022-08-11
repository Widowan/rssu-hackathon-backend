package com.hypnotoad.responses.gameresults;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.gameresults.GameTotalResult;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGameTotalResultResponse extends Response {
    abstract public GameTotalResult getGameTotalResult();
}
