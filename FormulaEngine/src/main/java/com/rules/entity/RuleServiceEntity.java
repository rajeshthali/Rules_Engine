package com.rules.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "RULES_SERVICE_REPO")
// , schema="cube")
public class RuleServiceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rule_service_id", unique = true, nullable = false)
	private int ruleserviceId;
	@Column(name = "rule_service_name")
	private String serviceName;
	@Column(name = "rule_service_url")
	private String serviceURL;
	@Column(name = "rule_service_type")
	private String serviceType;
	@Column(name = "rule_param_name")
	private String serviceParam;

	
	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceParam() {
		return serviceParam;
	}

	public void setServiceParam(String serviceParam) {
		this.serviceParam = serviceParam;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public int getRuleserviceId() {
		return ruleserviceId;
	}

	public void setRuleserviceId(int ruleserviceId) {
		this.ruleserviceId = ruleserviceId;
	}

}
