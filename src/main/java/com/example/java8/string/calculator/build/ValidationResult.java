package com.example.java8.string.calculator.build;

import java.util.List;
/**
 * Contains the validation result for {@link MathematicalStringExpression}
 * 
 * @author Prabhakar
 *
 */
public class ValidationResult {
	
	private final boolean valid;
	private final List<String> errors;
	
	/**
	 * A static class representing a successful validation result
	 */
	public static final ValidationResult SUCCESS = new ValidationResult(true, null);

	public ValidationResult(boolean valid, List<String> vErrors) {
		this.valid = valid;
		this.errors = vErrors;
	}

	public boolean isValid() {
		return valid;
	}

	public List<String> getErrors() {
		return errors;
	}
}
