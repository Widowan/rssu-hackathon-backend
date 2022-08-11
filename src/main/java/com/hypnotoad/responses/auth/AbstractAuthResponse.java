package com.hypnotoad.responses.auth;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractAuthResponse extends Response {
    abstract public String getToken();
}
