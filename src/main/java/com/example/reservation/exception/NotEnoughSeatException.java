package com.example.reservation.exception;

public class NotEnoughSeatException extends RuntimeException{

    public NotEnoughSeatException(String message) {
        super(message);
    }
}
