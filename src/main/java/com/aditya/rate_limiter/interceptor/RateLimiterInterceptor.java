package com.aditya.rate_limiter.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.aditya.rate_limiter.exception.RateLimitExceededException;
import com.aditya.rate_limiter.model.RateLimitResponse;
import com.aditya.rate_limiter.service.MetricsService;
import com.aditya.rate_limiter.service.RateLimiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(RateLimiterInterceptor.class);

    private final RateLimiterService rateLimiterService;
    private final MetricsService metricsService;

    public RateLimiterInterceptor(RateLimiterService rateLimiterService,
                                  MetricsService metricsService) {
        this.rateLimiterService = rateLimiterService;
        this.metricsService = metricsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String clientId = request.getHeader("X-API-KEY");

        if (clientId == null || clientId.isBlank()) {
            clientId = request.getRemoteAddr();
        }

        logger.info("Client ID: {}", clientId);

        if (!rateLimiterService.allowRequest(clientId)) {

            logger.warn("Rate limit exceeded for client: {}", clientId);

            metricsService.incrementBlocked();

            RateLimitResponse info =
                    rateLimiterService.getRateLimitResponse(clientId);

            response.setHeader("X-RateLimit-Limit",
                    String.valueOf(info.getLimit()));

            response.setHeader("X-RateLimit-Remaining",
                    String.valueOf(info.getRemaining()));

            response.setHeader("X-RateLimit-Reset",
                    String.valueOf(info.getReset()));

            response.setHeader("Retry-After",
                    String.valueOf(info.getReset()));

            throw new RateLimitExceededException("Rate Limit Exceeded!");
        }

        logger.info("Request allowed for client: {}", clientId);

        metricsService.incrementAllowed();

        RateLimitResponse info =
                rateLimiterService.getRateLimitResponse(clientId);

        response.setHeader("X-RateLimit-Limit",
                String.valueOf(info.getLimit()));

        response.setHeader("X-RateLimit-Remaining",
                String.valueOf(info.getRemaining()));

        response.setHeader("X-RateLimit-Reset",
                String.valueOf(info.getReset()));

        return true;
    }
}