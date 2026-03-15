package com.order_service.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private Integer quantity;
    private Double price;
    private String createdBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}

