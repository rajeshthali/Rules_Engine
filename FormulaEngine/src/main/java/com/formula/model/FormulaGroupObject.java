package com.formula.model;

import java.io.Serializable;
import java.util.List;

public class FormulaGroupObject implements Serializable {

	/* private String formulaGroup; */ // auto generated from code - should be
										// unique
										// for a business FG + db seq no
	private String formulaGroupName; // caller -friendly name
	private List<FormulaObject> formula;
	private String formulaGroupDescription;
	private String startDate;

	private int version;

	public List<FormulaObject> getFormula() {
		return formula;
	}

	public void setFormula(List<FormulaObject> formula) {
		this.formula = formula;
	}

	public String getFormulaGroupDescription() {
		return formulaGroupDescription;
	}

	public void setFormulaGroupDescription(String formulaGroupDescription) {
		this.formulaGroupDescription = formulaGroupDescription;
	}

	public String getFormulaGroupName() {
		return formulaGroupName;
	}

	public void setFormulaGroupName(String formulaGroupName) {
		this.formulaGroupName = formulaGroupName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
