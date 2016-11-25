package com.rules.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RULES_ENGINE")// , schema="cube")
public class RulesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rule_id", unique = true, nullable = false)
	private int ruleId;
	
	@Column(name = "rule_name")
	private String ruleName;
	
	@Column(name = "rule_desc" , length = 1000)
	private String ruleDesc;
	
	@Column(name = "rule_text" , length = 1000)
	private String ruleText;  // USER UNDERSTANDABLE STRING IF A > B AND C>D THEN FORMULA
	
	@Column(name="rule_Operator")
	private String ruleOperator; // CALL/EXEC
		
	@Column(name = "rule_formula_text" , length = 1000)
	private String ruleConditionText;   ///// if($formula1>$formula2) 
	

	@Column(name="rule_Action_Text")
	private String ruleActionText; ///formula3,formula4;
	
	@Column(name="start_date")
	private String ruleValidFrom;
	
	@Column(name="end_date")
	private String ruleValidTo;
	
	/*calc*/
		
	@Column(name = "formula_group_id")
	private int formulaGroupId; 
	
	@Column(name = "email_list")
	private String ruleEmailList;
	
	@Column(name = "version")
	private String ruleVersion;
	
	@Column(name = "service_Id")
	private int externalServiceId;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "ruleserviceId", cascade = CascadeType.ALL)
	public int getExternalServiceId() {
		return externalServiceId;
	}

	@Column(name = "dbfunctinon_Id")
	private int dbfunctinonId;

	@Column(name = "dbquery_Id")
	private int dbqueryId;
	
	//
	@ManyToOne(cascade = { CascadeType.ALL } )
	private RulesGroupEntity rules_Group_Entity;


	public void setExternalServiceId(int externalServiceId) {
		this.externalServiceId = externalServiceId;
	}
	
	
	
	
	/*@ManyToOne(cascade = { CascadeType.ALL } )
	private RulesGroupEntity rulesGroupEntity*/;
	
	
	
	
	
	/*@OneToOne(fetch = FetchType.LAZY, mappedBy = "serviceId", cascade = CascadeType.ALL)
	
	public RulesGroupEntity getRulesGroupEntity() {
		return rulesGroupEntity;
	}

	public void setRulesGroupEntity(RulesGroupEntity rulesGroupEntity) {
		this.rulesGroupEntity = rulesGroupEntity;
	}*/




	public int getRuleId() {
		return ruleId;
	}




	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}




	public String getRuleName() {
		return ruleName;
	}




	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}




	public String getRuleDesc() {
		return ruleDesc;
	}




	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}




	public String getRuleText() {
		return ruleText;
	}




	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}




	public String getRuleOperator() {
		return ruleOperator;
	}




	public void setRuleOperator(String ruleOperator) {
		this.ruleOperator = ruleOperator;
	}




	public String getRuleConditionText() {
		return ruleConditionText;
	}




	public void setRuleConditionText(String ruleConditionText) {
		this.ruleConditionText = ruleConditionText;
	}




	public String getRuleActionText() {
		return ruleActionText;
	}




	public void setRuleActionText(String ruleActionText) {
		this.ruleActionText = ruleActionText;
	}




	public String getRuleValidFrom() {
		return ruleValidFrom;
	}




	public void setRuleValidFrom(String ruleValidFrom) {
		this.ruleValidFrom = ruleValidFrom;
	}




	public String getRuleValidTo() {
		return ruleValidTo;
	}




	public void setRuleValidTo(String ruleValidTo) {
		this.ruleValidTo = ruleValidTo;
	}




	public int getFormulaGroupId() {
		return formulaGroupId;
	}




	public void setFormulaGroupId(int formulaGroupId) {
		this.formulaGroupId = formulaGroupId;
	}




	public String getRuleEmailList() {
		return ruleEmailList;
	}




	public void setRuleEmailList(String ruleEmailList) {
		this.ruleEmailList = ruleEmailList;
	}




	public String getRuleVersion() {
		return ruleVersion;
	}




	public void setRuleVersion(String ruleVersion) {
		this.ruleVersion = ruleVersion;
	}



	



	public RulesEntity(){
		super();
	}
	


	public RulesEntity(int ruleId, String ruleName, String ruleDesc, String ruleText, String ruleOperator,
			String ruleConditionText, String ruleActionText, String ruleValidFrom, String ruleValidTo,
			int formulaGroupId, String ruleEmailList, String ruleVersion, int externalServiceId
			) {
		super();
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.ruleDesc = ruleDesc;
		this.ruleText = ruleText;
		this.ruleOperator = ruleOperator;
		this.ruleConditionText = ruleConditionText;
		this.ruleActionText = ruleActionText;
		this.ruleValidFrom = ruleValidFrom;
		this.ruleValidTo = ruleValidTo;
		this.formulaGroupId = formulaGroupId;
		this.ruleEmailList = ruleEmailList;
		this.ruleVersion = ruleVersion;
		this.externalServiceId = externalServiceId;
		
	}

	public int getDbfunctinonId() {
		return dbfunctinonId;
	}

	public void setDbfunctinonId(int dbfunctinonId) {
		this.dbfunctinonId = dbfunctinonId;
	}

	public int getDbqueryId() {
		return dbqueryId;
	}

	public void setDbqueryId(int dbqueryId) {
		this.dbqueryId = dbqueryId;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "serviceId", cascade = CascadeType.ALL)
	public RulesGroupEntity getRules_Group_Entity() {
		return rules_Group_Entity;
	}

	public void setRules_Group_Entity(RulesGroupEntity rules_Group_Entity) {
		this.rules_Group_Entity = rules_Group_Entity;
	}


	

	
		
}