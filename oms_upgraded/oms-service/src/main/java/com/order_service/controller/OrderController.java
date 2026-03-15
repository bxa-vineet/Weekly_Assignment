package com.order_service.controller;

import com.order_service.dto.CreateRequest;

import com.order_service.entity.Order;
import com.order_service.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/trader/init")
    public Order create(@RequestBody CreateRequest request, Authentication authentication) {
        return orderService.createOrder(request, authentication.getName());
    }

    @GetMapping("/trader/my/orders")
    public List<Order> myOrders(Authentication authentication) {
        return orderService.getOrdersByUser(authentication.getName());
    }

    @GetMapping("/admin/all/orders")
    public List<Order> allOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/admin/cancel/by/id/{id}")
    public Order cancel(@PathVariable("id") Long id) {
        return orderService.cancelOrder(id);
    }
}

