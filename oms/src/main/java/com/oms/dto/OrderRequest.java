package com.oms.dto;
import com.oms.enums.OrderType;
import com.oms.model.Order;
public class OrderRequest {

    private String customer;
    private double price;
    private int quantity;
    private OrderType type;

    public String getCustomer() {

        return customer;
    }

    public double getPrice() {

        return price;
    }

    public int getQuantity() {

        return quantity;
    }

    public OrderType getType() {

        return type;
    }
}