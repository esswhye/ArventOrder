package com.arvent.order.exception;

public class OutOfStockException extends ArventException {

    public OutOfStockException(Long productId) {
        super("Error Product id: " + productId + " Out of stock");
    }
}
