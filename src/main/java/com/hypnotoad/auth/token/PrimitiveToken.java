package com.hypnotoad.auth.token;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(stagedBuilder = true)
abstract public class PrimitiveToken {
    abstract public int getUserId();
    abstract public String getToken();
    abstract public long getExpiryTimestamp();
}
