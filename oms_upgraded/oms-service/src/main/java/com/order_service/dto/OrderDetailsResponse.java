package com.order_service.dto;


import com.order_service.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderDetailsResponse {
    private Long id;
    private String symbol;
    private Integer quantity;
    private Double price;
    private String createdBy;
    private OrderStatus status;

}

