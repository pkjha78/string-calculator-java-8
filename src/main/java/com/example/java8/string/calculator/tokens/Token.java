package com.example.java8.string.calculator.tokens;

public class Token {
	public static final short TOKEN_NUMBER = 1;
	public static final short TOKEN_OPERATOR = 2;
	public static final short TOKEN_FUNCTION = 3;
	public static final short TOKEN_PARENTHESES_OPEN = 4;
	public static final short TOKEN_PARENTHESES_CLOSE = 5;
	public static final short TOKEN_VARIABLE = 6;
	public static final short TOKEN_SEPARATOR = 7;

	protected final int type;

	Token(int vType) {
		this.type = vType;
	}

	public int getType() {
		return type;
	}

}
