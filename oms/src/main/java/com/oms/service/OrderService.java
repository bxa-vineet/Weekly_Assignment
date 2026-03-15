package com.oms.service;

import com.oms.enums.*;
import com.oms.exception.*;
import com.oms.logger.*;
import com.oms.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final ExecutorService pool = Executors.newFixedThreadPool(5);

public Order place(Order order) {
    validate(order);
    order.setId(idGen.getAndIncrement());
    order.setStatus(OrderStatus.NEW);
    store.put(order.getId(), order);
    pool.submit(() -> process(order));
    return order;
}

private void validate(Order o) {
    if (o.getPrice() <= 0 || o.getQuantity() <= 0) {
        throw new OrderException("Invalid order input");
    }
}

private void process(Order o) {
    try {
        o.setStatus(OrderStatus.PROCESSING);
        Thread.sleep(200);
        o.setStatus(OrderStatus.COMPLETED);
    } catch (Exception e) {
        o.setStatus(OrderStatus.FAILED);
    }
    FileLogger.log(o);
}

public Collection<Order> getAll() {

    return store.values();
}

// ===== STREAM ANALYTICS =====

public double totalAmount() {
    return store.values().stream()
            .mapToDouble(Order::totalAmount)
            .sum();
}

public Map<OrderType, Long> buySellCount() {
    return store.values().stream()
            .collect(Collectors.groupingBy(
                    Order::getType,
                    Collectors.counting()
            ));
}

public String topCustomer() {
    return store.values().stream()
            .collect(Collectors.groupingBy(
                    Order::getCustomer,
                    Collectors.summingDouble(Order::totalAmount)
            ))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
}

public Map<OrderStatus, List<Order>> groupByStatus() {
    return store.values().stream()
            .collect(Collectors.groupingBy(Order::getStatus));
}
}