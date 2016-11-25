package com.rules.model;

import java.util.ArrayList;
import java.util.List;

public class RulesRequestModel {
	
	private RulesGroupObject ruleGroupObject;

	private List<RulesEngineObject> ruleEngineList = new ArrayList<RulesEngineObject>();

	public List<RulesEngineObject> getRuleEngineList() {
		return ruleEngineList;
	}

	public void setRuleEngine(List<RulesEngineObject> ruleEngineList) {
		this.ruleEngineList = ruleEngineList;
	}

	public RulesGroupObject getRuleGroupObject() {
		return ruleGroupObject;
	}

	public void setRuleGroupObject(RulesGroupObject ruleGroupObject) {
		this.ruleGroupObject = ruleGroupObject;
	}

	@Override
	public String toString() {
		return "RulesRequestModel [ruleGroupObject=" + ruleGroupObject + ", ruleEngine=" + ruleEngineList + "]";
	}

	

}
