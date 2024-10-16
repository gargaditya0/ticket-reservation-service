package com.example.reservation.exception;

public class SeatingLevelNotFound extends RuntimeException{

    public SeatingLevelNotFound(String message) {
        super(message);
    }
}
