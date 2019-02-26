package com.example.java8.string.calculator.build;

import java.util.EmptyStackException;
/**
 * Double array Stack for data storage
 * 
 * @author Prabhakar
 *
 */
public class ArrayStack {
	
	private double[] data;

	private int idx;

	ArrayStack() {
		this(5);
	}

	ArrayStack(int vInitialCapacity) {
		if (vInitialCapacity <= 0) {
			throw new IllegalArgumentException("Stack's capacity must be positive");
		}

		data = new double[vInitialCapacity];
		idx = -1;
	}

	void push(double value) {
		if (idx + 1 == data.length) {
			double[] temp = new double[(int) (data.length * 1.2) + 1];
			System.arraycopy(data, 0, temp, 0, data.length);
			data = temp;
		}

		data[++idx] = value;
	}

	double peek() {
		if (idx == -1) {
			throw new EmptyStackException();
		}
		return data[idx];
	}

	double pop() {
		if (idx == -1) {
			throw new EmptyStackException();
		}
		return data[idx--];
	}

	boolean isEmpty() {
		return idx == -1;
	}

	int size() {
		return idx + 1;
	}
}
