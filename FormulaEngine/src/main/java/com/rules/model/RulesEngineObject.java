package com.rules.model;

import java.util.Map;

import javax.persistence.Column;

/*
 * Rule Engine Object, used to save the object in Database
 */
public class RulesEngineObject{

	private String ruleId;
	private String ruleName;
	private String ruleDesc;
	private String ruleValidFrom;
	private String ruleValidTo;
	private String ruleVersion="1.0.0";
	
	private String ruleText;  // USER UNDERSTANDABLE STRING IF A > B AND C>D THEN FORMULA
	private String ruleOperator; // CALL/EXEC
	private String ruleConditionText;   ///// if($formula1>$formula2) 
	private String ruleActionText; ///formula3,formula4;
	
	private int formulaGroupId;
	
	private String ruleFormulaGroup;
	private String ruleFormulaGroupVersion;
	
	
	private String ruleEmailList;//service availability check
	private int externalServiceUrlId;//call the external service User Story 4
	
	private int dbfunctinonId;
	private int dbqueryId;
	
	private Map<String, Object> otherParameters;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getRuleValidFrom() {
		return ruleValidFrom;
	}

	public void setRuleValidFrom(String ruleValidFrom) {
		this.ruleValidFrom = ruleValidFrom;
	}

	public String getRuleValidTo() {
		return ruleValidTo;
	}

	public void setRuleValidTo(String ruleValidTo) {
		this.ruleValidTo = ruleValidTo;
	}

	

	public String getRuleVersion() {
		return ruleVersion;
	}

	public void setRuleVersion(String ruleVersion) {
		this.ruleVersion = ruleVersion;
	}

	public String getRuleText() {
		return ruleText;
	}

	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}

	public String getRuleOperator() {
		return ruleOperator;
	}

	public void setRuleOperator(String ruleOperator) {
		this.ruleOperator = ruleOperator;
	}

	public String getRuleConditionText() {
		return ruleConditionText;
	}

	public void setRuleConditionText(String ruleConditionText) {
		this.ruleConditionText = ruleConditionText;
	}

	public String getRuleActionText() {
		return ruleActionText;
	}

	public void setRuleActionText(String ruleActionText) {
		this.ruleActionText = ruleActionText;
	}

	public int getFormulaGroupId() {
		return formulaGroupId;
	}

	public void setFormulaGroupId(int formulaGroupId) {
		this.formulaGroupId = formulaGroupId;
	}

	

	public String getRuleFormulaGroup() {
		return ruleFormulaGroup;
	}

	public void setRuleFormulaGroup(String ruleFormulaGroup) {
		this.ruleFormulaGroup = ruleFormulaGroup;
	}

	public String getRuleFormulaGroupVersion() {
		return ruleFormulaGroupVersion;
	}

	public void setRuleFormulaGroupVersion(String ruleFormulaGroupVersion) {
		this.ruleFormulaGroupVersion = ruleFormulaGroupVersion;
	}

	public String getRuleEmailList() {
		return ruleEmailList;
	}

	public void setRuleEmailList(String ruleEmailList) {
		this.ruleEmailList = ruleEmailList;
	}

	public int getExternalServiceUrlId() {
		return externalServiceUrlId;
	}

	public void setExternalServiceUrlId(int externalServiceUrlId) {
		this.externalServiceUrlId = externalServiceUrlId;
	}

	public int getDbfunctinonId() {
		return dbfunctinonId;
	}

	public void setDbfunctinonId(int dbfunctinonId) {
		this.dbfunctinonId = dbfunctinonId;
	}

	public int getDbqueryId() {
		return dbqueryId;
	}

	public void setDbqueryId(int dbqueryId) {
		this.dbqueryId = dbqueryId;
	}

	public Map<String, Object> getOtherParameters() {
		return otherParameters;
	}

	public void setOtherParameters(Map<String, Object> otherParameters) {
		this.otherParameters = otherParameters;
	}

	@Override
	public String toString() {
		return "RulesEngineObject [ruleId=" + ruleId + ", ruleName=" + ruleName
				+ ", ruleDesc=" + ruleDesc + ", ruleValidFrom=" + ruleValidFrom
				+ ", ruleValidTo=" + ruleValidTo + ", ruleVersion=" + ruleVersion + ", ruleText=" + ruleText
				+ ", ruleOperator=" + ruleOperator + ", ruleConditionText="
				+ ruleConditionText + ", ruleActionText=" + ruleActionText
				+ ", formulaGroupId=" + formulaGroupId + "ruleFormulaGroup=" + ruleFormulaGroup
				+ ", ruleFormulaGroupVersion=" + ruleFormulaGroupVersion
				+ ", ruleEmailList=" + ruleEmailList
				+ ", externalServiceUrlId=" + externalServiceUrlId
				+ ", dbfunctinonId=" + dbfunctinonId + ", dbqueryId="
				+ dbqueryId + ", otherParameters=" + otherParameters + "]";
	}
	
	
		
}
