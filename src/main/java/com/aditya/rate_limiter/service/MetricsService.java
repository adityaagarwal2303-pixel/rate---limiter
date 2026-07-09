package com.aditya.rate_limiter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class MetricsService {

    private static final Logger logger =
            LoggerFactory.getLogger(MetricsService.class);

    private final Counter allowedRequests;
    private final Counter blockedRequests;

    public MetricsService(MeterRegistry registry) {

        logger.info("MetricsService initialized");

        this.allowedRequests = Counter.builder("rate_limiter_allowed_requests")
                .description("Total allowed requests")
                .register(registry);

        this.blockedRequests = Counter.builder("rate_limiter_blocked_requests")
                .description("Total blocked requests")
                .register(registry);
    }

    public void incrementAllowed() {
        allowedRequests.increment();
    }

    public void incrementBlocked() {
        blockedRequests.increment();
    }
}