package com.rules.model;

import java.util.List;
import java.util.Map;

import com.formula.model.FormulaObject;
/*
 * This object will be used for Executing the Rule
 */
public class RulesExecutionRequest {

	private String ruleGroupId;
	private String ruleId;
    private Map<String, Map<String,String>> rulesPrameters; //Key: Rule ID , Map: Parameter Key, Parameter value
	private Map<String, List<FormulaObject>> formulaListObject;//Key: Rule ID , Map: Formula object

	public String getRuleGroupId() {
		return ruleGroupId;
	}
	public void setRuleGroupId(String ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public Map<String, Map<String, String>> getRulesPrameters() {
		return rulesPrameters;
	}
	public void setRulesPrameters(Map<String, Map<String, String>> rulesPrameters) {
		this.rulesPrameters = rulesPrameters;
	}
	public Map<String, List<FormulaObject>> getFormulaListObject() {
		return formulaListObject;
	}
	public void setFormulaListObject(Map<String, List<FormulaObject>> formulaListObject) {
		this.formulaListObject = formulaListObject;
	}
	@Override
	public String toString() {
		return "RulesExecutionRequest [ruleGroupId=" + ruleGroupId + ", ruleId=" + ruleId + ", rulesPrameters="
				+ rulesPrameters + ", formulaListObject=" + formulaListObject + "]";
	}
	
		
}
