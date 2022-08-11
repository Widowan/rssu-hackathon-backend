package com.hypnotoad.gameresults;

import com.hypnotoad.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
abstract public class LeaderboardRow {
    abstract int    getUserId();
    abstract int    getPlace();
    abstract int    sumScore();
    abstract String getUsername();
}
