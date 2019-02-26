package com.example.java8.string.calculator.tokens;

import com.example.java8.string.calculator.function.Function;

public class FunctionToken extends Token {

	private final Function function;

	FunctionToken(final Function vFunction) {
		super(TOKEN_FUNCTION);
		this.function = vFunction;
	}

	public Function getFunction() {
		return function;
	}
}
