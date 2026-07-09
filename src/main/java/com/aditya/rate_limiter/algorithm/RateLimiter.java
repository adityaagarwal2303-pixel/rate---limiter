package com.aditya.rate_limiter.algorithm;

public interface RateLimiter {

    boolean allowRequest(String clientId, int maxRequests, long windowSeconds);

}