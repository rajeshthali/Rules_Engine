package com.formula.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FRE_Matrix")
public class Matrix {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "matrixid")
	private float matrixId;
	
	@Column(name = "interpol_col1_name")
	private String interpolCol1Name;
	
	@Column(name = "interpol_col2_name")
	private String interpolCol2Name;
	
	@Column(name = "interpol_col3_name")
	private String interpolCol3Name;
	
	@Column(name = "interpol_col4_name")
	private String interpolCol4Name;
	
	@Column(name = "interpol_factor1")
	private float interpolFactor1 ;
	
	@Column(name = "interpol_factor2")
	private float interpolFactor2;
	
	@Column(name = "interpol_factor3")
	private float interpolFactor3;
	
	@Column(name = "interpol_factor4")
	private float interpolFactor4;

	@Column(name = "interpol_value")
	private float interpolValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getMatrixId() {
		return matrixId;
	}

	public void setMatrixId(float matrixId) {
		this.matrixId = matrixId;
	}

	public String getInterpolCol1Name() {
		return interpolCol1Name;
	}

	public void setInterpolCol1Name(String interpolCol1Name) {
		this.interpolCol1Name = interpolCol1Name;
	}

	public String getInterpolCol2Name() {
		return interpolCol2Name;
	}

	public void setInterpolCol2Name(String interpolCol2Name) {
		this.interpolCol2Name = interpolCol2Name;
	}

	public String getInterpolCol3Name() {
		return interpolCol3Name;
	}

	public void setInterpolCol3Name(String interpolCol3Name) {
		this.interpolCol3Name = interpolCol3Name;
	}

	public String getInterpolCol4Name() {
		return interpolCol4Name;
	}

	public void setInterpolCol4Name(String interpolCol4Name) {
		this.interpolCol4Name = interpolCol4Name;
	}

	public float getInterpolFactor1() {
		return interpolFactor1;
	}

	public void setInterpolFactor1(float interpolFactor1) {
		this.interpolFactor1 = interpolFactor1;
	}

	public float getInterpolFactor2() {
		return interpolFactor2;
	}

	public void setInterpolFactor2(float interpolFactor2) {
		this.interpolFactor2 = interpolFactor2;
	}

	public float getInterpolFactor3() {
		return interpolFactor3;
	}

	public void setInterpolFactor3(float interpolFactor3) {
		this.interpolFactor3 = interpolFactor3;
	}

	public float getInterpolFactor4() {
		return interpolFactor4;
	}

	public void setInterpolFactor4(float interpolFactor4) {
		this.interpolFactor4 = interpolFactor4;
	}

	public float getInterpolValue() {
		return interpolValue;
	}

	public void setInterpolValue(float interpolValue) {
		this.interpolValue = interpolValue;
	}
}
