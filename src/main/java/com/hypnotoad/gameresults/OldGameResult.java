package com.hypnotoad.gameresults;

import com.hypnotoad.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
public abstract class OldGameResult {
    abstract int     getId();
    abstract int     getUserId();
    abstract int     getGameId();
    abstract boolean getResult();
    abstract int     getScore();
    abstract float   getTimeElapsed();
    abstract int     getDateTimestamp();
}
