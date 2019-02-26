package com.example.java8.string.calculator.function;

/**
 * Built in functions available for use in expressions
 * 
 * @author Prabhakar
 *
 */
public class Functions {
	private static final int INDEX_SIN = 0;
	private static final int INDEX_COS = 1;
	private static final int INDEX_TAN = 2;
	private static final int INDEX_COT = 3;
	private static final int INDEX_LOG = 4;
	private static final int INDEX_LOG1P = 5;
	private static final int INDEX_ABS = 6;
	private static final int INDEX_ACOS = 7;
	private static final int INDEX_ASIN = 8;
	private static final int INDEX_ATAN = 9;
	private static final int INDEX_CBRT = 10;
	private static final int INDEX_CEIL = 11;
	private static final int INDEX_FLOOR = 12;
	private static final int INDEX_SINH = 13;
	private static final int INDEX_SQRT = 14;
	private static final int INDEX_TANH = 15;
	private static final int INDEX_COSH = 16;
	private static final int INDEX_POW = 17;
	private static final int INDEX_EXP = 18;
	private static final int INDEX_EXPM1 = 19;
	private static final int INDEX_LOG10 = 20;
	private static final int INDEX_LOG2 = 21;
	private static final int INDEX_SGN = 22;

	private static final Function[] builtinFunctions = new Function[23];

