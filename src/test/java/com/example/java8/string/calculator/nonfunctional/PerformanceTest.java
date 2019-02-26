package com.example.java8.string.calculator.nonfunctional;

import java.util.Formatter;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

import com.example.java8.string.calculator.build.MathematicalExpressionBuilder;
import com.example.java8.string.calculator.build.MathematicalStringExpression;

public class PerformanceTest {
	private static final long BENCH_TIME = 2l;
	private static final String EXPRESSION = "log(x) - y * (sqrt(x^cos(y)))";

	@SuppressWarnings("resource")
	@Test
	public void testBenches() throws Exception {
		StringBuffer sb = new StringBuffer();
		Formatter fmt = new Formatter(sb);
		fmt.format("+------------------------+---------------------------+--------------------------+%n");
		fmt.format("| %-22s | %-25s | %-24s |%n", "Implementation", "Calculations per Second", "Percentage of Math");
		fmt.format("+------------------------+---------------------------+--------------------------+%n");
		System.out.print(sb.toString());
		sb.setLength(0);

		int math = benchJavaMath();
		double mathRate = (double) math / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "Java Math", mathRate, 100f);
		System.out.print(sb.toString());
		sb.setLength(0);

		int db = benchDouble();
		double dbRate = (double) db / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "StringExpCalculator", dbRate, dbRate * 100 / mathRate);
		System.out.print(sb.toString());
		sb.setLength(0);

		int js = benchJavaScript();
		double jsRate = (double) js / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "JSR-223 (Java Script)", jsRate, jsRate * 100 / mathRate);
		fmt.format("+------------------------+---------------------------+--------------------------+%n");
		System.out.print(sb.toString());
	}
	@SuppressWarnings("unused")
	private int benchDouble() {
		final MathematicalStringExpression expression = new MathematicalExpressionBuilder(EXPRESSION)
				.variables("x", "y").build();		
		double val;
		Random rnd = new Random();
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		int count = 0;
		while (time > System.currentTimeMillis()) {
			expression.setVariable("x", rnd.nextDouble());
			expression.setVariable("y", rnd.nextDouble());
			val = expression.evaluate();
			count++;
		}		
		double rate = count / timeout;
		return count;
	}

	@SuppressWarnings("unused")
	private int benchJavaMath() {
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		double x, y, val, rate;
		int count = 0;
		Random rnd = new Random();
		while (time > System.currentTimeMillis()) {
			x = rnd.nextDouble();
			y = rnd.nextDouble();
			val = Math.log(x) - y * (Math.sqrt(Math.pow(x, Math.cos(y))));
			count++;
		}
		rate = count / timeout;
		return count;
	}

	@SuppressWarnings("unused")
	private int benchJavaScript() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		double x, y, val, rate;
		int count = 0;
		Random rnd = new Random();
		if (engine == null) {
			System.err.println("Unable to instantiate javascript engine. skipping naive JS bench.");
			return -1;
		} else {
			time = System.currentTimeMillis() + (1000 * timeout);
			count = 0;
			while (time > System.currentTimeMillis()) {
				x = rnd.nextDouble();
				y = rnd.nextDouble();
				engine.eval("Math.log(" + x + ") - " + y + "* (Math.sqrt(" + x + "^Math.cos(" + y + ")))");
				count++;
			}
			rate = count / timeout;
		}
		return count;
	}
}
