package com.devcourse.springbootjpa.exception;

public class InvalidQuantityException extends RuntimeException {
	public InvalidQuantityException() {
	}

	public InvalidQuantityException(String message) {
		super(message);
	}
}
