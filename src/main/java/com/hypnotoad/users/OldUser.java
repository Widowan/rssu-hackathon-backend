package com.hypnotoad.users;

import com.hypnotoad.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
abstract public class OldUser {
    abstract public int getId();
    abstract public String getUsername();
    abstract public String getAvatar();
}
