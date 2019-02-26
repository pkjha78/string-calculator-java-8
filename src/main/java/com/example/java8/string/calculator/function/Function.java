package com.example.java8.string.calculator.function;
/**
 * Function which will be used in an expression
 * 
 * @author Prabhakar
 *
 */
public abstract class Function {
	
	public String functionName;
	public int numberOfArguments;
	
	public Function(String vFunctionName) {
        this(vFunctionName, 1);
    }

	public Function(String vFunctionName, int vNumberOfArguments) {
		if (vNumberOfArguments < 0) {
			throw new IllegalArgumentException(
					"The number of function arguments can not be less than 0 for '" + vFunctionName + "'");
		}
		if (!isValidFunctionName(vFunctionName)) {
			throw new IllegalArgumentException("The function name '" + vFunctionName + "' is invalid");
		}
		this.functionName = vFunctionName;
		this.numberOfArguments = vNumberOfArguments;
	}

	public static boolean isValidFunctionName(final String vFunctionName) {
		if (vFunctionName == null) {
			return false;
		}

		final int size = vFunctionName.length();

		if (size == 0) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			final char c = vFunctionName.charAt(i);
			if (Character.isLetter(c) || c == '_') {
				continue;
			} else if (Character.isDigit(c) && i > 0) {
				continue;
			}
			return false;
		}
		return true;
	}
	/**
	 * Method that does the actual calculation of the function value given the arguments
	 * 
	 * @param args set of input for calculating the function
	 * @return the result
	 */
	public abstract double apply(double... args);

	public String getFunctionName() {
		return functionName;
	}

	public int getNumberOfArguments() {
		return numberOfArguments;
	}
}
