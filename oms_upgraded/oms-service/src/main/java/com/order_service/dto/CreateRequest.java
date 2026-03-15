package com.order_service.dto;

import lombok.Data;

@Data
public class CreateRequest {
    private String symbol;
    private Integer quantity;
    private Double price;
}

