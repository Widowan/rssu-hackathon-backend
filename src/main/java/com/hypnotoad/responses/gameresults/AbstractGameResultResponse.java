package com.hypnotoad.responses.gameresults;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.gameresults.OldGameResult;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGameResultResponse extends Response {
    abstract public OldGameResult getGameResult();
}
