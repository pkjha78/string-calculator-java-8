package com.example.java8.string.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;

import com.example.java8.string.calculator.build.MathematicalExpressionBuilder;
import com.example.java8.string.calculator.build.MathematicalStringExpression;
import com.example.java8.string.calculator.error.InvalidExpressionException;

public class ScientificStringExpressionCalculatorTest {
	
	DecimalFormat decimalFormat = new DecimalFormat("0.#");
	
	@Test
	public void testValid_MathmaticalExpression_TestCase1() {
		MathematicalStringExpression exp = new MathematicalExpressionBuilder("7+(6*5^2+3-4/2)").build();
		//assertEquals(158.0, exp.evaluate(), 0d);
		assertEquals(158, Math.round(exp.evaluate()));
	}
	
	@Test
    public void testValid_MathmaticalExpression_When_NoOperator_AfterOpenParentheses_TestCase2() {
		MathematicalStringExpression e = new MathematicalExpressionBuilder("7+(67(56*2))")
                .build();
		//assertEquals(7511.0, e.evaluate(), 0d);
		assertEquals(7511, Math.round(e.evaluate()));
    }

	@Test(expected = InvalidExpressionException.class)
    public void testInvalid_MathmaticalExpression_When_MultipleOperator_TestCase3() {
		MathematicalStringExpression e = new MathematicalExpressionBuilder("8*+7")
                .build();
        e.evaluate();
    }
	
	@Test
	public void testValid_MathmaticalExpression_TestCase4() {
		MathematicalStringExpression exp1 = new MathematicalExpressionBuilder("(8*5/8)-(3/1)-5").build();
		//assertEquals(-3, exp1.evaluate(), 0d);
		assertEquals(-3, Math.round(exp1.evaluate()));
	}
}
