package com.rules.service.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rules.model.RuleParameterModel;
import com.rules.model.RulesRequestModel;

public interface IRulesEngineService {


	@RequestMapping(value="saveRule",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public RulesRequestModel saveRule(RulesRequestModel rulesRequestModel) throws Exception;
	
	@RequestMapping(value="retrieveAllRules",method = RequestMethod.GET, produces = "application/json")
	public List<RulesRequestModel> retrieveRules() throws Exception;
	
	@RequestMapping(value="retrieveRuleDetails/{ruleId}",method = RequestMethod.GET , produces = "application/json")
	public RulesRequestModel retrieveRuleDetailsByRuleId(String ruleId) throws Exception;
	
	@RequestMapping(value="retrieveRuleDetails/{ruleId}",method = RequestMethod.GET , produces = "application/json")
	public RulesRequestModel retrieveRuleDetailsByRuleGroupId(String ruleId) throws Exception;
	
	
	
	@RequestMapping(value = "executeRule/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public HashMap<String,Object> executeRule(
			@RequestBody RuleParameterModel objRuleParameterModel)throws Exception;
	
	@RequestMapping(value = "/parameterList", method = RequestMethod.POST)
	HashMap <String, Object> /*String*/ ruleParameterList(@RequestBody RuleParameterModel objRuleParameterModel);
	
	@RequestMapping(value = "/getDiscountMargin", method = RequestMethod.GET)
	public double getDiscountMargin(
			@RequestParam("CustomerName") String CustomerName) throws Exception;
	
	@RequestMapping(value = "/getCustomerDiscount", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getCustomerDiscount(
			@RequestBody String json) throws Exception ;
	
}
