package com.rules.model;

import java.util.List;

import com.formula.model.FormulaExecuteObject;

public class RulesResultsObject {

	private String status;
	private List<FormulaExecuteObject> formResults;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<FormulaExecuteObject> getFormResults() {
		return formResults;
	}
	public void setFormResults(List<FormulaExecuteObject> formResults) {
		this.formResults = formResults;
	}
	
	
}
