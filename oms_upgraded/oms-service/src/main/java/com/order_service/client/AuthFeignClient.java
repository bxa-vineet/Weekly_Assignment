package com.order_service.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthFeignClient {

    @GetMapping("/api/auth/validate")
    Object validateToken(@RequestHeader("Authorization") String authHeader);
}

