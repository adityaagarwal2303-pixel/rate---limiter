
//NOT REQUIRED ANYMORE


// package com.aditya.rate_limiter.controller;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.aditya.rate_limiter.service.MetricsService;

// @RestController
// public class MetricsController {

//     private final MetricsService metricsService;

//     public MetricsController(MetricsService metricsService) {
//         this.metricsService = metricsService;
//     }

//     @GetMapping("/metrics")
//     public Map<String, Long> metrics() {

//         Map<String, Long> response = new HashMap<>();

//         response.put("allowedRequests",
//                 metricsService.getAllowedRequests());

//         response.put("blockedRequests",
//                 metricsService.getBlockedRequests());

//         return response;
//     }
// }