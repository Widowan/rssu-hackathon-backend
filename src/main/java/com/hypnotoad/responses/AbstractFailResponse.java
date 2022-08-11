package com.hypnotoad.responses;

import com.hypnotoad.configurations.ResponseStyle;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractFailResponse extends Response {
    abstract public String getReason();
    public boolean getOk() { return false; }
}
