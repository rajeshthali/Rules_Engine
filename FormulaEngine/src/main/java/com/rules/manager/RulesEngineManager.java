package com.rules.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.formula.entity.Formula;
import com.formula.model.FormulaGroupObject;
import com.formula.service.FormulaEngineService;
import com.formula.util.Expr;
import com.formula.util.Parser;
import com.formula.util.SyntaxException;
import com.rules.dao.RulesEngineDao;
import com.rules.entity.RuleServiceEntity;
import com.rules.entity.RulesEntity;
import com.rules.model.RuleParameterModel;
import com.rules.model.RulesDbConDetailsObj;
import com.rules.model.RulesDbDataObj;
import com.rules.model.RulesEngineObject;
import com.rules.model.RulesRequestModel;
import com.rules.util.Utility;

@Component
public class RulesEngineManager {

	@Autowired
	RulesEngineDao rulesEngineDao;

	@PersistenceContext
	EntityManager em;

	public RulesRequestModel saveRule(RulesRequestModel rulesRequestModel)
			throws Exception {

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		rulesRequestModel = rulesEngineDao.saveRule(rulesRequestModel,
				entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return rulesRequestModel;
	}

	public String deleteRule(String ruleId) throws Exception {

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		String result = rulesEngineDao.deleteRule(ruleId, entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public List<RulesRequestModel> retrieveAllRules() throws Exception {

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		List<RulesRequestModel> result = rulesEngineDao
				.retrieveAllRules(entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public RulesRequestModel ruleDetailsByRuleGroupId(String ruleGroupId)
			throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		RulesRequestModel result = rulesEngineDao.ruleDetailsByRuleGroupId(
				ruleGroupId, entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public RulesRequestModel ruleDetailsByRuleId(String ruleId)
			throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		RulesRequestModel result = rulesEngineDao.ruleDetailsByRuleId(ruleId,
				entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public RulesDbConDetailsObj addDbConnections(
			RulesDbConDetailsObj rulesDbConDetailsObj) throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		RulesDbConDetailsObj result = rulesEngineDao.addDbConnections(
				rulesDbConDetailsObj, entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public RulesDbDataObj addDBQueryFunction(RulesDbDataObj rulesDbDataObj)
			throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		RulesDbDataObj result = rulesEngineDao.addDBQueryFunction(
				rulesDbDataObj, entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public RulesDbDataObj callDBQueryFunction(RulesDbDataObj rulesDbDataObj)
			throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		RulesDbDataObj result = rulesEngineDao.callDBQueryFunction(
				rulesDbDataObj, entitymanager);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		factory.close();

		return result;
	}

	public HashMap<String, Object> executeRule(
			RuleParameterModel objRuleParameterModel,
			EntityManagerFactory factory, EntityManager entitymanager)
			throws Exception {
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		try {

			if (objRuleParameterModel.getRuleOperator()
					.equalsIgnoreCase("CALC")) {

				objRuleParameterModel = calculateFormulaValues(
						objRuleParameterModel, entitymanager, factory);
				outputMap.put("ruleId", objRuleParameterModel.getRuleId());
				outputMap.put("ruleName", objRuleParameterModel.getRuleName());
				outputMap.put("ruleOperator",
						objRuleParameterModel.getRuleOperator());
				outputMap.put("conditionFormulaString",
						objRuleParameterModel.getConditionFormulaString());
				outputMap.put("ruleFormulaString",
						objRuleParameterModel.getRuleFormulaString());
				outputMap.put("ruleValues",
						objRuleParameterModel.getRuleValues());
				outputMap.put("outputValue",
						objRuleParameterModel.getOutputValue());
			} else if (objRuleParameterModel.getRuleOperator()
					.equalsIgnoreCase("EXEC")) {

				objRuleParameterModel = executeServiceCall(
						objRuleParameterModel, entitymanager, factory);

				objRuleParameterModel = calculateFormulaServiceValues(
						objRuleParameterModel, entitymanager, factory);

				outputMap.put("ruleId", objRuleParameterModel.getRuleId());
				outputMap.put("ruleName", objRuleParameterModel.getRuleName());
				outputMap.put("ruleOperator",
						objRuleParameterModel.getRuleOperator());
				outputMap.put("conditionFormulaString",
						objRuleParameterModel.getConditionFormulaString());
				outputMap.put("ruleValues",
						objRuleParameterModel.getRuleValues());
				outputMap.put("serviceParam",
						objRuleParameterModel.getServiceParam());
				outputMap.put("outputValue",
						objRuleParameterModel.getOutputValue());

			}
			return outputMap;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public RuleParameterModel calculateFormulaServiceValues(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager, EntityManagerFactory factory) {
		String formulaExpression = objRuleParameterModel
				.getConditionFormulaString()
				+ ","
				+ objRuleParameterModel.getOutputValue() + ",0)";
		formulaExpression = formulaExpression.replace("if", "if(");
		System.out.println("formulaExpression " + formulaExpression);
		double answer = getFormulaValueExpr(formulaExpression,
				objRuleParameterModel.getRuleValues(), factory, entitymanager);

		System.out.println("Answer " + answer);

		objRuleParameterModel.setOutputValue(answer);
		return objRuleParameterModel;
	}

	public RuleParameterModel calculateFormulaValues(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager, EntityManagerFactory factory) {
		String formulaExpression = objRuleParameterModel
				.getConditionFormulaString()
				+ ","
				+ objRuleParameterModel.getRuleFormulaString() + ")";
		formulaExpression = formulaExpression.replace("if", "if(");

		double answer = getFormulaValueExpr(formulaExpression,
				objRuleParameterModel.getRuleValues(), factory, entitymanager);

		System.out.println("Answer " + answer);

		objRuleParameterModel.setOutputValue(answer);
		return objRuleParameterModel;
	}

	public RuleParameterModel executeServiceCall(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager, EntityManagerFactory factory)
			throws Exception {
		double outputValue = 0.0;
		try {

			RuleServiceEntity objRuleServiceEntity = rulesEngineDao
					.getRuleServiceDetails(objRuleParameterModel.getRuleId(),
							entitymanager);

			HashMap<String, String> inputData = new HashMap<String, String>();
			String serviceURL = "";

			// / inputData.put("CustomerName","British Airways");
			objRuleServiceEntity.setServiceType("POST");
			if (objRuleServiceEntity.getServiceType().equalsIgnoreCase("GET")) {
				serviceURL = "http://localhost:8080/getDiscountMargin?CustomerName=";
				outputValue = Utility.serviceGETCall(
						objRuleParameterModel.getServiceParam(), serviceURL);

			} else if (objRuleServiceEntity.getServiceType().equalsIgnoreCase(
					"POST")) {
				// /serviceURL="http://localhost:8080/getCustomerDiscount";
				outputValue = Utility.servicePostCall(
						objRuleParameterModel.getServiceParam(),
						objRuleServiceEntity.getServiceURL());
			}
			/*
			 * double outputValue = Utility.serviceGETCall(
			 * objRuleParameterModel.getRuleValues(),
			 * objRuleServiceEntity.getServiceURL());
			 */
			objRuleParameterModel.setOutputValue(outputValue);
			return objRuleParameterModel;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private double getFormulaValueExpr(String formulaExpression,
			LinkedHashMap<String, String> formulaPrevValues,
			EntityManagerFactory factory, EntityManager entitymanager) {

		Expr expr = null;
		FormulaEngineService objFormulaServiceImpl = new FormulaEngineService();

		/*
		 * Formula formualDetail =
		 * getFormulaString(objFormula.getFormulaDescription(), formulaGroup,
		 * version, factory, entitymanager);
		 */

		String formulaExpr = formulaExpression;
		HashMap<String, String> formulaValuesMap = formulaPrevValues;

		HashMap<String, Double> valueMap = new HashMap<String, Double>();
		HashMap<String, String> agrValueMap = new HashMap<String, String>();
		HashMap<String, Date> dateValueMap = new HashMap<String, Date>();

		if (formulaValuesMap != null && formulaValuesMap.size() > 0) {
			Set baseSet = formulaValuesMap.keySet();
			Iterator irr = baseSet.iterator();

			while (irr.hasNext()) {

				String key = (String) irr.next();
				String string = formulaValuesMap.get(key);

				if (string.contains(",")) {
					agrValueMap.put(key.replaceAll("\\s+", ""), string);
				} else if (string.contains("/")) {
					try {
						String dateString = string;
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
						Date date = df.parse(dateString);
						dateValueMap.put(key.replaceAll("\\s+", ""), date);
					} catch (ParseException e) {
						System.out
								.println("Exception occured while parsing Dates for "
										+ string);
					}
				} else {
					String value = formulaValuesMap.get(key);
					valueMap.put(key.replaceAll("\\s+", ""), new Double(value));
				}
			}
		}
		if (formulaPrevValues != null && formulaPrevValues.size() > 0) {
			Set baseSet1 = formulaPrevValues.keySet();
			Iterator irr1 = baseSet1.iterator();

			while (irr1.hasNext()) {

				String key1 = (String) irr1.next();
				String value1 = formulaPrevValues.get(key1);

				valueMap.put(key1.replaceAll("\\s+", ""), new Double(value1));

			}
		}

		try {
			expr = Parser.parse(formulaExpr.replaceAll("\\s+", ""));
		} catch (SyntaxException e) {
			// logger.error("inside class: FormulaEngineService.java,  method: getFormulaValueExpr, message :: "
			// , e);
		}

		objFormulaServiceImpl.replaceLexicalTokens(
				formulaExpr.replaceAll("\\s+", ""), valueMap, agrValueMap,
				dateValueMap);
		double answer = (double) expr.value();
		// logger.info("inside class: FormulaEngineService.java,  method: getFormulaValueExpr, message :: Exit");
		return answer;

	}

	public HashMap<String, Object> retrievePrameterList(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager) throws Exception {
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		try {

			if (objRuleParameterModel.getRuleOperator()
					.equalsIgnoreCase("CALC")) {

				objRuleParameterModel = getCalcParameterList(
						objRuleParameterModel, entitymanager);

				outputMap.put("ruleId", objRuleParameterModel.getRuleId());
				outputMap.put("ruleName", objRuleParameterModel.getRuleName());
				outputMap.put("ruleAction",
						objRuleParameterModel.getRuleAction());
				outputMap.put("ruleCondition",
						objRuleParameterModel.getRuleCondition());
				outputMap.put("formulaParameter",
						objRuleParameterModel.getFormulaParameter());
				outputMap.put("conditionParameter",
						objRuleParameterModel.getConditionParameter());
				outputMap.put("ruleFormulaString",
						objRuleParameterModel.getRuleFormulaString());
				outputMap.put("conditionFormulaString",
						objRuleParameterModel.getConditionFormulaString());

			} else if (objRuleParameterModel.getRuleOperator()
					.equalsIgnoreCase("EXEC")) {

				objRuleParameterModel = getExecParameter(objRuleParameterModel,
						entitymanager);

				objRuleParameterModel = getConditionString(
						objRuleParameterModel, entitymanager);

				outputMap.put("ruleId", objRuleParameterModel.getRuleId());
				outputMap.put("ruleName", objRuleParameterModel.getRuleName());
				outputMap.put("ruleOperator",
						objRuleParameterModel.getRuleOperator());
				outputMap.put("formulaParameter",
						objRuleParameterModel.getFormulaParameter());
				outputMap.put("serviceURL",
						objRuleParameterModel.getServiceURL());
				outputMap.put("serviceType",
						objRuleParameterModel.getServiceMethod());
				outputMap.put("ruleCondition",
						objRuleParameterModel.getRuleCondition());
				outputMap.put("conditionFormulaString",
						objRuleParameterModel.getConditionFormulaString());
				outputMap.put("conditionParameter",
						objRuleParameterModel.getConditionParameter());

			}
			return outputMap;
		} catch (Exception e) {

		}
		return null;
	}

	public RuleParameterModel getExecParameter(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager) {

		ArrayList<String> serviceParamList = new ArrayList<String>();
		try {
			RuleServiceEntity objRuleServiceEntity = rulesEngineDao
					.getRuleServiceDetails(objRuleParameterModel.getRuleId(),
							entitymanager);

			if (objRuleServiceEntity.getServiceParam().contains(";")) {
				String[] serviceParam = objRuleServiceEntity.getServiceParam()
						.split(";");
				for (int i = 0; i < serviceParam.length; i++) {
					serviceParamList.add(serviceParam[i]);
				}
			} else
				serviceParamList.add(objRuleServiceEntity.getServiceParam());
			objRuleParameterModel.setFormulaParameter(serviceParamList);
			objRuleParameterModel.setServiceURL(objRuleServiceEntity
					.getServiceURL());
			objRuleParameterModel.setServiceMethod(objRuleServiceEntity
					.getServiceType());
			return objRuleParameterModel;
		} catch (Exception e) {
			return null;
		}

	}

	public RuleParameterModel getCalcParameterList(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager) {
		String ruleString = "";
		String conditionString = "";
		RulesEntity objRulesEntity = new RulesEntity();
		ArrayList formulaList = new ArrayList();
		ArrayList variableList = new ArrayList();
		FormulaEngineService objFormulaService = new FormulaEngineService();
		Formula objformula = new Formula();
		// RuleParameterModel objRuleParameterModel = new RuleParameterModel();
		try {

			objRulesEntity = rulesEngineDao.getRuleGroupObject(
					objRuleParameterModel.getRuleId(), entitymanager);

			String ruleAction = objRulesEntity.getRuleActionText();
			System.out.println("ruleAction " + ruleAction);
			if (ruleAction.contains("$")) {
				formulaList = Utility.getFormulaList(ruleAction);
				variableList = Utility.getVariableList(ruleAction);
				for (int i = 0; i < formulaList.size(); i++) {
					String formulaString = formulaList.get(i).toString();
					if (formulaString.charAt(0) == '+'
							|| formulaString.charAt(0) == '-'
							|| formulaString.charAt(0) == '*'
							|| formulaString.charAt(0) == '\\') {

						objformula = objFormulaService
								.searchByFormulaName(formulaString.substring(1,
										formulaString.length()));

						ruleString = ruleString + formulaString.charAt(0)
								+ objformula.getFormulaString();

					} else if (formulaString.charAt(0) == ',') {
						ruleString = ruleString + formulaString.charAt(0);
					} else {
						objformula = objFormulaService
								.searchByFormulaName(formulaString);
						ruleString = ruleString + objformula.getFormulaString();
					}

					if (i < formulaList.size() - 1)
						ruleString = ruleString + ",";
					System.out.println("i " + i + " \t" + formulaString);
					System.out.println("i " + i + " \t" + ruleString);
					/*
					 * ruleString = ruleString +
					 * objFormulaService.searchByFormulaName(formulaList
					 * .get(i).toString());
					 */
				}

				for (int i = 0; i < variableList.size(); i++) {
					String variableString = variableList.get(i).toString();
					ruleString = ruleString + variableString;
				}

			} else {

				variableList = Utility.getVariableList(ruleAction);
				for (int i = 0; i < variableList.size() - 1; i++) {
					ruleString = ruleString + variableList.get(i).toString();
				}
			}
			System.out.println("Final Rule String " + ruleString);

			String ruleCondition = objRulesEntity.getRuleConditionText();
			System.out.println("ruleCondition " + ruleCondition);

			String outputString = Utility.getTokeString(ruleCondition);

			String conditonArray[] = outputString.split("~");

			outputString = conditonArray[0];

			if (outputString.contains("$")) {
				formulaList = Utility.getFormulaList(outputString);
				variableList = Utility.getVariableListCond(outputString);
				for (int i = 0; i < formulaList.size(); i++) {
					String formulaString = formulaList.get(i).toString();
					if (formulaString.charAt(0) == '+'
							|| formulaString.charAt(0) == '-'
							|| formulaString.charAt(0) == '*'
							|| formulaString.charAt(0) == '\\') {

						objformula = objFormulaService
								.searchByFormulaName(formulaString.substring(1,
										formulaString.length()));

						conditionString = conditionString
								+ formulaString.charAt(0)
								+ objformula.getFormulaString();

					} else {
						objformula = objFormulaService
								.searchByFormulaName(formulaString);
						conditionString = conditionString
								+ objformula.getFormulaString();
					}

					System.out.println("i " + i + " \t" + formulaString);
					System.out.println("i " + i + " \t" + conditionString);
					/*
					 * ruleString = ruleString +
					 * objFormulaService.searchByFormulaName(formulaList
					 * .get(i).toString());
					 */
				}

				for (int i = 0; i < variableList.size(); i++) {
					String variableString = variableList.get(i).toString();
					conditionString = conditionString + variableString;
				}

			}

			System.out.println("conditionString " + "if(" + conditionString
					+ ")" + conditonArray[1]);

			ArrayList parameterList = Utility.retrievePrameterList(ruleString);
			ArrayList parameterListCondition = Utility
					.retrievePrameterList(conditionString);

			objRuleParameterModel.setRuleName(objRulesEntity.getRuleName());
			objRuleParameterModel.setFormulaParameter(parameterList);

			objRuleParameterModel.setRuleFormulaString(ruleString);
			objRuleParameterModel.setRuleId(objRulesEntity.getRuleId());
			objRuleParameterModel.setRuleAction(ruleAction);
			objRuleParameterModel.setRuleCondition(objRulesEntity
					.getRuleConditionText());
			objRuleParameterModel.setConditionParameter(parameterListCondition);
			objRuleParameterModel.setConditionFormulaString("if("
					+ conditionString + ")" + conditonArray[1]);

			return objRuleParameterModel;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public RuleParameterModel getConditionString(
			RuleParameterModel objRuleParameterModel,
			EntityManager entitymanager) {

		String ruleString = "";
		String conditionString = "";
		RulesEntity objRulesEntity = new RulesEntity();
		ArrayList formulaList = new ArrayList();
		ArrayList variableList = new ArrayList();
		FormulaEngineService objFormulaService = new FormulaEngineService();
		Formula objformula = new Formula();

		try {

			objRulesEntity = rulesEngineDao.getRuleGroupObject(
					objRuleParameterModel.getRuleId(), entitymanager);
			String ruleCondition = objRulesEntity.getRuleConditionText();
			System.out.println("ruleCondition " + ruleCondition);

			String outputString = Utility.getTokeString(ruleCondition);

			String conditonArray[] = outputString.split("~");

			outputString = conditonArray[0];

			if (outputString.contains("$")) {
				formulaList = Utility.getFormulaList(outputString);
				variableList = Utility.getVariableListCond(outputString);
				for (int i = 0; i < formulaList.size(); i++) {
					String formulaString = formulaList.get(i).toString();
					if (formulaString.charAt(0) == '+'
							|| formulaString.charAt(0) == '-'
							|| formulaString.charAt(0) == '*'
							|| formulaString.charAt(0) == '\\') {

						objformula = objFormulaService
								.searchByFormulaName(formulaString.substring(1,
										formulaString.length()));

						conditionString = conditionString
								+ formulaString.charAt(0)
								+ objformula.getFormulaString();

					} else {
						objformula = objFormulaService
								.searchByFormulaName(formulaString);
						conditionString = conditionString
								+ objformula.getFormulaString();
					}

					System.out.println("i " + i + " \t" + formulaString);
					System.out.println("i " + i + " \t" + conditionString);
					/*
					 * ruleString = ruleString +
					 * objFormulaService.searchByFormulaName(formulaList
					 * .get(i).toString());
					 */
				}

				for (int i = 0; i < variableList.size(); i++) {
					String variableString = variableList.get(i).toString();
					conditionString = conditionString + variableString;
				}

			}

			System.out.println("conditionString " + "if(" + conditionString
					+ ")" + conditonArray[1]);

			ArrayList parameterList = Utility.retrievePrameterList(ruleString);
			ArrayList parameterListCondition = Utility
					.retrievePrameterList(conditionString);

			objRuleParameterModel.setRuleName(objRulesEntity.getRuleName());
			// /objRuleParameterModel.setFormulaParameter(parameterList);
			objRuleParameterModel.setRuleId(objRulesEntity.getRuleId());

			objRuleParameterModel.setRuleCondition(objRulesEntity
					.getRuleConditionText());
			objRuleParameterModel.setConditionParameter(parameterListCondition);
			objRuleParameterModel.setConditionFormulaString("if("
					+ conditionString + ")" + conditonArray[1]);

			return objRuleParameterModel;

		} catch (Exception e) {
			return null;
		}
	}

}
