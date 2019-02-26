package com.example.java8.string.calculator.tokens;

public class Number extends Token {

	private final double numberValue;

	Number(double vNumberValue) {
		super(TOKEN_NUMBER);
		this.numberValue = vNumberValue;
	}

	public Number(final char[] vExpression, final int vOffset, final int vLength) {
		this(Double.parseDouble(String.valueOf(vExpression, vOffset, vLength)));
	}

	public double getValue() {
		return numberValue;
	}
}
