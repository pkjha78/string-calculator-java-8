package com.example.java8.string.calculator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.java8.string.calculator.build.MathematicalExpressionBuilder;
import com.example.java8.string.calculator.build.MathematicalStringExpression;
import com.example.java8.string.calculator.build.ValidationResult;


public class ExpressionValidateTest {

	@Test
	public void testValidateOperator() throws Exception {
		MathematicalStringExpression exp = new MathematicalExpressionBuilder("x + 1 + 2")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		assertTrue(result.isValid());
	}
	
	@Test
	public void testValidateFunction() throws Exception {
		MathematicalStringExpression exp = new MathematicalExpressionBuilder("sin(x)")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		assertTrue(result.isValid());
	}
}
