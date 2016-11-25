package com.rules.model;


public class RulesDbConDetailsObj {

	private int connId;
	private String connUrl;
	
	private String userName;

	private String password;

	private String connName;
	
	private String driver;
	

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public int getConnId() {
		return connId;
	}

	public void setConnId(int connId) {
		this.connId = connId;
	}

	public String getConnUrl() {
		return connUrl;
	}

	public void setConnUrl(String connUrl) {
		this.connUrl = connUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}


	@Override
	public String toString() {
		return "RulesDbConDetailsEntity [connId=" + connId + ", connUrl="
				+ connUrl + ", userName=" + userName + ", password=" + password
				+ ", connName=" + connName + "]";
	}
	
	

}