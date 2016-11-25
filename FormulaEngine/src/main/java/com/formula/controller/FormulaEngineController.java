package com.formula.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formula.model.FormulaRequestModel;
import com.formula.service.FormulaEngineService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@RestController
@RequestMapping("FormulaEngine")
@ComponentScan(basePackages = { "com" })
@EnableSwagger2
//@EnableResourceServer
public class FormulaEngineController {
	
	private static final Logger logger = LoggerFactory.getLogger(FormulaEngineController.class);
	
	@Autowired
	private FormulaEngineService formulaEngineService;

	@PersistenceContext
	EntityManager em;

	@RequestMapping(value = "/addFormula", method = RequestMethod.POST)
	public List<HashMap<String, Object>> saveFormula(@RequestBody FormulaRequestModel requestObject) {
		
		logger.info("inside class:FormulaEngineController.java,  method:saveFormula, message :: Starts");
		
		List<HashMap<String, Object>> outputString = new ArrayList<HashMap<String, Object>>();
		try {
			outputString = formulaEngineService.updateFormulaByGroup(requestObject);
			logger.info("inside class:FormulaEngineController.java,  method: saveFormula,message ::Exit");
			return outputString;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: saveFormula, Exception message ", e);
			return outputString;
		}
		
	}

	@RequestMapping(value = "/searchFormulaByGroup", method = RequestMethod.GET, produces = "application/json")
	public ArrayList<HashMap<String, String>> searchByGroup(@RequestParam("formulaGroup") String formulaGroup, @RequestParam("version") int version) {
		
		logger.info("inside class:FormulaEngineController.java,  method: searchByGroup, message :: Starts");
		
		try {
			ArrayList<HashMap<String, String>> list = formulaEngineService.searchByGroup(formulaGroup, version);
			logger.info("inside class:FormulaEngineController.java,  method: searchByGroup, message :: Exit");
			return list;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: saveFormula, Exception message : " , e);
			return null;
		}
		
	}

	@RequestMapping(value = "/validateFormula", method = RequestMethod.POST)
	public String validateFormula(@RequestBody String formula) {
		
		logger.info("inside class:FormulaEngineController.java,  method: validateFormula, message :: Starts");
		
		String validateOutput = "";
		
		try {
			logger.info("inside class:FormulaEngineController.java,  method: validateFormula, message :: Exit");
			validateOutput = formulaEngineService.validateFormula(formula);
			return validateOutput;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: validateFormula, Exception message : " , e);
			return validateOutput;
		}

	}

	@RequestMapping(value = "/searchFormulaByName", method = RequestMethod.GET, produces = "application/json")
	public HashMap<String, String> searchByName(@RequestParam("formulaGroup") String formulaGroup,@RequestParam("version") int version, @RequestParam("formulaName") String formulaName) {
		
		logger.info("inside class:FormulaEngineController.java,  method: searchByName, message :: Starts");
		
		try {
			HashMap<String, String> map = formulaEngineService.searchByFormulaNameNew(formulaGroup, formulaName,
					version);
			logger.info("inside class:FormulaEngineController.java,  method: searchByName, message :: Exit");
			return map;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: searchByName, Exception message : " , e);
			return null;
		}
		
	}

	@RequestMapping(value = "/deleteByFormulaName", method = RequestMethod.POST, produces = "application/json")
	public HashMap<String, HashMap<String, Object>> deleteByName(@RequestBody FormulaRequestModel objFormulaRequestModel) {
		
		logger.info("inside class:FormulaEngineController.java,  method: deleteByName, message :: Starts");
		
		try {
			HashMap<String, HashMap<String, Object>> map = formulaEngineService
					.deleteByFormulaName(objFormulaRequestModel);
			logger.info("inside class:FormulaEngineController.java,  method: deleteByName, message :: Exit");
			return map;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: deleteByName, Exception message : " , e);
			return null;
		}
		
	}

	@RequestMapping(value = "/deleteByFormulaGroup", method = RequestMethod.POST)
	public HashMap<String, Object> deleteByFormulaGroup(@RequestBody FormulaRequestModel objFormulaRequestModel) {
		
		try {
			logger.info("inside class:FormulaEngineController.java,  method: deleteByFormulaGroup, message :: Starts");
			return formulaEngineService.deleteByFormulaGroup(objFormulaRequestModel);
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: deleteByFormulaGroup, Exception message : " , e);
			return null;
		}
		
	}

	@RequestMapping(value = "/updateFormulaGroup", method = RequestMethod.POST, produces = "application/json")
	public List<HashMap<String, Object>> updateFormulaGroup(@RequestBody FormulaRequestModel objFormulaRequestModel) {
		
		logger.info("inside class:FormulaEngineController.java,  method: updateFormulaGroup, message :: Starts");
		
		try {
			return formulaEngineService.updateFormulaByGroup(objFormulaRequestModel);
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: updateFormulaGroup, Exception message : " , e);
			return null;
		}
		
	}

	@RequestMapping(value = "/executeFormula", method = RequestMethod.POST)
	public HashMap<String, List<HashMap<String, Object>>> executeFormula(@RequestBody String inputJSON) {
		
		logger.info("inside class:FormulaEngineController.java,  method: executeFormula, message :: Starts");
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		
		try {
			HashMap<String, List<HashMap<String, Object>>> outputMap = formulaEngineService.executeFormulaMthd(inputJSON, factory, entitymanager);
			entitymanager.close();
			factory.close();
			logger.info("inside class:FormulaEngineController.java,  method: executeFormula, message :: Exit");
			return outputMap;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: executeFormula, Exception message : " , e);
			return null;
		}
		
	}

	@RequestMapping(value = "/version", method = RequestMethod.POST)
	public List<HashMap<String, Object>> addVersion(@RequestBody FormulaRequestModel formulaGroupObject) {
		
		logger.info("inside class:FormulaEngineController.java,  method: addVersion, message :: Starts");
		
		try {
			return formulaEngineService.addFormulaVersion(formulaGroupObject);
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: addVersion, Exception message : " , e);
			return null;
		}
		
	}

	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testMethod(@RequestParam("name") String name) {
		
		logger.info("inside class:FormulaEngineController.java,  method: testMethod, message :: Starts");
		
		try {
			return "Hello " + name;
		} catch (Exception e) {
			logger.error("inside class:FormulaEngineController.java,  method: testMethod, Exception message : " , e);
			return null;
		}
		
	}
	
}
