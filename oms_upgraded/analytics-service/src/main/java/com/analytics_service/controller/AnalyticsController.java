package com.analytics_service.controller;
import com.analytics_service.dto.AnalyticsResponse;
import com.analytics_service.service.AnalyticsService;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.service = analyticsService;
    }

    @GetMapping
    public AnalyticsResponse getAnalytics() {
        return service.generateAnalytics();
    }
}

