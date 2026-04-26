package com.pao.proiect.fooddelivery.exception;

public class ItemsFromDifferentRestaurantsException extends RuntimeException {
    public ItemsFromDifferentRestaurantsException(String message) {
        super(message);
    }
}
