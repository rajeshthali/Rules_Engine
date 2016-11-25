package com.rules.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RuleParameterModel {

	private String ruleName;
	private String ruleCondition;
	private String ruleAction;
	private String ruleOperator;
	private double outputValue;
	private String serviceName;
	private String serviceURL;
	private String serviceMethod;
	private HashMap serviceParam;
	
	public double getOutputValue() {
		return outputValue;
	}
	public void setOutputValue(double outputValue) {
		this.outputValue = outputValue;
	}
	LinkedHashMap<String, String> ruleValues = new LinkedHashMap<>();
	
	public LinkedHashMap<String, String> getRuleValues() {
		return ruleValues;
	}
	public void setRuleValues(LinkedHashMap<String, String> ruleValues) {
		this.ruleValues = ruleValues;
	}
	public ArrayList<String> getFormulaParameter() {
		return formulaParameter;
	}
	public void setFormulaParameter(ArrayList<String> formulaParameter) {
		this.formulaParameter = formulaParameter;
	}
	public ArrayList<String> getConditionParameter() {
		return conditionParameter;
	}
	public void setConditionParameter(ArrayList<String> conditionParameter) {
		this.conditionParameter = conditionParameter;
	}
	private ArrayList <String> formulaParameter;
	private ArrayList <String> conditionParameter;
	private String ruleFormulaString;
	public String getRuleFormulaString() {
		return ruleFormulaString;
	}
	public void setRuleFormulaString(String ruleFormulaString) {
		this.ruleFormulaString = ruleFormulaString;
	}
	public String getConditionFormulaString() {
		return conditionFormulaString;
	}
	public void setConditionFormulaString(String conditionFormulaString) {
		this.conditionFormulaString = conditionFormulaString;
	}
	private String conditionFormulaString;
	private int ruleId;
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleCondition() {
		return ruleCondition;
	}
	public void setRuleCondition(String ruleCondition) {
		this.ruleCondition = ruleCondition;
	}
	public String getRuleAction() {
		return ruleAction;
	}
	public void setRuleAction(String ruleAction) {
		this.ruleAction = ruleAction;
	}
	public String getRuleOperator() {
		return ruleOperator;
	}
	public void setRuleOperator(String ruleOperator) {
		this.ruleOperator = ruleOperator;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceURL() {
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	public HashMap getServiceParam() {
		return serviceParam;
	}
	public void setServiceParam(HashMap serviceParam) {
		this.serviceParam = serviceParam;
	}
	
	
	
}
