package com.aditya.rate_limiter.service;

import org.springframework.stereotype.Service;

import com.aditya.rate_limiter.model.RateLimitInfo;

@Service
public class UserPlanService {

    public RateLimitInfo getPlan(String apiKey) {

        switch (apiKey) {

            case "premium":
                return new RateLimitInfo(50,60);

            case "admin":
                return new RateLimitInfo(Integer.MAX_VALUE,60);

            default:
                return new RateLimitInfo(5,60);
        }
    }
}