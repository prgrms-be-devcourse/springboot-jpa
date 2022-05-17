package com.kdt.lecture.exception;

public class StockException extends RuntimeException {

    public StockException() {
    }

    public StockException(String message) {
        super(message);
    }
}
