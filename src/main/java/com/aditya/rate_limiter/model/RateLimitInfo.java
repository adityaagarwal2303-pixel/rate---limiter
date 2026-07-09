package com.aditya.rate_limiter.model;

public class RateLimitInfo {

    private final int maxRequests;
    private final long windowSeconds;

    public RateLimitInfo(int maxRequests, long windowSeconds) {
        this.maxRequests = maxRequests;
        this.windowSeconds = windowSeconds;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowSeconds() {
        return windowSeconds;
    }
}