package com.example.java8.string.calculator.tokens;

import java.util.Map;
import java.util.Set;

import com.example.java8.string.calculator.function.Function;
import com.example.java8.string.calculator.function.Functions;
import com.example.java8.string.calculator.operator.Operator;
import com.example.java8.string.calculator.operator.Operators;

public class MathStringExpression {

	private final char[] expression;
	private final int expressionLength;
	private final Map<String, Function> userFunctions;
	private final Map<String, Operator> userOperators;
	private final Set<String> variableNames;
	private final boolean implicitMultiplication;
	private int pos = 0;
	private Token lastToken;

	public MathStringExpression(String vExpression, final Map<String, Function> vUserFunctions,
			final Map<String, Operator> vUserOperators, final Set<String> variableNames,
			final boolean implicitMultiplication) {
		this.expression = vExpression.trim().toCharArray();
		this.expressionLength = this.expression.length;
		this.userFunctions = vUserFunctions;
		this.userOperators = vUserOperators;
		this.variableNames = variableNames;
		this.implicitMultiplication = implicitMultiplication;
	}

	public MathStringExpression(String vExpression, final Map<String, Function> vUserFunctions,
			final Map<String, Operator> vUserOperators, final Set<String> variableNames) {
		this.expression = vExpression.trim().toCharArray();
		this.expressionLength = this.expression.length;
		this.userFunctions = vUserFunctions;
		this.userOperators = vUserOperators;
		this.variableNames = variableNames;
		this.implicitMultiplication = true;
	}

	public boolean hasNext() {
		return this.expression.length > pos;
	}

