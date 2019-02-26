package com.example.java8.string.calculator.error;

public class InvalidExpressionException extends RuntimeException {

	private static final long serialVersionUID = 3957110778506035375L;

	public InvalidExpressionException() {
		super();
	}

	public InvalidExpressionException(String vMessage) {
		super(vMessage);
	}
}
