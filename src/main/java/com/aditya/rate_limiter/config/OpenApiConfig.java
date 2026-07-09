package com.aditya.rate_limiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rateLimiterAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Rate Limiter API")
                        .description("Spring Boot + Redis Rate Limiter")
                        .version("1.0"));
    }
}