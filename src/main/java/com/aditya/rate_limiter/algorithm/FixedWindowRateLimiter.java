package com.aditya.rate_limiter.algorithm;

import org.springframework.stereotype.Component;

import com.aditya.rate_limiter.repository.RedisRepository;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final RedisRepository redisRepository;

    public FixedWindowRateLimiter(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public boolean allowRequest(String clientId,
                                int maxRequests,
                                long windowSeconds) {

        String key = "rate:" + clientId;

        // Atomically increment first — avoids race condition between exists/increment/setExpiry
        Long count = redisRepository.increment(key);

        // Only set expiry on the very first request (count == 1) for this window
        if (count == 1) {
            redisRepository.setExpiry(key, windowSeconds);
        }

        return count <= maxRequests;
    }
}