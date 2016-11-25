package com.rules.model;

import java.util.HashMap;
import java.util.Map;


public class RulesDbDataObj {

	
	private int dataId;
	
	private String callType;
	
	private String callDetails;
	
	private String inputParams; //list of input  parameters will be seperated with ;
	
	private String outputParams;//list of output parameters will be seperated with ;
	
	private int rulesDbConnId;
	
	
	private Map<String,String> outputResult=  new HashMap<String,String>(); 
	private Map<String,String> inputData=  new HashMap<String,String>();

	
	public Map<String, String> getOutputResult() {
		return outputResult;
	}



	public void setOutputResult(Map<String, String> outputResult) {
		this.outputResult = outputResult;
	}



	public Map<String, String> getInputData() {
		return inputData;
	}



	public void setInputData(Map<String, String> inputData) {
		this.inputData = inputData;
	}



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


	public int getRulesDbConnId() {
		return rulesDbConnId;
	}

	public void setRulesDbConnId(int rulesDbConnId) {
		this.rulesDbConnId = rulesDbConnId;
	}
	
	
		
}