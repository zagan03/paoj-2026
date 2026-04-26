package com.pao.proiect.fooddelivery.exception;

public class RestaurantClosedException extends RuntimeException{
    public RestaurantClosedException(String message){
        super(message);
    }
}
