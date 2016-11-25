package com.rules.model;

import java.io.Serializable;

public class RulesGroupObject implements Serializable {

	/* private String formulaGroup; */ // auto generated from code - should be
										// unique
										// for a business FG + db seq no
	private String ruleFormulaGroupId;
	private String ruleGroupName; // caller -friendly name
	private String ruleGroupDesc;
	private String startDate;
	private String version;
	public String getRuleFormulaGroupId() {
		return ruleFormulaGroupId;
	}
	public void setRuleFormulaGroupId(String ruleFormulaGroupId) {
		this.ruleFormulaGroupId = ruleFormulaGroupId;
	}
	public String getRuleGroupName() {
		return ruleGroupName;
	}
	public void setRuleGroupName(String ruleGroupName) {
		this.ruleGroupName = ruleGroupName;
	}
	
	public String getRuleGroupDesc() {
		return ruleGroupDesc;
	}
	public void setRuleGroupDesc(String ruleGroupDesc) {
		this.ruleGroupDesc = ruleGroupDesc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "RulesGroupObject [ruleFormulaGroupId=" + ruleFormulaGroupId + ", ruleGroupName=" + ruleGroupName
				+ ", ruleGroupDesc=" + ruleGroupDesc + ", startDate=" + startDate + ", version=" + version + "]";
	}
	
		
}
