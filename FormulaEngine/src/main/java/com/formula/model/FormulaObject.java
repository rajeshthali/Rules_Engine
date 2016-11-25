package com.formula.model;

import java.util.LinkedHashMap;

public class FormulaObject {

	private String formulaStr;// actual formula string from caller
	private String formulaDescription;
	private Double formulaValue; // Formula Values Hash map
	// private int formulaOrder;
	private int formulaSeq;
	LinkedHashMap<String, String> formulaValues;

	public String getFormulaStr() {
		return formulaStr;
	}

	public void setFormulaStr(String formulaStr) {
		this.formulaStr = formulaStr;
	}

	public String getFormulaDescription() {
		return formulaDescription;
	}

	public void setFormulaDescription(String formulaDescription) {
		this.formulaDescription = formulaDescription;
	}

	public LinkedHashMap<String, String> getFormulaValues() {
		return formulaValues;
	}

	public void setFormulaValues(LinkedHashMap<String, String> formulaValues) {
		this.formulaValues = formulaValues;
	}

	public Double getFormulaValue() {
		return formulaValue;
	}

	public void setFormulaValue(Double formulaValue) {
		this.formulaValue = formulaValue;
	}

	public int getFormulaSeq() {
		return formulaSeq;
	}

	public void setFormulaSeq(int formulaSeq) {
		this.formulaSeq = formulaSeq;
	}

}
