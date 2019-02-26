package com.example.java8.string.calculator.operator;

public abstract class Operator {
	public static final int PRECEDENCE_ADDITION = 500;
	public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
	public static final int PRECEDENCE_MULTIPLICATION = 1000;
	public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
	public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
	public static final int PRECEDENCE_POWER = 10000;

	public static final char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/', '%', '^', '!', '#', '§', '$', '&', ';',
			':', '~', '<', '>', '|', '=' };

	protected final int numOperands;
	protected final boolean leftAssociative;
	protected final String symbol;
	protected final int precedence;

	public Operator(String vSymbol, int vNumberOfOperands, boolean vLeftAssociative, int vPrecedence) {
		super();
		this.symbol = vSymbol;
		this.numOperands = vNumberOfOperands;
		this.leftAssociative = vLeftAssociative;
		this.precedence = vPrecedence;
	}

	public static boolean isAllowedOperatorChar(char vChar) {
		for (char vAllowed : ALLOWED_OPERATOR_CHARS) {
			if (vChar == vAllowed) {
				return true;
			}
		}
		return false;
	}

	public boolean isLeftAssociative() {
		return leftAssociative;
	}

	public int getPrecedence() {
		return precedence;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getNumOperands() {
		return numOperands;
	}

	public abstract double apply(double... args);

}
