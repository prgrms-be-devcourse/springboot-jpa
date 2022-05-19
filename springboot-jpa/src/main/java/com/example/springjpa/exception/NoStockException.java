package com.example.springjpa.exception;

public class NoStockException extends RuntimeException {
    public NoStockException(String message) {
        super(message);
    }
}
