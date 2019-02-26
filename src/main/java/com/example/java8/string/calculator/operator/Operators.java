package com.example.java8.string.calculator.operator;

import com.example.java8.string.calculator.error.InvalidExpressionException;

public class Operators {
	private static final int INDEX_ADDITION = 0;
	private static final int INDEX_SUBTRACTION = 1;
	private static final int INDEX_MUTLIPLICATION = 2;
	private static final int INDEX_DIVISION = 3;
	private static final int INDEX_POWER = 4;
	private static final int INDEX_MODULO = 5;

	private static final Operator[] builtinOperators = new Operator[6];

	static {
		builtinOperators[INDEX_ADDITION] = new Operator("+", 2, true, Operator.PRECEDENCE_ADDITION) {
			@Override
			public double apply(final double... args) {
				return args[0] + args[1];
			}
		};
		builtinOperators[INDEX_SUBTRACTION] = new Operator("-", 2, true, Operator.PRECEDENCE_ADDITION) {
			@Override
			public double apply(final double... args) {
				return args[0] - args[1];
			}
		};

		builtinOperators[INDEX_MUTLIPLICATION] = new Operator("*", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
			@Override
			public double apply(final double... args) {
				return args[0] * args[1];
			}
		};
		builtinOperators[INDEX_DIVISION] = new Operator("/", 2, true, Operator.PRECEDENCE_DIVISION) {
			@Override
			public double apply(final double... args) {
				if (args[1] == 0d) {
					throw new ArithmeticException("Division by zero!");
				}
				return args[0] / args[1];
			}
		};
		builtinOperators[INDEX_POWER] = new Operator("^", 2, false, Operator.PRECEDENCE_POWER) {
			@Override
			public double apply(final double... args) {
				return Math.pow(args[0], args[1]);
			}
		};
		builtinOperators[INDEX_MODULO] = new Operator("%", 2, true, Operator.PRECEDENCE_MODULO) {
			@Override
			public double apply(final double... args) {
				if (args[1] == 0d) {
					throw new ArithmeticException("Division by zero!");
				}
				return args[0] % args[1];
			}
		};
	}

	public static Operator getBuiltinOperator(final char symbol, final int numArguments)
			throws InvalidExpressionException {
		switch (symbol) {
		case '+':
			if (numArguments != 1) {
				return builtinOperators[INDEX_ADDITION];
			} else {
				throw new InvalidExpressionException("INVALID EXPRESSION");
			}
		case '-':
			if (numArguments != 1) {
				return builtinOperators[INDEX_SUBTRACTION];
			} else {
				throw new InvalidExpressionException("INVALID EXPRESSION");
			}
		case '*':
			return builtinOperators[INDEX_MUTLIPLICATION];
		case '/':
			return builtinOperators[INDEX_DIVISION];
		case '^':
			return builtinOperators[INDEX_POWER];
		case '%':
			return builtinOperators[INDEX_MODULO];
		default:
			return null;
		}
	}
}
