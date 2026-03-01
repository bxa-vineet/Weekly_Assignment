package com.oms.model;
import com.oms.enums.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    private long id;
    private String customer;
    private double price;
    private int quantity;
    private OrderType type;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public double totalAmount() {
        return price * quantity;
    }
}
