package com.example.java8.string.calculator.tokens;
/**
 * Represents a setVariable used in an expression
 * 
 * @author spart
 *
 */
public class Variable extends Token {
	private final String name;

	Variable(String vName) {
		super(TOKEN_VARIABLE);
		this.name = vName;
	}

	public String getName() {
		return name;
	}
}
