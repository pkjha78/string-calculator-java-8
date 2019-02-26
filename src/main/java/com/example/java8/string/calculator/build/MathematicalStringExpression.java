package com.example.java8.string.calculator.build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.example.java8.string.calculator.function.Function;
import com.example.java8.string.calculator.function.Functions;
import com.example.java8.string.calculator.operator.Operator;
import com.example.java8.string.calculator.tokens.FunctionToken;
import com.example.java8.string.calculator.tokens.Number;
import com.example.java8.string.calculator.tokens.OperatorToken;
import com.example.java8.string.calculator.tokens.Token;
import com.example.java8.string.calculator.tokens.Variable;
/**
 * Build Mathematical String Expression
 * 
 * @author @author Prabhakar
 *
 */
public class MathematicalStringExpression {
	
	private final Token[] tokens;

	private final Map<String, Double> variables;

	private final Set<String> userFunctionNames;

	private static Map<String, Double> createDefaultVariables() {
		final Map<String, Double> vars = new HashMap<String, Double>(4);
		vars.put("pi", Math.PI);
		vars.put("π", Math.PI);
		vars.put("φ", 1.61803398874d);
		vars.put("e", Math.E);
		return vars;
	}

	public MathematicalStringExpression(final MathematicalStringExpression vExisting) {
		this.tokens = Arrays.copyOf(vExisting.tokens, vExisting.tokens.length);
		this.variables = new HashMap<String, Double>();
		this.variables.putAll(vExisting.variables);
		this.userFunctionNames = new HashSet<String>(vExisting.userFunctionNames);
	}

	MathematicalStringExpression(final Token[] vTokens) {
		this.tokens = vTokens;
		this.variables = createDefaultVariables();
		this.userFunctionNames = Collections.<String>emptySet();
	}

	MathematicalStringExpression(final Token[] vTokens, Set<String> vUserFunctionNames) {
		this.tokens = vTokens;
		this.variables = createDefaultVariables();
		this.userFunctionNames = vUserFunctionNames;
	}

	public MathematicalStringExpression setVariable(final String vName, final double value) {
		this.checkVariableName(vName);
		this.variables.put(vName, Double.valueOf(value));
		return this;
	}

	private void checkVariableName(String vName) {
		if (this.userFunctionNames.contains(vName) || Functions.getBuiltinFunction(vName) != null) {
			throw new IllegalArgumentException(
					"INVALID EXPRESSION");
		}
	}

	public MathematicalStringExpression setVariables(Map<String, Double> variables) {
		for (Map.Entry<String, Double> v : variables.entrySet()) {
			this.setVariable(v.getKey(), v.getValue());
		}
		return this;
	}

	public Set<String> getVariableNames() {
		final Set<String> variables = new HashSet<String>();
		for (final Token t : tokens) {
			if (t.getType() == Token.TOKEN_VARIABLE)
				variables.add(((Variable) t).getName());
		}
		return variables;
	}

	public ValidationResult validate(boolean checkVariablesSet) {
		final List<String> errors = new ArrayList<String>(0);
		if (checkVariablesSet) {
			// check Variable have a value set
			for (final Token t : this.tokens) {
				if (t.getType() == Token.TOKEN_VARIABLE) {
					final String var = ((Variable) t).getName();
					if (!variables.containsKey(var)) {
						errors.add("The setVariable '" + var + "' has not been set");
					}
				}
			}
		}
		int count = 0;
		for (Token tok : this.tokens) {
			switch (tok.getType()) {
			case Token.TOKEN_NUMBER:
			case Token.TOKEN_VARIABLE:
				count++;
				break;
			case Token.TOKEN_FUNCTION:
				final Function func = ((FunctionToken) tok).getFunction();
				final int argsNum = func.getNumberOfArguments();
				if (argsNum > count) {
					errors.add("Not enough arguments for '" + func.getFunctionName() + "'");
				}
				if (argsNum > 1) {
					count -= argsNum - 1;
				} else if (argsNum == 0) {					
					count++;
				}
				break;
			case Token.TOKEN_OPERATOR:
				Operator op = ((OperatorToken) tok).getOperator();
				if (op.getNumOperands() == 2) {
					count--;
				}
				break;
			}
			if (count < 1) {
				errors.add("Too many operators");
				return new ValidationResult(false, errors);
			}
		}
		if (count > 1) {
			errors.add("Too many operands");
		}
		return errors.size() == 0 ? ValidationResult.SUCCESS : new ValidationResult(false, errors);

	}

	public ValidationResult validate() {
		return validate(true);
	}

	public Future<Double> evaluateAsync(ExecutorService executor) {
		return executor.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return evaluate();
			}
		});
	}

	public double evaluate() {
		final ArrayStack output = new ArrayStack();
		for (int i = 0; i < tokens.length; i++) {
			Token t = tokens[i];
			if (t.getType() == Token.TOKEN_NUMBER) {
				output.push(((Number) t).getValue());
			} else if (t.getType() == Token.TOKEN_VARIABLE) {
				final String name = ((Variable) t).getName();
				final Double value = this.variables.get(name);
				if (value == null) {
					throw new IllegalArgumentException("No value has been set for the setVariable '" + name + "'.");
				}
				output.push(value);
			} else if (t.getType() == Token.TOKEN_OPERATOR) {
				OperatorToken op = (OperatorToken) t;
				if (output.size() < op.getOperator().getNumOperands()) {
					throw new IllegalArgumentException(
							"Invalid number of operands available for '" + op.getOperator().getSymbol() + "' operator");
				}
				if (op.getOperator().getNumOperands() == 2) {
					// pop out the operands and push the result of the operation
					double rightArg = output.pop();
					double leftArg = output.pop();
					output.push(op.getOperator().apply(leftArg, rightArg));
				} else if (op.getOperator().getNumOperands() == 1) {
					// pop out the operand and push the result of the operation
					double arg = output.pop();
					output.push(op.getOperator().apply(arg));
				}
			} else if (t.getType() == Token.TOKEN_FUNCTION) {
				FunctionToken func = (FunctionToken) t;
				final int numArguments = func.getFunction().getNumberOfArguments();
				if (output.size() < numArguments) {
					throw new IllegalArgumentException("Invalid number of arguments available for '"
							+ func.getFunction().getFunctionName() + "' function");
				}
				// get the arguments from the stack
				double[] args = new double[numArguments];
				for (int j = numArguments - 1; j >= 0; j--) {
					args[j] = output.pop();
				}
				output.push(func.getFunction().apply(args));
			}
		}
		if (output.size() > 1) {
			throw new IllegalArgumentException(
					"Invalid number of items on the output queue. Might be caused by an invalid number of arguments for a function.");
		}
		return output.pop();
	}
}
