package com.rules.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formula.util.Scanner;
import com.formula.util.Token;
import com.rules.model.RuleParameterModel;

public class Utility {

	public static ArrayList retrievePrameterList(
			String ruleString) throws Exception {
		RuleParameterModel objRuleParameterModel = new RuleParameterModel();
		try {
			/*
			 * Expr expr = null; expr =
			 * Parser.parse("(InternalLabour * LabourHours)+ OverheadCost"
			 * .replaceAll("\\s+", ""));
			 * 
			 * List exprList = (List) expr.value();
			 */

			final String operatorChars = "*/+-^<>=,()";
			Scanner tokens = new Scanner(ruleString, operatorChars);
			Token token = tokens.nextToken();
			ArrayList parameterList = new ArrayList();
			HashMap<String, Object> outputParameterMap = new HashMap<String, Object>();
			while (!tokens.atEnd()) {

				if (token.ttype == token.TT_WORD) {

					if (!token.sval.equals("if")) {
						// System.out.println(token.sval);
						if (!parameterList.contains(token.sval))
							parameterList.add(token.sval);
					}
				}
				token = tokens.nextToken();
			}

			System.out.println("This is main method ");
			/*outputParameterMap.put("Parameters", parameterList);
			outputParameterMap.put("FormulaString",ruleString);*/
			
			return parameterList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList getFormulaList(String ruleString) {
		String conString = "";
		ArrayList formulaList = new ArrayList();
		
		ruleString= ruleString.replaceAll(" ", "");
		ruleString = ruleString+"+";
		boolean hashFlag=false;
		int i = 0;
		while (i < ruleString.length()) {
			if (ruleString.charAt(i) == '+' || ruleString.charAt(i) == '-'
					|| ruleString.charAt(i) == '/'
					|| ruleString.charAt(i) == '^'
					|| ruleString.charAt(i) == '<'
					|| ruleString.charAt(i) == '>'
					|| ruleString.charAt(i) == '='				
					|| ruleString.charAt(i) == '('
					|| ruleString.charAt(i) == ')'
					||ruleString.charAt(i) == ',') {
				
				if(!hashFlag && !conString.equalsIgnoreCase(""))
				formulaList.add(conString);
				conString = "";
			} else if (ruleString.charAt(i) == '$') {
				/*if(i>1)
					conString = conString + ruleString.charAt(i-1);
				hashFlag=false;*/
			}
			else if(ruleString.charAt(i)=='#')
			{
				conString="";
				hashFlag=true;
			}
			
			else {
				conString = conString + ruleString.charAt(i);

			}
			
			

			i++;
		}
		///formulaList.add(conString);

		return formulaList;
	}
	
	public static ArrayList getVariableList(String ruleString)
	{
		//String ruleString="$FormulaTotalRevenue+$FormulaDiscount- #Expense +$FormulaTest + #Product+";
		ruleString= ruleString.replaceAll(" ", "");
		ruleString = ruleString+"+";
		String conString="";
		String varString="";
		ArrayList formulaList = new ArrayList();
		ArrayList variableList = new ArrayList();
		boolean dollarFlag=false;
		
		
		/*final String operatorChars = "+-^<>=,()";
		Scanner tokens = new Scanner(ruleString, operatorChars);
		Token token = tokens.nextToken();
		ArrayList parameterList = new ArrayList();
		HashMap<String, Object> outputParameterMap = new HashMap<String, Object>();
		ruleString="";
		while (!tokens.atEnd()) {

			if (token.ttype == token.TT_WORD) {

				if (!token.sval.equals("if")) {
					// System.out.println(token.sval);
					if (!parameterList.contains(token.sval))
						ruleString = ruleString+token.sval;
				}
			}
			token = tokens.nextToken();
		}*/
		
		
		
		
		
		if(ruleString.contains("$")||ruleString.contains("#"))
		{
			int dollarIndex = ruleString.indexOf("$");
			int i=0;
			while (i<ruleString.length())
			{	
				
				if(ruleString.charAt(i)=='#' && ((ruleString.charAt(i-1)!=')'&& ruleString.charAt(i-1)!='(' )))
				{
					dollarFlag=false;
					varString = varString +  ruleString.charAt(i-1);
				}
				else
					if(ruleString.charAt(i)=='#')
					{
						dollarFlag=false;
						//varString = varString +  ruleString.charAt(i-1);
					}
				else if(ruleString.charAt(i)=='i' || ruleString.charAt(i)=='f'||ruleString.charAt(i)=='('||ruleString.charAt(i)==')')
				{
					
				}
					
				
				else if(ruleString.charAt(i)=='+'||ruleString.charAt(i)=='-'|| ruleString.charAt(i)=='/'||
				ruleString.charAt(i)=='^'||ruleString.charAt(i)=='<'|| ruleString.charAt(i)=='>'||
				ruleString.charAt(i)=='='||ruleString.charAt(i)==',' )
				{	if (!varString.equalsIgnoreCase("")) 
					variableList.add(varString);
					varString = "";					
					
				}
				
				else if(ruleString.charAt(i)=='$')
				{
					varString="";
					dollarFlag=true;
				}
				else {
					if(!dollarFlag)
					varString = varString + ruleString.charAt(i);

				}
				
				i++;
			}
			
			/*for (int j = 0; j < variableList.size(); j++) {
				
				System.out.println(variableList.get(j));
			}
			*/
			
			
		}
		/*else
		{
			
			int i=0;
			while (i<ruleString.length())
			{	
				
				if(ruleString.charAt(i)=='#')
				{
					dollarFlag=false;
					varString = varString +  ruleString.charAt(i-1);
				}
				else if(ruleString.charAt(i)=='+'||ruleString.charAt(i)=='-'|| ruleString.charAt(i)=='/'||
						ruleString.charAt(i)=='^'||ruleString.charAt(i)=='<'|| ruleString.charAt(i)=='>'||
						ruleString.charAt(i)=='='||ruleString.charAt(i)==','|| ruleString.charAt(i)=='('||ruleString.charAt(i)==')')
						{	if (!varString.equalsIgnoreCase("")) 
							variableList.add(varString);
							varString = "";					
							
						}
				else {
					
					varString = varString + ruleString.charAt(i);

				}
				i++;
			}
			}*/
		return variableList;
	}
	
	
	public static String getTokeString(String ruleString) {
		final String operatorChars = "*/+-^<>=,()";
		Scanner tokens = new Scanner(ruleString, operatorChars);
		Token token = tokens.nextToken();
		ArrayList parameterList = new ArrayList();
		HashMap<String, Object> outputParameterMap = new HashMap<String, Object>();
		String outputString = "";
		String compareString="";
		while (!tokens.atEnd()) {

			if (token.ttype == token.TT_WORD) {

				if (!token.sval.equals("if")) {
					// System.out.println(token.sval);
					if (!parameterList.contains(token.sval))
						// parameterList.add(token.sval);
						outputString = outputString + token.sval;
				}
			} /*else if (token.ttype == token.TT_NUMBER) {
				// parameterList.add(token.sval);
				outputString = outputString + token.sval;
			}*/
			else if
			(token.sval.equals(">") || token.sval.equals("<")
					|| token.sval.equals("=")||token.ttype == token.TT_NUMBER)
			{
				compareString = compareString + token.sval;
			}

			if (token.sval.equals("$") || token.sval.equals("#")
					|| token.sval.equals("+")
					|| token.sval.equals("-"))

			{
				// parameterList.add(token.sval);
				outputString = outputString + token.sval;
			}
			

			token = tokens.nextToken();
		}

		return outputString+"~"+compareString;
	}
	
	
	
	public static ArrayList getVariableListCond(String ruleString)
	{
		//String ruleString="$FormulaTotalRevenue+$FormulaDiscount- #Expense +$FormulaTest + #Product+";
		ruleString= ruleString.replaceAll(" ", "");
		ruleString = ruleString+"+";
		String conString="";
		String varString="";
		ArrayList formulaList = new ArrayList();
		ArrayList variableList = new ArrayList();
		boolean dollarFlag=false;
		
		
		/*final String operatorChars = "+-^<>=,()";
		Scanner tokens = new Scanner(ruleString, operatorChars);
		Token token = tokens.nextToken();
		ArrayList parameterList = new ArrayList();
		HashMap<String, Object> outputParameterMap = new HashMap<String, Object>();
		ruleString="";
		while (!tokens.atEnd()) {

			if (token.ttype == token.TT_WORD) {

				if (!token.sval.equals("if")) {
					// System.out.println(token.sval);
					if (!parameterList.contains(token.sval))
						ruleString = ruleString+token.sval;
				}
			}
			token = tokens.nextToken();
		}*/
		
		
		
		
		
		if(ruleString.contains("$")||ruleString.contains("#"))
		{
			int dollarIndex = ruleString.indexOf("$");
			int i=0;
			while (i<ruleString.length())
			{	
				
				if(ruleString.charAt(i)=='#' && ((ruleString.charAt(i-1)!=')'&& ruleString.charAt(i-1)!='(' )))
				{
					dollarFlag=false;
					varString = varString +  ruleString.charAt(i-1);
				}
				else
					if(ruleString.charAt(i)=='#')
					{
						dollarFlag=false;
						//varString = varString +  ruleString.charAt(i-1);
					}
				else if(ruleString.charAt(i)=='i' || ruleString.charAt(i)=='f'||ruleString.charAt(i)=='('||ruleString.charAt(i)==')')
				{
					
				}
				else if (Character.isDigit(ruleString.charAt(i))) {
					
				}	
				
				else if(ruleString.charAt(i)=='+'||ruleString.charAt(i)=='-'|| ruleString.charAt(i)=='/'||
				ruleString.charAt(i)=='^'||ruleString.charAt(i)=='<'|| ruleString.charAt(i)=='>'||
				ruleString.charAt(i)=='='||ruleString.charAt(i)==',' )
				{	if (!varString.equalsIgnoreCase("")) 
					variableList.add(varString);
					varString = "";					
					
				}
				
				else if(ruleString.charAt(i)=='$')
				{
					varString="";
					dollarFlag=true;
				}
				else {
					if(!dollarFlag)
					varString = varString + ruleString.charAt(i);

				}
				
				i++;
			}
			
			/*for (int j = 0; j < variableList.size(); j++) {
				
				System.out.println(variableList.get(j));
			}
			*/
			
			
		}
		/*else
		{
			
			int i=0;
			while (i<ruleString.length())
			{	
				
				if(ruleString.charAt(i)=='#')
				{
					dollarFlag=false;
					varString = varString +  ruleString.charAt(i-1);
				}
				else if(ruleString.charAt(i)=='+'||ruleString.charAt(i)=='-'|| ruleString.charAt(i)=='/'||
						ruleString.charAt(i)=='^'||ruleString.charAt(i)=='<'|| ruleString.charAt(i)=='>'||
						ruleString.charAt(i)=='='||ruleString.charAt(i)==','|| ruleString.charAt(i)=='('||ruleString.charAt(i)==')')
						{	if (!varString.equalsIgnoreCase("")) 
							variableList.add(varString);
							varString = "";					
							
						}
				else {
					
					varString = varString + ruleString.charAt(i);

				}
				i++;
			}
			}*/
		return variableList;
	}
	
	
	// http://localhost:8080/RESTfulExample/json/product/get
		public static double serviceGETCall(HashMap<String, String> inputData,
				String serviceURL) {

			try {

				System.out.println(serviceURL + "?" + mapToString(inputData));

				URL url = new URL(serviceURL + "?" + mapToString(inputData));
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				// conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				double answer=0.0;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					answer = Double.parseDouble(output);
				}

				conn.disconnect();
				return answer;

			} catch (MalformedURLException e) {

				e.printStackTrace();
				return 0.0;

			} catch (IOException e) {

				e.printStackTrace();
				return 0.0;

			}

		}

	

	
	
		public static double servicePostCall(HashMap<String, String> inputData,
				String serviceURL) {

			try {

				URL url = new URL(serviceURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

			/*	HashMap<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("qty", "100");
				inputMap.put("name", "iPad 4");*/

				ObjectMapper mapper = new ObjectMapper();

				// Object to JSON in String
				String jsonInString = mapper.writeValueAsString(inputData);

				System.out.println(jsonInString);

				OutputStream os = conn.getOutputStream();
				os.write(jsonInString.getBytes());
				os.flush();

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				double answer=0.0;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					answer = Double.parseDouble(output);
				}

				conn.disconnect();
				return answer;
			} catch (MalformedURLException e) {

				e.printStackTrace();
				return 0.0;

			} catch (IOException e) {

				e.printStackTrace();
				return 0.0;
			}
		}
	
	public static String mapToString(HashMap<String, String> map) {
		StringBuilder stringBuilder = new StringBuilder();

		for (String key : map.keySet()) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append("&");
			}
			String value = map.get(key);
			try {
				stringBuilder.append((key != null ? URLEncoder.encode(key,
						"UTF-8") : ""));
				stringBuilder.append("=");
				stringBuilder.append(value != null ? URLEncoder.encode(value,
						"UTF-8") : "");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"This method requires UTF-8 encoding support", e);
			}
		}

		return stringBuilder.toString();
	}

	public static HashMap<String, String> stringToMap(String input) {
		HashMap<String, String> map = new HashMap<String, String>();

		String[] nameValuePairs = input.split("&");
		for (String nameValuePair : nameValuePairs) {
			String[] nameValue = nameValuePair.split("=");
			try {
				map.put(URLDecoder.decode(nameValue[0], "UTF-8"),
						nameValue.length > 1 ? URLDecoder.decode(nameValue[1],
								"UTF-8") : "");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"This method requires UTF-8 encoding support", e);
			}
		}

		return map;
	}

	
	/*public static void main (String args[])
	{
		
		
		HashMap<String,String> inputData = new HashMap<String,String>(); 
		String serviceURL="http://localhost:8080/rulesEngine/getDiscountMargin?CustomerName=";
		
		inputData.put("CustomerName","British Airways");
		try
		{
			serviceGETCall(inputData,serviceURL);
		}
		catch(Exception e)
		{
			
		}
	}*/
}
