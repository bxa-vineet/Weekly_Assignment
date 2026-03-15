package com.api_gateway.config;

import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public RouteLocator gatewayRoutes1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://auth-service"))
                .route("oms-service", r -> r
                        .path("/api/orders/**")
                        .uri("lb://oms-service"))
                .route("analytics-service", r -> r
                        .path("/api/analytics/**")
                        .uri("lb://analytics-service"))
                .build();
    }
}


