package com.rules.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.rules.manager.RulesEngineManager;
import com.rules.model.Product;
import com.rules.model.RuleParameterModel;
import com.rules.model.RulesDbConDetailsObj;
import com.rules.model.RulesDbDataObj;
import com.rules.model.RulesEngineObject;
import com.rules.model.RulesGroupObject;
import com.rules.model.RulesRequestModel;
import com.rules.service.api.IRulesEngineService;

@RequestMapping("/rulesEngine/")
@RestController
@ComponentScan(basePackages = { "com" })
@EnableSwagger2
public class RulesEngineServicesImpl implements IRulesEngineService {

	private static final Logger logger = LoggerFactory
			.getLogger(RulesEngineServicesImpl.class);

	@Autowired
	RulesEngineManager rulesEngineManager;

		
	
	@RequestMapping(value="getProduct/post",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public Product createProductInJSON(@RequestBody Product product) {

		String result = "Product created : " + product;
		return product;
		
	}
	
	@RequestMapping(value="saveRule",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public RulesRequestModel saveRule(@RequestBody RulesRequestModel rulesRequestModel) throws Exception{
		
		logger.info("START saveRule rulesRequestModel:"+rulesRequestModel);
		rulesRequestModel =rulesEngineManager.saveRule(rulesRequestModel);
		return rulesRequestModel;
	}
	
	@RequestMapping(value="retrieveAllRules",method = RequestMethod.GET, produces = "application/json")
	public List<RulesRequestModel> retrieveRules() throws Exception{
		logger.info("START retrieveRules");		
		return rulesEngineManager.retrieveAllRules() ;
	}
	
	@RequestMapping(value="sample",method = RequestMethod.GET, produces = "application/json")
	public RulesRequestModel getSample() throws Exception{
		logger.info("START getSample");
		RulesRequestModel requestModel = new RulesRequestModel();
		RulesEngineObject rulesEngineObject = new RulesEngineObject();
		requestModel.setRuleEngine(new ArrayList<RulesEngineObject>());
		requestModel.getRuleEngineList().add(rulesEngineObject);
		requestModel.setRuleGroupObject(new RulesGroupObject());
		return requestModel;
	}
	
	@RequestMapping(value="ruleDetailsByRuleId/{ruleId}",method = RequestMethod.GET , produces = "application/json")
	public RulesRequestModel retrieveRuleDetailsByRuleId(@PathVariable String ruleId) throws Exception{
		logger.info("START retrieveRuleDetailsByRuleId rule ID:"+ruleId);
		
		return rulesEngineManager.ruleDetailsByRuleId(ruleId);
	}
	
	@RequestMapping(value="ruleDetailsByRuleGroupId/{ruleGroupId}",method = RequestMethod.GET , produces = "application/json")
	public RulesRequestModel retrieveRuleDetailsByRuleGroupId(@PathVariable String ruleGroupId) throws Exception{
		logger.info("START retrieveRuleDetailsByRuleGroupId ruleGroupId:"+ruleGroupId);
		
		return rulesEngineManager.ruleDetailsByRuleGroupId(ruleGroupId);
	}
		
		
	
	@RequestMapping(value="deleteRule",method = RequestMethod.GET,produces = "application/json")
	public String deleteRule(String ruleId) throws Exception{
		
		logger.info("START deleteRule :"+ruleId);
		return rulesEngineManager.deleteRule(ruleId);
		
	}
	
	
	@RequestMapping(value="addDbConnections",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public RulesDbConDetailsObj addDbConnections(@RequestBody RulesDbConDetailsObj rulesDbConDetailsObj) throws Exception{
		
		
		return rulesEngineManager.addDbConnections(rulesDbConDetailsObj);
		
	}
	
	@RequestMapping(value="addDbFunctionQuery",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public RulesDbDataObj addDBQueryFunction(@RequestBody RulesDbDataObj rulesDbDataObj) throws Exception{
		
		
		return rulesEngineManager.addDBQueryFunction(rulesDbDataObj);
		
	}
	
	@RequestMapping(value="callDbQueryFunction",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public RulesDbDataObj callDBQueryFunction(@RequestBody RulesDbDataObj rulesDbDataObj) throws Exception{
		
		
		return rulesEngineManager.callDBQueryFunction(rulesDbDataObj);
		
	}
	


	@RequestMapping(value = "executeRule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public HashMap<String,Object> executeRule(
			@RequestBody RuleParameterModel objRuleParameterModel)
			throws Exception {
		HashMap<String,Object> outputMap = new HashMap<String,Object>(); 
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		try {
			outputMap = rulesEngineManager.executeRule(
					objRuleParameterModel, factory, entitymanager);
			//return objRuleParameterModel;
			
			
			return outputMap;
			
			

		} catch (Exception e) {
			logger.error(
					"inside class:FormulaEngineController.java,  method: testMethod, Exception message : ",
					e);
			return null;
		}

		/*
		 * LinkedHashMap<String, String> ruleValues =
		 * objRuleParameterModel.getRuleValues();
		 * 
		 * Set valueSet = ruleValues.keySet(); Iterator valueIrr =
		 * valueSet.iterator();
		 * 
		 * while(valueIrr.hasNext()) { String key = valueIrr.next().toString();
		 * String value = ruleValues.get(key).toString();
		 * 
		 * System.out.println("key: " + key + " value: " + value ); }
		 */

	}

	/*@Override
	public List<RulesRequestModel> upDateRule(String ruleGroupId,
			RulesRequestModel ruleRequestModel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/

	@RequestMapping(value = "/parameterList", method = RequestMethod.POST)
	public/* RuleParameterModel */HashMap<String, Object> /* String */ruleParameterList(
			@RequestBody RuleParameterModel objRuleParameterModel) {
		logger.info("inside class:FormulaEngineController.java,  method: testMethod, message :: Starts");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();

		try {
			HashMap<String, Object> outputMap = rulesEngineManager
					.retrievePrameterList(objRuleParameterModel, entitymanager);

			return outputMap;

		} catch (Exception e) {
			logger.error(
					"inside class:FormulaEngineController.java,  method: testMethod, Exception message : ",
					e);
			return null;
		}
	}



	@RequestMapping(value="/getProduct",method = RequestMethod.GET, produces = "application/json")
	public Product getProductInJSON() {

		Product product = new Product();
		product.setName("iPad 3");
		product.setQty(999);

		return product;

	}
	
	@RequestMapping(value = "/getDiscountedBillingRate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getCustomerDiscount(
			@RequestBody String json) throws Exception {
		double number = 0.0;
		
		JSONObject jObject = new JSONObject(json);
		String customerName =  jObject.getString("CustomerName");		
		if(customerName.equalsIgnoreCase("Air India")) number=100.0;
		else if(customerName.equalsIgnoreCase("British Airways")) number=300.0;
		else if(customerName.equalsIgnoreCase("Air China")) number=500.0;
		else if(customerName.equalsIgnoreCase("Air China")) number=1000.0;
		
		return Double.toString(number);
		
		
	}
	
	
	

	@Override
	public double getDiscountMargin(String CustomerName) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
		
	/*@RequestMapping(value = "/getDiscountMargin", method = RequestMethod.GET consumes = "application/json")
	public double getDiscountMargin(
			@RequestParam("CustomerName") String CustomerName) throws Exception {
		return 10.0;
	}*/
	
	
	/*
	 * @RequestMapping(value = "/addFormula", method = RequestMethod.POST)
	 * public List<HashMap<String, Object>> saveFormula(@RequestBody
	 * FormulaRequestModel requestObject) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method:saveFormula, message :: Starts"
	 * );
	 * 
	 * List<HashMap<String, Object>> outputString = new
	 * ArrayList<HashMap<String, Object>>(); try { outputString =
	 * formulaEngineService.updateFormulaByGroup(requestObject); logger.info(
	 * "inside class:FormulaEngineController.java,  method: saveFormula,message ::Exit"
	 * ); return outputString; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: saveFormula, Exception message "
	 * , e); return outputString; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/searchFormulaByGroup", method =
	 * RequestMethod.GET, produces = "application/json") public
	 * ArrayList<HashMap<String, String>>
	 * searchByGroup(@RequestParam("formulaGroup") String formulaGroup,
	 * 
	 * @RequestParam("version") int version) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: searchByGroup, message :: Starts"
	 * );
	 * 
	 * try { ArrayList<HashMap<String, String>> list =
	 * formulaEngineService.searchByGroup(formulaGroup, version); logger.info(
	 * "inside class:FormulaEngineController.java,  method: searchByGroup, message :: Exit"
	 * ); return list; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: saveFormula, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/validateFormula", method = RequestMethod.POST)
	 * public String validateFormula(@RequestBody String formula) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: validateFormula, message :: Starts"
	 * );
	 * 
	 * String validateOutput = "";
	 * 
	 * try { logger.info(
	 * "inside class:FormulaEngineController.java,  method: validateFormula, message :: Exit"
	 * ); validateOutput = formulaEngineService.validateFormula(formula); return
	 * validateOutput; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: validateFormula, Exception message : "
	 * , e); return validateOutput; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/searchFormulaByName", method =
	 * RequestMethod.GET, produces = "application/json") public HashMap<String,
	 * String> searchByName(@RequestParam("formulaGroup") String
	 * formulaGroup,@RequestParam("version") int version,
	 * 
	 * @RequestParam("formulaName") String formulaName) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: searchByName, message :: Starts"
	 * );
	 * 
	 * try { HashMap<String, String> map =
	 * formulaEngineService.searchByFormulaNameNew(formulaGroup, formulaName,
	 * version); logger.info(
	 * "inside class:FormulaEngineController.java,  method: searchByName, message :: Exit"
	 * ); return map; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: searchByName, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/deleteByFormulaName", method =
	 * RequestMethod.POST, produces = "application/json") public HashMap<String,
	 * HashMap<String, Object>> deleteByName(@RequestBody FormulaRequestModel
	 * objFormulaRequestModel) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: deleteByName, message :: Starts"
	 * );
	 * 
	 * try { HashMap<String, HashMap<String, Object>> map = formulaEngineService
	 * .deleteByFormulaName(objFormulaRequestModel); logger.info(
	 * "inside class:FormulaEngineController.java,  method: deleteByName, message :: Exit"
	 * ); return map; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: deleteByName, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/deleteByFormulaGroup", method =
	 * RequestMethod.POST) public HashMap<String, Object>
	 * deleteByFormulaGroup(@RequestBody FormulaRequestModel
	 * objFormulaRequestModel) {
	 * 
	 * try { logger.info(
	 * "inside class:FormulaEngineController.java,  method: deleteByFormulaGroup, message :: Starts"
	 * ); return
	 * formulaEngineService.deleteByFormulaGroup(objFormulaRequestModel); }
	 * catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: deleteByFormulaGroup, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/updateFormulaGroup", method =
	 * RequestMethod.POST, produces = "application/json") public
	 * List<HashMap<String, Object>> updateFormulaGroup(@RequestBody
	 * FormulaRequestModel objFormulaRequestModel) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: updateFormulaGroup, message :: Starts"
	 * );
	 * 
	 * try { return
	 * formulaEngineService.updateFormulaByGroup(objFormulaRequestModel); }
	 * catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: updateFormulaGroup, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/executeFormula", method = RequestMethod.POST)
	 * public HashMap<String, List<HashMap<String, Object>>>
	 * executeFormula(@RequestBody String inputJSON) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: executeFormula, message :: Starts"
	 * );
	 * 
	 * EntityManagerFactory factory =
	 * Persistence.createEntityManagerFactory("Application"); EntityManager
	 * entitymanager = factory.createEntityManager();
	 * 
	 * try { HashMap<String, List<HashMap<String, Object>>> outputMap =
	 * formulaEngineService.executeFormulaMthd(inputJSON, factory,
	 * entitymanager); entitymanager.close(); factory.close(); logger.info(
	 * "inside class:FormulaEngineController.java,  method: executeFormula, message :: Exit"
	 * ); return outputMap; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: executeFormula, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/version", method = RequestMethod.POST) public
	 * List<HashMap<String, Object>> addVersion(@RequestBody FormulaRequestModel
	 * formulaGroupObject) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: addVersion, message :: Starts"
	 * );
	 * 
	 * try { return formulaEngineService.addFormulaVersion(formulaGroupObject);
	 * } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: addVersion, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 * 
	 * 
	 * @RequestMapping(value = "/test", method = RequestMethod.GET) public
	 * String testMethod(@RequestParam("name") String name) {
	 * 
	 * logger.info(
	 * "inside class:FormulaEngineController.java,  method: testMethod, message :: Starts"
	 * );
	 * 
	 * try { return "Hello " + name; } catch (Exception e) { logger.error(
	 * "inside class:FormulaEngineController.java,  method: testMethod, Exception message : "
	 * , e); return null; }
	 * 
	 * }
	 */

}
