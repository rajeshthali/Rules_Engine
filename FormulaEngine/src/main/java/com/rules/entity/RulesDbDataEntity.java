package com.rules.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rules.entity.RulesDbConDetailsEntity;

@Entity
@Table(name = "RULES_DB_DATA")// , schema="cube")
public class RulesDbDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "data_id", unique = true, nullable = false)
	private int dataId;
	
	@Column(name = "call_Type")
	private String callType;
	
	@Column(name = "call_Details" , length = 1000)
	private String callDetails;
	
	@Column(name = "input_Params" , length = 1000)
	private String inputParams; //list of input  parameters will be seperated with ;
	
	@Column(name = "output_Params" , length = 1000)
	private String outputParams;//list of output parameters will be seperated with ;
	
	@ManyToOne(cascade = { CascadeType.ALL } )
	private RulesDbConDetailsEntity rules_Db;



	public String getInputParams() {
		return inputParams;
	}



	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}



	public String getOutputParams() {
		return outputParams;
	}



	public void setOutputParams(String outputParams) {
		this.outputParams = outputParams;
	}



	public int getDataId() {
		return dataId;
	}



	public void setDataId(int dataId) {
		this.dataId = dataId;
	}



	public String getCallType() {
		return callType;
	}



	public void setCallType(String callType) {
		this.callType = callType;
	}



	public String getCallDetails() {
		return callDetails;
	}



	public void setCallDetails(String callDetails) {
		this.callDetails = callDetails;
	}



	public RulesDbConDetailsEntity getRules_Db() {
		return rules_Db;
	}



	public void setRules_Db(RulesDbConDetailsEntity rules_Db) {
		this.rules_Db = rules_Db;
	}



	

	
	
		
}