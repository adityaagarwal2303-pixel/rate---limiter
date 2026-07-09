package com.aditya.rate_limiter.algorithm;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRateLimiter implements RateLimiter {

    private final StringRedisTemplate redisTemplate;

    public SlidingWindowRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean allowRequest(String clientId,
                                int maxRequests,
                                long windowSeconds) {

        String key = "sliding:" + clientId;

        long now = System.currentTimeMillis();

        // Remove requests older than the window
        redisTemplate.opsForZSet().removeRangeByScore(
                key,
                0,
                now - windowSeconds * 1000
        );

        Long count = redisTemplate.opsForZSet().zCard(key);

        if (count != null && count >= maxRequests) {
            return false;
        }

        // Store current request timestamp
        redisTemplate.opsForZSet().add(
                key,
                String.valueOf(now),
                now
        );

        redisTemplate.expire(
                key,
                Duration.ofSeconds(windowSeconds)
        );

        return true;
    }
}