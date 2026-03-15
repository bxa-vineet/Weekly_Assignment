package com.order_service.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "analytics-service")
public interface AnalyticsFeignClient {
}

