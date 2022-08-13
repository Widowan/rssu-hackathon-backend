package com.hypnotoad.responses.auth;

import com.hypnotoad.configurations.ResponseStyle;
import com.hypnotoad.responses.Response;
import com.hypnotoad.users.OldUser;
import com.hypnotoad.users.User;
import com.hypnotoad.users.UserDto;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGetMeResponse extends Response {
    abstract public UserDto getMe();
}
