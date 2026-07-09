package com.aditya.rate_limiter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aditya.rate_limiter.factory.RateLimiterFactory;
import com.aditya.rate_limiter.model.RateLimitInfo;
import com.aditya.rate_limiter.model.RateLimitResponse;
import com.aditya.rate_limiter.repository.RedisRepository;

@Service
public class RateLimiterService {

    private static final Logger logger =
            LoggerFactory.getLogger(RateLimiterService.class);

    private final RateLimiterFactory factory;
    private final UserPlanService userPlanService;
    private final RedisRepository redisRepository;

    public RateLimiterService(RateLimiterFactory factory,
                              UserPlanService userPlanService,
                              RedisRepository redisRepository) {

        this.factory = factory;
        this.userPlanService = userPlanService;
        this.redisRepository = redisRepository;
    }

    public boolean allowRequest(String clientId) {

        RateLimitInfo info = userPlanService.getPlan(clientId);

        logger.info(
                "Checking rate limit for client: {} (Limit: {}, Window: {} sec)",
                clientId,
                info.getMaxRequests(),
                info.getWindowSeconds());

        return factory.getRateLimiter().allowRequest(
                clientId,
                info.getMaxRequests(),
                info.getWindowSeconds()
        );
    }

    public RateLimitResponse getRateLimitResponse(String clientId) {

        RateLimitInfo info = userPlanService.getPlan(clientId);

        String key = "rate:" + clientId;

        long count = redisRepository.getCount(key);

        long remaining = Math.max(0,
                info.getMaxRequests() - count);

        long reset = redisRepository.getTTL(key);

        return new RateLimitResponse(
                info.getMaxRequests(),
                remaining,
                reset
        );
    }
}