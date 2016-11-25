package com.formula.util;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopFormula {
	private static final Logger logger = LoggerFactory.getLogger(LoopFormula.class);
	int i = 0;

	public int scanTokenExtn(String inputString, String operatorChars) {
		while (i < inputString.length()) {

			if (i + 1 < inputString.length()) {
				String pair = inputString.substring(i, i + 2);
				logger.info(pair);
				int ttype = 0;
				if (pair.equals("<="))
					ttype = Token.TT_LE;
				else if (pair.equals(">="))
					ttype = Token.TT_GE;
				else if (pair.equals("<>"))
					ttype = Token.TT_NE;
				if (0 != ttype) {
					return ttype;
				}
			}

			i++;
		}
		return i;
	}

	public void formulToken() {

		String expression = "if(InternalRepair>= ExternalRepair),Internal*05,100)";
		String operatorChars = "*/+-^<>=,()";
		try {
			Scanner tokens = new Scanner(expression, operatorChars);

			Token token = tokens.nextToken();
			while (!tokens.atEnd()) {
				logger.info("Type " + token.ttype);
				if (token.ttype == token.TT_WORD) {
					logger.info(token.sval);
					if (!token.sval.equals("if"))
						logger.info("Found :  " + token.sval);
				}

				if (token.ttype == token.TT_LE)
					logger.info("TT_LE");
				if (token.ttype == token.TT_NE)
					logger.info("TT_NE");
				if (token.ttype == token.TT_GE)
					logger.info("TT_GE");

				token = tokens.nextToken();
			}
		} catch (Exception e) {
		}
	}

	public double calculateFormula(String expression) {
		Expr expr;
		try {
			expr = Parser.parse(expression);
		} catch (SyntaxException e) {
			e.printStackTrace();
			return 0;
		}

		HashMap<String, Integer> map1 = new HashMap<String, Integer>();
		map1.put("InternalRepair", 100);
		replaceLexicalTokens(expression, map1);
		return (double)expr.value();
	}

	private void replaceLexicalTokens(String expression, HashMap<String, Integer> map1) {
		final String operatorChars = "*/+-^<>=,()";
		Scanner tokens = new Scanner(expression, operatorChars);
		Token token = tokens.nextToken();
		while (!tokens.atEnd()) {
			if (token.ttype == token.TT_WORD) {
				logger.info(token.sval);
				if (!token.sval.equals("if")) {
					logger.info("Found :  " + token.sval);
					Variable.make(token.sval).setValue(variableValueMapper(token.sval, map1));
				}
			}
			token = tokens.nextToken();
		}
	}

	private double variableValueMapper(String token, HashMap<String, Integer> map1) {
		// ICAM:AvgOfLLP_Cost:Cost:QT
		double returnValue = 0;

		java.util.Set<String> set1 = map1.keySet();

		String[] args = token.split(":");
		switch (args[0]) {
		default:
			returnValue = 10;
			break;
		}
		return map1.get(token);

	}
}
