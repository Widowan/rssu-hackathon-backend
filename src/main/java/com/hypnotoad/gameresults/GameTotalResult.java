package com.hypnotoad.gameresults;

import com.hypnotoad.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
abstract public class GameTotalResult {
    abstract int getUserId();
    abstract int getGameId();
    abstract int getSumScore();
}
