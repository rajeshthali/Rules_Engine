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
@Table(name = "RULES_DBCON_DETAILS") // , schema="cube")
@SequenceGenerator(name = "rulesDBSeq", initialValue = 1, allocationSize = 1)
public class RulesDbConDetailsEntity {

	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rulesDBSeq")
	@Column(name = "conn_id")
	private int connId;
	@Column(name = "conn_url")
	private String connUrl;
	
	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "conn_name")
	private String connName;
	
	@Column(name = "driver")
	private String driver;

	@OneToMany(mappedBy="rulesDbEntity",cascade=CascadeType.ALL) 
	private Set<RulesDbConDetailsEntity> rulesDbEntity = new HashSet<RulesDbConDetailsEntity>();

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

	public Set<RulesDbConDetailsEntity> getRulesDbEntity() {
		return rulesDbEntity;
	}

	public void setRulesDbEntity(Set<RulesDbConDetailsEntity> rulesDbEntity) {
		this.rulesDbEntity = rulesDbEntity;
	}

	
}