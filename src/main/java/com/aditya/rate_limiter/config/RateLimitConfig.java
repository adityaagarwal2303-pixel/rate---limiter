package com.aditya.rate_limiter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimitConfig {

    @Value("${rate.limit.requests}")
    private int maxRequests;

    @Value("${rate.limit.window}")
    private long windowSeconds;

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowSeconds() {
        return windowSeconds;
    }
}