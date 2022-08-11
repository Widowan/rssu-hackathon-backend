package com.hypnotoad.games;

import com.hypnotoad.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
public abstract class OldGame {
    abstract int    getId();
    abstract String getName();
    abstract String getDescription();
    abstract String getRules();
    abstract String getIcon();
}
