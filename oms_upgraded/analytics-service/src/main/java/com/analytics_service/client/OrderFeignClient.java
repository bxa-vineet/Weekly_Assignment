package com.analytics_service.client;

import com.analytics_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;
@FeignClient(name = "oms-service", configuration = FeignConfig.class)
public interface OrderFeignClient {

    @GetMapping("/api/orders")
    List<Map<String, Object>> getAllOrders();
}