	public Token nextToken() {
		char ch = expression[pos];
		while (Character.isWhitespace(ch)) {
			ch = expression[++pos];
		}
		if (Character.isDigit(ch) || ch == '.') {
			if (lastToken != null) {
				if (lastToken.getType() == Token.TOKEN_NUMBER) {
					throw new IllegalArgumentException(
							"Unable to parse char '" + ch + "' (Code:" + (int) ch + ") at [" + pos + "]");
				} else if (implicitMultiplication && (lastToken.getType() != Token.TOKEN_OPERATOR
						&& lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
						&& lastToken.getType() != Token.TOKEN_FUNCTION
						&& lastToken.getType() != Token.TOKEN_SEPARATOR)) {
					// insert an implicit multiplication token
					lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
					return lastToken;
				}
			}
			return parseNumberToken(ch);
		} else if (isArgumentSeparator(ch)) {
			return parseArgumentSeparatorToken(ch);
		} else if (isOpenParentheses(ch)) {
			if (lastToken != null && implicitMultiplication && (lastToken.getType() != Token.TOKEN_OPERATOR
					&& lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
					&& lastToken.getType() != Token.TOKEN_FUNCTION && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
				// insert an implicit multiplication token
				lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
				return lastToken;
			}
			return parseParentheses(true);
		} else if (isCloseParentheses(ch)) {
			return parseParentheses(false);
		} else if (Operator.isAllowedOperatorChar(ch)) {
			return parseOperatorToken(ch);
		} else if (isAlphabetic(ch) || ch == '_') {
			// parse the name which can be a setVariable or a function
			if (lastToken != null && implicitMultiplication && (lastToken.getType() != Token.TOKEN_OPERATOR
					&& lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
					&& lastToken.getType() != Token.TOKEN_FUNCTION && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
				// insert an implicit multiplication token
				lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
				return lastToken;
			}
			return parseFunctionOrVariable();

		}
		throw new IllegalArgumentException(
				"Unable to parse char '" + ch + "' (Code:" + (int) ch + ") at [" + pos + "]");
	}

	private Token parseArgumentSeparatorToken(char vCh) {
		this.pos++;
		this.lastToken = new ArgumentSeparator();
		return lastToken;
	}

	private boolean isArgumentSeparator(char vCh) {
		return vCh == ',';
	}

	private Token parseParentheses(final boolean vOpen) {
		if (vOpen) {
			this.lastToken = new OpenParentheses();
		} else {
			this.lastToken = new CloseParentheses();
		}
		this.pos++;
		return lastToken;
	}

	private boolean isOpenParentheses(char vCh) {
		return vCh == '(' || vCh == '{' || vCh == '[';
	}

	private boolean isCloseParentheses(char vCh) {
		return vCh == ')' || vCh == '}' || vCh == ']';
	}

	private Token parseFunctionOrVariable() {
		final int offset = this.pos;
		int testPos;
		int lastValidLen = 1;
		Token lastValidToken = null;
		int len = 1;
		if (isEndOfExpression(offset)) {
			this.pos++;
		}
		testPos = offset + len - 1;
		while (!isEndOfExpression(testPos) && isVariableOrFunctionCharacter(expression[testPos])) {
			String name = new String(expression, offset, len);
			if (variableNames != null && variableNames.contains(name)) {
				lastValidLen = len;
				lastValidToken = new Variable(name);
			} else {
				final Function f = getFunction(name);
				if (f != null) {
					lastValidLen = len;
					lastValidToken = new FunctionToken(f);
				}
			}
			len++;
			testPos = offset + len - 1;
		}
		if (lastValidToken == null) {
			throw new UnknownExpressionException(new String(expression), pos, len);
		}
		pos += lastValidLen;
		lastToken = lastValidToken;
		return lastToken;
	}

	private Function getFunction(String vName) {
		Function f = null;
		if (this.userFunctions != null) {
			f = this.userFunctions.get(vName);
		}
		if (f == null) {
			f = Functions.getBuiltinFunction(vName);
		}
		return f;
	}

	private Token parseOperatorToken(char vFirstChar) {
		final int offset = this.pos;
		int len = 1;
		final StringBuilder symbol = new StringBuilder();
		Operator lastValid = null;
		symbol.append(vFirstChar);

		while (!isEndOfExpression(offset + len) && Operator.isAllowedOperatorChar(expression[offset + len])) {
			symbol.append(expression[offset + len++]);
		}

		while (symbol.length() > 0) {
			Operator op = this.getOperator(symbol.toString());
			if (op == null) {
				symbol.setLength(symbol.length() - 1);
			} else {
				lastValid = op;
				break;
			}
		}

		pos += symbol.length();
		lastToken = new OperatorToken(lastValid);
		return lastToken;
	}

	private Operator getOperator(String vSymbol) {
		Operator op = null;
		if (this.userOperators != null) {
			op = this.userOperators.get(vSymbol);
		}
		if (op == null && vSymbol.length() == 1) {
			int argc = 2;
			if (lastToken == null) {
				argc = 1;
			} else {
				int lastTokenType = lastToken.getType();
				if (lastTokenType == Token.TOKEN_PARENTHESES_OPEN || lastTokenType == Token.TOKEN_SEPARATOR) {
					argc = 1;
				} else if (lastTokenType == Token.TOKEN_OPERATOR) {
					final Operator lastOp = ((OperatorToken) lastToken).getOperator();
					if (lastOp.getNumOperands() == 2 || (lastOp.getNumOperands() == 1 && !lastOp.isLeftAssociative())) {
						argc = 1;
					}
				}

			}
			op = Operators.getBuiltinOperator(vSymbol.charAt(0), argc);
		}
		return op;
	}

	private Token parseNumberToken(final char vFirstChar) {
		final int offset = this.pos;
		int len = 1;
		this.pos++;
		if (isEndOfExpression(offset + len)) {
			lastToken = new Number(Double.parseDouble(String.valueOf(vFirstChar)));
			return lastToken;
		}
		while (!isEndOfExpression(offset + len) && isNumeric(expression[offset + len],
				expression[offset + len - 1] == 'e' || expression[offset + len - 1] == 'E')) {
			len++;
			this.pos++;
		}
		// check if the e is at the end
		if (expression[offset + len - 1] == 'e' || expression[offset + len - 1] == 'E') {
			// since the e is at the end it's not part of the number and a
			// rollback is necessary
			len--;
			pos--;
		}
		lastToken = new Number(expression, offset, len);
		return lastToken;
	}

	private static boolean isNumeric(char ch, boolean vLastCharE) {
		return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' || (vLastCharE && (ch == '-' || ch == '+'));
	}

	public static boolean isAlphabetic(int vCodePoint) {
		return Character.isLetter(vCodePoint);
	}

	public static boolean isVariableOrFunctionCharacter(int vCodePoint) {
		return isAlphabetic(vCodePoint) || Character.isDigit(vCodePoint) || vCodePoint == '_' || vCodePoint == '.';
	}

	private boolean isEndOfExpression(int vOffset) {
		return this.expressionLength <= vOffset;
	}
}
