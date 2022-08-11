package com.hypnotoad.responses;

import com.hypnotoad.configurations.ResponseStyle;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractSuccessResponse extends Response {
    abstract public String getMessage();
}
