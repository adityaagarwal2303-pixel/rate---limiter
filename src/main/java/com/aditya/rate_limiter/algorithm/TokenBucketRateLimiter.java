package com.aditya.rate_limiter.algorithm;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBucketRateLimiter implements RateLimiter {

    private static class Bucket {
        int tokens;
        long lastRefill;

        Bucket(int capacity) {
            this.tokens = capacity;
            this.lastRefill = System.currentTimeMillis();
        }
    }

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String clientId,
                                int capacity,
                                long refillSeconds) {

        Bucket bucket = buckets.computeIfAbsent(clientId,
                k -> new Bucket(capacity));

        synchronized (bucket) {

            long now = System.currentTimeMillis();

            long elapsed = now - bucket.lastRefill;

            if (elapsed >= refillSeconds * 1000) {
                bucket.tokens = capacity;
                bucket.lastRefill = now;
            }

            if (bucket.tokens <= 0) {
                return false;
            }

            bucket.tokens--;

            return true;
        }
    }
}