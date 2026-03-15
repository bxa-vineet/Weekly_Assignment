package com.oms.exception;

public class OrderException extends RuntimeException {
   // Spring Boot they are caught by the framework and converted into HTTP error responses.
    public OrderException(String message) {
        super(message);
    }
}
