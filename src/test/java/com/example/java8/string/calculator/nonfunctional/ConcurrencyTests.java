package com.example.java8.string.calculator.nonfunctional;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.example.java8.string.calculator.build.MathematicalExpressionBuilder;

public class ConcurrencyTests {
	@SuppressWarnings("rawtypes")
	@Test
	public void testFutureEvaluation() throws Exception {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		int numTests = 10000;
		double[] correct1 = new double[numTests];
		Future[] results1 = new Future[numTests];

		double[] correct2 = new double[numTests];
		Future[] results2 = new Future[numTests];

		for (int i = 0; i < numTests; i++) {
			correct1[i] = Math.sin(2 * Math.PI / (i + 1));
			results1[i] = new MathematicalExpressionBuilder("sin(2pi/(n+1))").variables("pi", "n").build()
					.setVariable("pi", Math.PI).setVariable("n", i).evaluateAsync(exec);

			correct2[i] = Math.log(Math.E * Math.PI * (i + 1));
			results2[i] = new MathematicalExpressionBuilder("log(epi(n+1))").variables("pi", "n", "e").build()
					.setVariable("pi", Math.PI).setVariable("e", Math.E).setVariable("n", i).evaluateAsync(exec);
		}

		for (int i = 0; i < numTests; i++) {
			assertEquals(correct1[i], (Double) results1[i].get(), 0d);
			assertEquals(correct2[i], (Double) results2[i].get(), 0d);
		}
	}
}
