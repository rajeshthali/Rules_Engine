package com.formula.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "FE_Formula")// , schema="cube")
public class Formula {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fomula_id", unique = true, nullable = false)
	private int formulaId;
	@Column(name = "formula_name")
	private String formulaName;
	@Column(name = "formula_text" , length = 1000)
	private String formulaString;

	@Column(name = "sequence_num")
	private int sequenceNumber;

	@ManyToOne(cascade = { CascadeType.ALL })

	private Groups group;

	public Formula(int formulaId, String formulaName, String formulaText) {
		super();
		this.formulaId = formulaId;
		this.formulaName = formulaName;
		this.formulaString = formulaText;

	}

	public Formula() {
		super();
	}

	public Groups getGroup() {
		return group;
	}

	public void setGroups(Groups group) {
		this.group = group;
	}

	public int getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(int formulaId) {
		this.formulaId = formulaId;
	}

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public String getFormulaString() {
		return formulaString;
	}

	public void setFormulaString(String formulaText) {
		this.formulaString = formulaText;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}