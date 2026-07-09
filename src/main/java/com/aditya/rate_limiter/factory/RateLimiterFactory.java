package com.aditya.rate_limiter.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aditya.rate_limiter.algorithm.FixedWindowRateLimiter;
import com.aditya.rate_limiter.algorithm.RateLimiter;
import com.aditya.rate_limiter.algorithm.SlidingWindowRateLimiter;
import com.aditya.rate_limiter.algorithm.TokenBucketRateLimiter;

@Component
public class RateLimiterFactory {

    private static final Logger logger =
            LoggerFactory.getLogger(RateLimiterFactory.class);

    private final FixedWindowRateLimiter fixedWindowRateLimiter;
    private final SlidingWindowRateLimiter slidingWindowRateLimiter;
    private final TokenBucketRateLimiter tokenBucketRateLimiter;

    @Value("${rate-limiter.type}")
    private String algorithm;

    public RateLimiterFactory(
            FixedWindowRateLimiter fixedWindowRateLimiter,
            SlidingWindowRateLimiter slidingWindowRateLimiter,
            TokenBucketRateLimiter tokenBucketRateLimiter) {

        this.fixedWindowRateLimiter = fixedWindowRateLimiter;
        this.slidingWindowRateLimiter = slidingWindowRateLimiter;
        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
    }

    public RateLimiter getRateLimiter() {

        logger.info("Using {} rate limiting algorithm", algorithm);

        switch (algorithm.toUpperCase()) {

            case "SLIDING_WINDOW":
                return slidingWindowRateLimiter;

            case "TOKEN_BUCKET":
                return tokenBucketRateLimiter;

            default:
                return fixedWindowRateLimiter;
        }
    }
}