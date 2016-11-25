package com.rules.entity;

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
@Table(name = "RULES_GROUP_ENGINE") // , schema="cube")
@SequenceGenerator(name = "rulesGroupSeq", initialValue = 1, allocationSize = 1)
public class RulesGroupEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rulesGroupSeq")
	@Column(name = "rules_group_id")
	private int rulesGroupId;
	@Column(name = "rules_group_name")
	private String rulesGroupName;
	@Column(name = "rules_group_desc")
	private String rulesGroupDesc;
	@Column(name = "start_date")
	private String startDate;

	@Column(name = "end_Date")
	private String endDate;

	@Column(name = "version")
	private String version;

	

	@OneToMany(mappedBy="rulesGroupEntity",cascade=CascadeType.ALL) 
	private Set<RulesGroupEntity> rulesGroupEntity = new HashSet<RulesGroupEntity>();

	
	public String getRulesGroupDesc() {
		return rulesGroupDesc;
	}

	public void setRulesGroupDesc(String rulesGroupDesc) {
		this.rulesGroupDesc = rulesGroupDesc;
	}
	
	public int getRulesGroupId() {
		return rulesGroupId;
	}

	public void setRulesGroupId(int rulesGroupId) {
		this.rulesGroupId = rulesGroupId;
	}

	public String getRulesGroupName() {
		return rulesGroupName;
	}

	public void setRulesGroupName(String rulesGroupName) {
		this.rulesGroupName = rulesGroupName;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<RulesGroupEntity> getRulesGroupEntity() {
		return rulesGroupEntity;
	}

	public void setRulesGroupEntity(Set<RulesGroupEntity> rulesGroupEntity) {
		this.rulesGroupEntity = rulesGroupEntity;
	}

	
	
	

}