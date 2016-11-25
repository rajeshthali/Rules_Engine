package com.formula.model;

import java.util.HashMap;

public class FormulaRequestModel {
	
	private HashMap<String,FormulaGroupObject>formulaGroupInfo;

	public HashMap<String, FormulaGroupObject> getFormulaGroupInfo() {
		return formulaGroupInfo;
	}

	public void setFormulaGroupInfo(HashMap<String, FormulaGroupObject> formulaGroupInfo) {
		this.formulaGroupInfo = formulaGroupInfo;
	}

}
