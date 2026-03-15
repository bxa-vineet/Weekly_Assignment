package com.oms.controller;

import com.oms.dto.*;
import com.oms.model.*;
import com.oms.service.*;
import lombok.RequiredArgsConstructor;// Automatically generates a constructor for all final fields
import org.springframework.web.bind.annotation.*;//It imports all Spring Web annotations

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public Order place(@RequestBody OrderRequest req) {

        return service.place(Order.builder()
                .customer(req.getCustomer())
                .price(req.getPrice())
                .quantity(req.getQuantity())
                .type(req.getType())
                .createdAt(LocalDateTime.now())
                .build());

    }

    @GetMapping
    public Collection<Order> all() {
        return service.getAll();
    }

    @GetMapping("/analytics")
    public Map<String, Object> analytics() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalAmount", service.totalAmount());
        map.put("buyVsSell", service.buySellCount());
        map.put("topCustomer", service.topCustomer());
        map.put("groupByStatus", service.groupByStatus());
        return map;
    }
}