package com.aditya.rate_limiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class HelloController {

    @Operation(summary = "Test endpoint for Rate Limiter")
    @GetMapping("/hello")
    public String hello() {
        return "Hello! Request Allowed.";
    }
}