package com.example.java8.string.calculator.tokens;

import com.example.java8.string.calculator.operator.Operator;

public class OperatorToken extends Token {
	
	private final Operator operator;

	OperatorToken(Operator vOperator) {
		super(TOKEN_OPERATOR);
		if (vOperator == null) {
			throw new IllegalArgumentException("INVALID	EXPRESSION");
		}
		this.operator = vOperator;
	}

	public Operator getOperator() {
		return operator;
	}
}
