package com.analytics_service.service;

import com.analytics_service.client.OrderFeignClient;
import com.analytics_service.dto.AnalyticsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class AnalyticsService {

    private final OrderFeignClient orderFeignClient;

    public AnalyticsService(OrderFeignClient orderFeignClient) {
        this.orderFeignClient = orderFeignClient;
    }

    public AnalyticsResponse generateAnalytics() {

        List<Map<String, Object>> orders = orderFeignClient.getAllOrders();

        // Total order amount
        double totalAmount = orders.stream()
                .mapToDouble(o -> Double.parseDouble(o.get("amount").toString()))
                .sum();

        // BUY vs SELL
        long buyOrders = orders.stream()
                .filter(o -> o.get("type").toString().equalsIgnoreCase("BUY"))
                .count();

        long sellOrders = orders.stream()
                .filter(o -> o.get("type").toString().equalsIgnoreCase("SELL"))
                .count();

        // Top customer by volume
        String topCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.get("customer").toString(),
                        Collectors.summingDouble(o -> Double.parseDouble(o.get("amount").toString()))
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        // Group by order status
        Map<String, Long> ordersByStatus = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.get("status").toString(),
                        Collectors.counting()
                ));

        return new AnalyticsResponse(
                totalAmount,
                buyOrders,
                sellOrders,
                topCustomer,
                ordersByStatus
        );
    }
}

