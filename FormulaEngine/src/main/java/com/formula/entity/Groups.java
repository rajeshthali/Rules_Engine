package com.formula.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
//Formual_Group_1 deployed to Predix
@Table(name = "FE_Groups")//, schema="cube")
@SequenceGenerator(name="groupSeq", initialValue=1,allocationSize=1)
public class Groups {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="groupSeq")
	@Column(name = "formula_group_id")
	private int formulaGroupId;
	@Column(name = "formula_group_name")
	private String formulaGroupName;
	   @Column(name="start_date")
	   private String startDate;
	   
	   @Column(name="end_Date")
	   private String endDate;
	   
	   @Column(name="version")
	   private int version;
	   
	   
	public int getFormulaGroupId() {
		return formulaGroupId;
	}
	public void setFormulaGroupId(int formulaGroupId) {
		this.formulaGroupId = formulaGroupId;
	}
	public String getFormulaGroupName() {
		return formulaGroupName;
	}
	public void setFormulaGroupName(String formulaGroupName) {
		this.formulaGroupName = formulaGroupName;
	}

	
    @OneToMany(mappedBy="groups", cascade = CascadeType.ALL)
    
    private Set<Groups> groups = new HashSet<Groups>();
	public Set<Groups> getGroups() {
		return groups;
	}
	public void setGroups(Set<Groups> groups) {
		this.groups = groups;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}    
	
}	