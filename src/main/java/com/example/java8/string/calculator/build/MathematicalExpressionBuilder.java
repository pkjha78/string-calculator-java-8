package com.example.java8.string.calculator.build;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.java8.string.calculator.function.Function;
import com.example.java8.string.calculator.function.Functions;
import com.example.java8.string.calculator.infix.ConvertInputExpression;
import com.example.java8.string.calculator.operator.Operator;
/**
 * Build Mathematical Expression with String
 * 
 * @author Prabhakar
 *
 */
public class MathematicalExpressionBuilder {
	private final String expression;

	private final Map<String, Function> userFunctions;

	private final Map<String, Operator> userOperators;

	private final Set<String> variableNames;

	private boolean implicitMultiplication = true;

	public MathematicalExpressionBuilder(String vExpression) {
		if (vExpression == null || vExpression.trim().length() == 0) {
			throw new IllegalArgumentException("Expression can not be empty");
		}
		this.expression = vExpression;
		this.userOperators = new HashMap<String, Operator>(4);
		this.userFunctions = new HashMap<String, Function>(4);
		this.variableNames = new HashSet<String>(4);
	}

	public MathematicalExpressionBuilder function(Function vFunction) {
		this.userFunctions.put(vFunction.getFunctionName(), vFunction);
		return this;
	}

	public MathematicalExpressionBuilder functions(Function... vFunction) {
		for (Function f : vFunction) {
			this.userFunctions.put(f.getFunctionName(), f);
		}
		return this;
	}

	public MathematicalExpressionBuilder functions(List<Function> vFunction) {
		for (Function f : vFunction) {
			this.userFunctions.put(f.getFunctionName(), f);
		}
		return this;
	}

	public MathematicalExpressionBuilder variables(Set<String> variableNames) {
		this.variableNames.addAll(variableNames);
		return this;
	}

	public MathematicalExpressionBuilder variables(String... variableNames) {
		Collections.addAll(this.variableNames, variableNames);
		return this;
	}

	public MathematicalExpressionBuilder variable(String variableName) {
		this.variableNames.add(variableName);
		return this;
	}

	public MathematicalExpressionBuilder implicitMultiplication(boolean vEnabled) {
		this.implicitMultiplication = vEnabled;
		return this;
	}

	public MathematicalExpressionBuilder operator(Operator vOperator) {
		this.checkOperatorSymbol(vOperator);
		this.userOperators.put(vOperator.getSymbol(), vOperator);
		return this;
	}

	private void checkOperatorSymbol(Operator vOperator) {
		String name = vOperator.getSymbol();
		for (char ch : name.toCharArray()) {
			if (!Operator.isAllowedOperatorChar(ch)) {
				throw new IllegalArgumentException("INVALID	EXPRESSION");
			}
		}
	}

	public MathematicalExpressionBuilder operator(Operator... vOperators) {
		for (Operator o : vOperators) {
			this.operator(o);
		}
		return this;
	}

	public MathematicalExpressionBuilder operator(List<Operator> vOperators) {
		for (Operator o : vOperators) {
			this.operator(o);
		}
		return this;
	}

	public MathematicalStringExpression build() {
		if (expression.length() == 0) {
			throw new IllegalArgumentException("The expression can not be empty");
		}
		//set the constants variable names
		variableNames.add("pi");
		variableNames.add("π");
		variableNames.add("e");
		variableNames.add("φ");
		// Check if there are duplicate variables/functions
		for (String var : variableNames) {
			if (Functions.getBuiltinFunction(var) != null || userFunctions.containsKey(var)) {
				throw new IllegalArgumentException("INVALID	EXPRESSION");
			}
		}
		return new MathematicalStringExpression(ConvertInputExpression.convertToRPN(this.expression, this.userFunctions,
				this.userOperators, this.variableNames, this.implicitMultiplication), this.userFunctions.keySet());
	}
}
