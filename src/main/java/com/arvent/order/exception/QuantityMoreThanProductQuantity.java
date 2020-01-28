package com.arvent.order.exception;


public class QuantityMoreThanProductQuantity extends ArventException {

    public QuantityMoreThanProductQuantity(Long productId) {
        super("Error Product id: " + productId + " Exceeding");
    }
}
