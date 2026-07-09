package com.aditya.rate_limiter.model;

public class RateLimitResponse {

    private final int limit;
    private final long remaining;
    private final long reset;

    public RateLimitResponse(int limit, long remaining, long reset) {
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    public int getLimit() {
        return limit;
    }

    public long getRemaining() {
        return remaining;
    }

    public long getReset() {
        return reset;
    }
}