	static {
		builtinFunctions[INDEX_SIN] = new Function("sin") {
			@Override
			public double apply(double... args) {
				return Math.sin(args[0]);
			}
		};
		builtinFunctions[INDEX_COS] = new Function("cos") {
			@Override
			public double apply(double... args) {
				return Math.cos(args[0]);
			}
		};
		builtinFunctions[INDEX_TAN] = new Function("tan") {
			@Override
			public double apply(double... args) {
				return Math.tan(args[0]);
			}
		};
		builtinFunctions[INDEX_COT] = new Function("cot") {
			@Override
			public double apply(double... args) {
				double tan = Math.tan(args[0]);
				if (tan == 0d) {
					throw new ArithmeticException("Division by zero in cotangent!");
				}
				return 1d / Math.tan(args[0]);
			}
		};
		builtinFunctions[INDEX_LOG] = new Function("log") {
			@Override
			public double apply(double... args) {
				return Math.log(args[0]);
			}
		};
		builtinFunctions[INDEX_LOG2] = new Function("log2") {
			@Override
			public double apply(double... args) {
				return Math.log(args[0]) / Math.log(2d);
			}
		};
		builtinFunctions[INDEX_LOG10] = new Function("log10") {
			@Override
			public double apply(double... args) {
				return Math.log10(args[0]);
			}
		};
		builtinFunctions[INDEX_LOG1P] = new Function("log1p") {
			@Override
			public double apply(double... args) {
				return Math.log1p(args[0]);
			}
		};
		builtinFunctions[INDEX_ABS] = new Function("abs") {
			@Override
			public double apply(double... args) {
				return Math.abs(args[0]);
			}
		};
		builtinFunctions[INDEX_ACOS] = new Function("acos") {
			@Override
			public double apply(double... args) {
				return Math.acos(args[0]);
			}
		};
		builtinFunctions[INDEX_ASIN] = new Function("asin") {
			@Override
			public double apply(double... args) {
				return Math.asin(args[0]);
			}
		};
		builtinFunctions[INDEX_ATAN] = new Function("atan") {
			@Override
			public double apply(double... args) {
				return Math.atan(args[0]);
			}
		};
		builtinFunctions[INDEX_CBRT] = new Function("cbrt") {
			@Override
			public double apply(double... args) {
				return Math.cbrt(args[0]);
			}
		};
		builtinFunctions[INDEX_FLOOR] = new Function("floor") {
			@Override
			public double apply(double... args) {
				return Math.floor(args[0]);
			}
		};
		builtinFunctions[INDEX_SINH] = new Function("sinh") {
			@Override
			public double apply(double... args) {
				return Math.sinh(args[0]);
			}
		};
		builtinFunctions[INDEX_SQRT] = new Function("sqrt") {
			@Override
			public double apply(double... args) {
				return Math.sqrt(args[0]);
			}
		};
		builtinFunctions[INDEX_TANH] = new Function("tanh") {
			@Override
			public double apply(double... args) {
				return Math.tanh(args[0]);
			}
		};
		builtinFunctions[INDEX_COSH] = new Function("cosh") {
			@Override
			public double apply(double... args) {
				return Math.cosh(args[0]);
			}
		};
		builtinFunctions[INDEX_CEIL] = new Function("ceil") {
			@Override
			public double apply(double... args) {
				return Math.ceil(args[0]);
			}
		};
		builtinFunctions[INDEX_POW] = new Function("pow", 2) {
			@Override
			public double apply(double... args) {
				return Math.pow(args[0], args[1]);
			}
		};
		builtinFunctions[INDEX_EXP] = new Function("exp", 1) {
			@Override
			public double apply(double... args) {
				return Math.exp(args[0]);
			}
		};
		builtinFunctions[INDEX_EXPM1] = new Function("expm1", 1) {
			@Override
			public double apply(double... args) {
				return Math.expm1(args[0]);
			}
		};
		builtinFunctions[INDEX_SGN] = new Function("signum", 1) {
			@Override
			public double apply(double... args) {
				if (args[0] > 0) {
					return 1;
				} else if (args[0] < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		};
	}

	/**
	 * Get the built in function for a given name
	 * 
	 * @param name the name of the function
	 * @return a Function instance
	 */
	public static Function getBuiltinFunction(final String vFunctionName) {

		if (vFunctionName.equals("sin")) {
			return builtinFunctions[INDEX_SIN];
		} else if (vFunctionName.equals("cos")) {
			return builtinFunctions[INDEX_COS];
		} else if (vFunctionName.equals("tan")) {
			return builtinFunctions[INDEX_TAN];
		} else if (vFunctionName.equals("cot")) {
			return builtinFunctions[INDEX_COT];
		} else if (vFunctionName.equals("asin")) {
			return builtinFunctions[INDEX_ASIN];
		} else if (vFunctionName.equals("acos")) {
			return builtinFunctions[INDEX_ACOS];
		} else if (vFunctionName.equals("atan")) {
			return builtinFunctions[INDEX_ATAN];
		} else if (vFunctionName.equals("sinh")) {
			return builtinFunctions[INDEX_SINH];
		} else if (vFunctionName.equals("cosh")) {
			return builtinFunctions[INDEX_COSH];
		} else if (vFunctionName.equals("tanh")) {
			return builtinFunctions[INDEX_TANH];
		} else if (vFunctionName.equals("abs")) {
			return builtinFunctions[INDEX_ABS];
		} else if (vFunctionName.equals("log")) {
			return builtinFunctions[INDEX_LOG];
		} else if (vFunctionName.equals("log10")) {
			return builtinFunctions[INDEX_LOG10];
		} else if (vFunctionName.equals("log2")) {
			return builtinFunctions[INDEX_LOG2];
		} else if (vFunctionName.equals("log1p")) {
			return builtinFunctions[INDEX_LOG1P];
		} else if (vFunctionName.equals("ceil")) {
			return builtinFunctions[INDEX_CEIL];
		} else if (vFunctionName.equals("floor")) {
			return builtinFunctions[INDEX_FLOOR];
		} else if (vFunctionName.equals("sqrt")) {
			return builtinFunctions[INDEX_SQRT];
		} else if (vFunctionName.equals("cbrt")) {
			return builtinFunctions[INDEX_CBRT];
		} else if (vFunctionName.equals("pow")) {
			return builtinFunctions[INDEX_POW];
		} else if (vFunctionName.equals("exp")) {
			return builtinFunctions[INDEX_EXP];
		} else if (vFunctionName.equals("expm1")) {
			return builtinFunctions[INDEX_EXPM1];
		} else if (vFunctionName.equals("signum")) {
			return builtinFunctions[INDEX_SGN];
		} else {
			return null;
		}
	}
}
