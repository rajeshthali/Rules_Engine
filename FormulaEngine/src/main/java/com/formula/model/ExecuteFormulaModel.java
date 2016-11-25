package com.formula.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class will hold the Formula Group results
 * 
  */
public class ExecuteFormulaModel implements Serializable {

	private static final long serialVersionUID = -1084207101076832592L;
	String formulaGroupName;
	List<FormulaExecuteObject> formulaResultList;

	public String getFormulaGroupName() {
		return formulaGroupName;
	}

	public void setFormulaGroupName(String formulaGroupName) {
		this.formulaGroupName = formulaGroupName;
	}

	public List<FormulaExecuteObject> getFormulaResultList() {
		return formulaResultList;
	}

	public void setFormulaResultList(List<FormulaExecuteObject> formulaResultList) {
		this.formulaResultList = formulaResultList;
	}

}
