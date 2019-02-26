package com.example.java8.string.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.java8.string.calculator.build.MathematicalExpressionBuilder;
import com.example.java8.string.calculator.build.MathematicalStringExpression;
import com.example.java8.string.calculator.error.InvalidExpressionException;

public class ScientificStringExpressionCalculator {
	
	 private static String MathExpresionEvaluation(String input) {
		 String result=null;
		 DecimalFormat decimalFormat = new DecimalFormat("0.#");
		 try{
			 MathematicalStringExpression exp = new MathematicalExpressionBuilder(input).build();	
			 result= decimalFormat.format(exp.evaluate()); 
		 }catch(InvalidExpressionException ex){
			 result="INVALID EXPRESSION";
		 }finally{
			 if(result!=null){
				 return result;
			 } 
		 }
		return result;
     }
	 
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int N = Integer.parseInt(line);
        ArrayList<String> inputs = new ArrayList<String>();
        for (int i = 0; i < N; i++) {
            inputs.add(br.readLine());
        }
        
        ArrayList<String> outputs = new ArrayList<String>();
        for(String input : inputs){
            outputs.add(MathExpresionEvaluation(input));
        }
        for(String result: outputs){
            System.out.println(result);
        }
	}
}
