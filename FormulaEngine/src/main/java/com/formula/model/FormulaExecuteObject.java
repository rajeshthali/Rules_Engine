package com.formula.model;

public class FormulaExecuteObject {

	/// private long sequenceNumber; //auto generated --incresing in order
	/// SEQ+seq no

	private String formulaDescription;
	private String formulaValue; // Formula Values Hash map
	private int formulaOrder;

	/*
	 * public long getSequenceNumber() { return sequenceNumber; } public void
	 * setSequenceNumber(long sequenceNumber) { this.sequenceNumber =
	 * sequenceNumber; }
	 */

	public String getFormulaDescription() {
		return formulaDescription;
	}

	public void setFormulaDescription(String formulaDescription) {
		this.formulaDescription = formulaDescription;
	}

	public String getFormulaValue() {
		return formulaValue;
	}

	public void setFormulaValue(String formulaValue) {
		this.formulaValue = formulaValue;
	}

	public int getFormulaOrder() {
		return formulaOrder;
	}

	public void setFormulaOrder(int formulaOrder) {
		this.formulaOrder = formulaOrder;
	}

}
