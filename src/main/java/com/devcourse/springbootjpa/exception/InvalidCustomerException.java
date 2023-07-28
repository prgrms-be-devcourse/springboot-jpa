package com.devcourse.springbootjpa.exception;

public class InvalidCustomerException extends RuntimeException{
	public InvalidCustomerException(String message) {
		super(message);
	}

	public InvalidCustomerException() {
	}
}
