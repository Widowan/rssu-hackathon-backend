package com.hypnotoad.gameresults;

import com.hypnotoad.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
abstract public class OldGameTotalResult {
    abstract int getUserId();
    abstract int getGameId();
    abstract int getSumScore();
}
