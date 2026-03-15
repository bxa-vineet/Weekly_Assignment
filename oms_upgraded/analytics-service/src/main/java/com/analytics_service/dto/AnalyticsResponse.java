package com.analytics_service.dto;

import java.util.Map;

public class AnalyticsResponse {

    private double totalOrderAmount;
    private long totalBuyOrders;
    private long totalSellOrders;
    private String topCustomer;
    private Map<String, Long> ordersByStatus;

    public AnalyticsResponse() {}

    public AnalyticsResponse(double totalOrderAmount,
                             long totalBuyOrders,
                             long totalSellOrders,
                             String topCustomer,
                             Map<String, Long> ordersByStatus) {
        this.totalOrderAmount = totalOrderAmount;
        this.totalBuyOrders = totalBuyOrders;
        this.totalSellOrders = totalSellOrders;
        this.topCustomer = topCustomer;
        this.ordersByStatus = ordersByStatus;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public long getTotalBuyOrders() {
        return totalBuyOrders;
    }

    public void setTotalBuyOrders(long totalBuyOrders) {
        this.totalBuyOrders = totalBuyOrders;
    }

    public long getTotalSellOrders() {
        return totalSellOrders;
    }

    public void setTotalSellOrders(long totalSellOrders) {
        this.totalSellOrders = totalSellOrders;
    }

    public String getTopCustomer() {
        return topCustomer;
    }

    public void setTopCustomer(String topCustomer) {
        this.topCustomer = topCustomer;
    }

    public Map<String, Long> getOrdersByStatus() {
        return ordersByStatus;
    }

    public void setOrdersByStatus(Map<String, Long> ordersByStatus) {
        this.ordersByStatus = ordersByStatus;
    }
}