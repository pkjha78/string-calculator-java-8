package com.example.java8.string.calculator.tokens;

import com.example.java8.string.calculator.error.InvalidExpressionException;

/**
 * Unknown mathematical expression i.e. variable or function
 * 
 * @author spart
 *
 */
public class UnknownExpressionException extends InvalidExpressionException{

	private static final long serialVersionUID = -1180153068813426841L;
	
	private final String message;
	private final String expression;
	private final String token;
	private final int position;
	
	public UnknownExpressionException(String vExpression, int vPosition, int vLength){
		super();
		this.expression=vExpression;
		this.position=vPosition;
		this.token=token(vExpression, vPosition, vLength);
		this.message = "INVALID	EXPRESSION";
		
	}

	private static String token(String vExpression, int vPosition, int vLength) {

		int len = vExpression.length();
		int end = vPosition + vLength - 1;
		if (len < end) {
			end = len;
		}
		return vExpression.substring(vPosition, end);
	}

	public String getMessage() {
		return message;
	}

	public String getExpression() {
		return expression;
	}

	public String getToken() {
		return token;
	}

	public int getPosition() {
		return position;
	}
}
