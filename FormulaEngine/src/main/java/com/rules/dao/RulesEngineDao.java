package com.rules.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import com.formula.entity.Formula;
import com.formula.entity.Groups;
import com.formula.model.ExecuteFormulaModel;
import com.formula.model.FormulaExecuteObject;
import com.formula.model.FormulaGroupObject;
import com.formula.model.FormulaObject;
import com.formula.model.FormulaRequestModel;
import com.formula.util.Expr;
import com.formula.util.Parser;
import com.formula.util.Scanner;
import com.formula.util.SyntaxException;
import com.formula.util.Token;
import com.formula.util.Variable;
import com.google.gson.Gson;
import com.rules.entity.RuleServiceEntity;
import com.rules.entity.RulesDbConDetailsEntity;
import com.rules.entity.RulesDbDataEntity;
import com.rules.entity.RulesEntity;
import com.rules.entity.RulesGroupEntity;
import com.rules.model.RulesDbConDetailsObj;
import com.rules.model.RulesDbDataObj;
/*import com.rules.entity.RulesEngine;*/
import com.rules.model.RulesEngineObject;
import com.rules.model.RulesGroupObject;
import com.rules.model.RulesRequestModel;

@Service
public class RulesEngineDao {
	private static final Logger logger = LoggerFactory
			.getLogger(RulesEngineDao.class);

	
public RulesRequestModel saveRule(RulesRequestModel rulesRequestModel,EntityManager entitymanager) throws Exception{
		
		logger.info("inside class:FormulaEngineService.java,  method:saveFormula, message :: Starts");

		/*HashMap<String, Object> outputMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> outputList = new ArrayList<HashMap<String, Object>>();*/
	
		try{
				
			    RulesGroupObject ruleGroupObject = rulesRequestModel.getRuleGroupObject();
			    System.out.println("ruleGroupObject"+ruleGroupObject);
			    RulesGroupEntity rulesGroupEntity=null;
			    if(null==ruleGroupObject.getRuleFormulaGroupId()){
			    	rulesGroupEntity = new RulesGroupEntity();
			    	   System.out.println("INSERT RULE GROUP");
			    }else{
			    	   Query query = entitymanager.createQuery( "Select rge from RulesGroupEntity rge where rge.rulesGroupId = ?1" );
			    	   query.setParameter( 1, Integer.parseInt(ruleGroupObject.getRuleFormulaGroupId()));
			    	   rulesGroupEntity = (RulesGroupEntity)query.getSingleResult();
			    	   System.out.println("UPDATE RULE GROUP");
			    }
			    
			    
			    rulesGroupEntity.setRulesGroupName(ruleGroupObject.getRuleGroupName());
			    rulesGroupEntity.setRulesGroupDesc(ruleGroupObject.getRuleGroupDesc());
			    rulesGroupEntity.setStartDate(ruleGroupObject.getStartDate());
			    rulesGroupEntity.setVersion(ruleGroupObject.getVersion());
			    entitymanager.persist(rulesGroupEntity);
			    
			    List<RulesEngineObject> listObject = rulesRequestModel.getRuleEngineList();
			    
				
				for (int i = 0; i < listObject.size(); i++) {
					RulesEntity rulesEntity=null;
					
					RulesEngineObject rulesEngineObject =  listObject.get(i);
					System.out.println("rulesEngineObject"+rulesEngineObject);
					
					if(null==rulesEngineObject.getRuleId()){
						rulesEntity=new RulesEntity();
						System.out.println("INSERT RULE ENGINE");
					}else{
						 Query query = entitymanager.createQuery( "Select re from RulesEntity re where re.ruleId = ?1" );
				    	   query.setParameter( 1, Integer.parseInt(rulesEngineObject.getRuleId()) );
				    	   rulesEntity = (RulesEntity)query.getSingleResult();
				    	   System.out.println("UPDATE RULE ENGINE");
					}
					
					rulesEntity.setExternalServiceId(rulesEngineObject.getExternalServiceUrlId());
					rulesEntity.setFormulaGroupId(rulesEngineObject.getFormulaGroupId());
					rulesEntity.setRuleActionText(rulesEngineObject.getRuleActionText());
					rulesEntity.setRuleConditionText(rulesEngineObject.getRuleConditionText());
					
					rulesEntity.setRuleDesc(rulesEngineObject.getRuleDesc());
					rulesEntity.setRuleEmailList(rulesEngineObject.getRuleEmailList());
					rulesEntity.setRuleName(rulesEngineObject.getRuleName());
					rulesEntity.setRuleOperator(rulesEngineObject.getRuleOperator());
					rulesEntity.setRuleText(rulesEngineObject.getRuleText());
					rulesEntity.setRuleValidFrom(rulesEngineObject.getRuleValidFrom());
					rulesEntity.setRuleValidTo(rulesEngineObject.getRuleValidTo());
					rulesEntity.setRuleVersion(rulesEngineObject.getRuleVersion());
					
					rulesEntity.setDbfunctinonId(rulesEngineObject.getDbfunctinonId());
					rulesEntity.setDbqueryId(rulesEngineObject.getDbqueryId());
					
					rulesEntity.setRules_Group_Entity(rulesGroupEntity);
					entitymanager.persist(rulesEntity);
					ruleGroupObject.setRuleFormulaGroupId(rulesGroupEntity.getRulesGroupId()+"");
					rulesEngineObject.setRuleId(rulesEntity.getRuleId()+"");
					listObject.set(i, rulesEngineObject);
					rulesRequestModel.setRuleGroupObject(ruleGroupObject);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("inside class:FormulaEngineController.java,  method: saveFormula, Exception message1 ", e);
		}
		
		return rulesRequestModel;

	}
	
	public String deleteRule(String ruleId,EntityManager entitymanager) throws Exception{
		
		
		Query query = entitymanager
				.createQuery("delete  from RulesEntity  d   WHERE  d.ruleId= ?1 ");

		query.setParameter(1, Integer.parseInt(ruleId));

		int deletionCount = query.executeUpdate();
		if(deletionCount>0)
			return "SUCCESS";
		else
			return "FAILURE";
	}

	public List<RulesRequestModel> retrieveAllRules(EntityManager entitymanager) throws Exception{
		
		

		Query query = entitymanager
				.createQuery("select d  from RulesGroupEntity  d");
		
		Query query2 = entitymanager
				.createQuery("select d  from RulesEntity  d , RulesGroupEntity a where d.rules_Group_Entity = a and a.rulesGroupId = ?1");

		List<RulesRequestModel> listRules = new ArrayList<RulesRequestModel>();
		List<RulesGroupEntity> rulesGroupEntities = query.getResultList();
		RulesEngineObject  rulesObject = null;
		RulesGroupObject rulesGroupObject =null;		
		for (Iterator iterator = rulesGroupEntities.iterator(); iterator.hasNext();) {
			
			RulesRequestModel rulesRequestModel = new RulesRequestModel();
			RulesGroupEntity rulesGroupEntity = (RulesGroupEntity) iterator.next();
			
			rulesObject = new RulesEngineObject();
			rulesGroupObject = new RulesGroupObject();
			rulesGroupObject.setRuleFormulaGroupId(rulesGroupEntity.getRulesGroupId()+"");
			rulesGroupObject.setRuleGroupDesc(rulesGroupEntity.getRulesGroupName());
			rulesGroupObject.setStartDate(rulesGroupEntity.getStartDate());
			rulesGroupObject.setVersion(rulesGroupEntity.getVersion());
			
			query2.setParameter(1, rulesGroupEntity.getRulesGroupId());
			List<RulesEntity> rulesEntities = query2.getResultList();
			for (Iterator iterator2 = rulesEntities.iterator(); iterator2.hasNext();) {
				RulesEntity rulesEntity = (RulesEntity) iterator2.next();
				rulesObject = new RulesEngineObject();
				rulesObject.setRuleId(""+rulesEntity.getRuleId());
				rulesObject.setExternalServiceUrlId(rulesEntity.getExternalServiceId());
				rulesObject.setFormulaGroupId(rulesEntity.getFormulaGroupId());
				rulesObject.setRuleActionText(rulesEntity.getRuleActionText());
				rulesObject.setRuleConditionText(rulesEntity.getRuleConditionText());
				
				rulesObject.setRuleDesc(rulesEntity.getRuleDesc());
				rulesObject.setRuleEmailList(rulesEntity.getRuleEmailList());
				rulesObject.setRuleName(rulesEntity.getRuleName());
				rulesObject.setRuleOperator(rulesEntity.getRuleOperator());
				rulesObject.setRuleText(rulesEntity.getRuleText());
				rulesObject.setRuleValidFrom(rulesEntity.getRuleValidFrom());
				rulesObject.setRuleValidTo(rulesEntity.getRuleValidTo());
				rulesObject.setRuleVersion(rulesEntity.getRuleVersion());
				
				rulesObject.setDbfunctinonId(rulesEntity.getDbfunctinonId());
				rulesObject.setDbqueryId(rulesEntity.getDbqueryId());
				
				rulesRequestModel.getRuleEngineList().add(rulesObject);
			}
			
			rulesRequestModel.setRuleGroupObject(rulesGroupObject);
			
			
			listRules.add(rulesRequestModel);
		}

		
		return listRules;
	}
	
	
	public RulesRequestModel ruleDetailsByRuleId(String ruleId,EntityManager entitymanager) throws Exception{

		Query query = entitymanager.createQuery("select d  from RulesGroupEntity  d where d.rulesGroupId=?1");
		
		Query query2 = entitymanager.createQuery("select d  from RulesEntity  d , RulesGroupEntity a where d.rules_Group_Entity = a and d.ruleId = ?1");

		RulesRequestModel rulesRequestModel = null;
		int ruleGroupId=0;
		RulesEngineObject  rulesObject = null;
		RulesGroupObject rulesGroupObject =null;		
			query2.setParameter(1, Integer.parseInt(ruleId));
			List<RulesEntity> rulesEntities = query2.getResultList();
			rulesRequestModel = new RulesRequestModel();
			for (Iterator iterator2 = rulesEntities.iterator(); iterator2.hasNext();) {
				RulesEntity rulesEntity = (RulesEntity) iterator2.next();
				rulesObject = new RulesEngineObject();
				rulesObject.setRuleId(""+rulesEntity.getRuleId());
				rulesObject.setExternalServiceUrlId(rulesEntity.getExternalServiceId());
				rulesObject.setFormulaGroupId(rulesEntity.getFormulaGroupId());
				rulesObject.setRuleActionText(rulesEntity.getRuleActionText());
				rulesObject.setRuleConditionText(rulesEntity.getRuleConditionText());
				
				rulesObject.setRuleDesc(rulesEntity.getRuleDesc());
				rulesObject.setRuleEmailList(rulesEntity.getRuleEmailList());
				rulesObject.setRuleName(rulesEntity.getRuleName());
				rulesObject.setRuleOperator(rulesEntity.getRuleOperator());
				rulesObject.setRuleText(rulesEntity.getRuleText());
				rulesObject.setRuleValidFrom(rulesEntity.getRuleValidFrom());
				rulesObject.setRuleValidTo(rulesEntity.getRuleValidTo());
				rulesObject.setRuleVersion(rulesEntity.getRuleVersion());
				
				rulesObject.setDbfunctinonId(rulesEntity.getDbfunctinonId());
				rulesObject.setDbqueryId(rulesEntity.getDbqueryId());
				
				rulesRequestModel.getRuleEngineList().add(rulesObject);
				ruleGroupId = rulesEntity.getRules_Group_Entity().getRulesGroupId();
			}
			query.setParameter(1, ruleGroupId);
			List<RulesGroupEntity> rulesGroupEntities = query.getResultList();
			
			for (Iterator iterator = rulesGroupEntities.iterator(); iterator.hasNext();) {
				
				
				RulesGroupEntity rulesGroupEntity = (RulesGroupEntity) iterator.next();
				
				rulesObject = new RulesEngineObject();
				rulesGroupObject = new RulesGroupObject();
				rulesGroupObject.setRuleFormulaGroupId(rulesGroupEntity.getRulesGroupId()+"");
				rulesGroupObject.setRuleGroupDesc(rulesGroupEntity.getRulesGroupName());
				rulesGroupObject.setStartDate(rulesGroupEntity.getStartDate());
				rulesGroupObject.setVersion(rulesGroupEntity.getVersion());
				
				
			}
			rulesRequestModel.setRuleGroupObject(rulesGroupObject);
			
			
			
		
		
		return rulesRequestModel;
	}
	
	public RulesRequestModel ruleDetailsByRuleGroupId(String ruleGroupId,EntityManager entitymanager) throws Exception{

		Query query = entitymanager.createQuery("select d  from RulesGroupEntity  d where d.rulesGroupId=?1");
		
		Query query2 = entitymanager.createQuery("select d  from RulesEntity  d , RulesGroupEntity a where d.rules_Group_Entity = a and a.rulesGroupId = ?1");

		RulesRequestModel rulesRequestModel = null;
		query.setParameter(1, Integer.parseInt(ruleGroupId));
		List<RulesGroupEntity> rulesGroupEntities = query.getResultList();
		RulesEngineObject  rulesObject = null;
		RulesGroupObject rulesGroupObject =null;		
		for (Iterator iterator = rulesGroupEntities.iterator(); iterator.hasNext();) {
			
			rulesRequestModel = new RulesRequestModel();
			RulesGroupEntity rulesGroupEntity = (RulesGroupEntity) iterator.next();
			
			rulesObject = new RulesEngineObject();
			rulesGroupObject = new RulesGroupObject();
			rulesGroupObject.setRuleFormulaGroupId(rulesGroupEntity.getRulesGroupId()+"");
			rulesGroupObject.setRuleGroupDesc(rulesGroupEntity.getRulesGroupName());
			rulesGroupObject.setStartDate(rulesGroupEntity.getStartDate());
			rulesGroupObject.setVersion(rulesGroupEntity.getVersion());
			
			query2.setParameter(1, rulesGroupEntity.getRulesGroupId());
			List<RulesEntity> rulesEntities = query2.getResultList();
			for (Iterator iterator2 = rulesEntities.iterator(); iterator2.hasNext();) {
				RulesEntity rulesEntity = (RulesEntity) iterator2.next();
				rulesObject = new RulesEngineObject();
				rulesObject.setRuleId(""+rulesEntity.getRuleId());
				rulesObject.setExternalServiceUrlId(rulesEntity.getExternalServiceId());
				rulesObject.setFormulaGroupId(rulesEntity.getFormulaGroupId());
				rulesObject.setRuleActionText(rulesEntity.getRuleActionText());
				rulesObject.setRuleConditionText(rulesEntity.getRuleConditionText());
				
				rulesObject.setRuleDesc(rulesEntity.getRuleDesc());
				rulesObject.setRuleEmailList(rulesEntity.getRuleEmailList());
				rulesObject.setRuleName(rulesEntity.getRuleName());
				rulesObject.setRuleOperator(rulesEntity.getRuleOperator());
				rulesObject.setRuleText(rulesEntity.getRuleText());
				rulesObject.setRuleValidFrom(rulesEntity.getRuleValidFrom());
				rulesObject.setRuleValidTo(rulesEntity.getRuleValidTo());
				rulesObject.setRuleVersion(rulesEntity.getRuleVersion());
				
				rulesObject.setDbfunctinonId(rulesEntity.getDbfunctinonId());
				rulesObject.setDbqueryId(rulesEntity.getDbqueryId());
				
				rulesRequestModel.getRuleEngineList().add(rulesObject);
			}
			
			rulesRequestModel.setRuleGroupObject(rulesGroupObject);
			
			
			
		}
		
		return rulesRequestModel;
	}
	
	
	public RulesDbConDetailsObj addDbConnections(RulesDbConDetailsObj rulesDbConDetailsObj,EntityManager entitymanager) throws Exception{
		
		RulesDbConDetailsEntity entity =  new RulesDbConDetailsEntity();
		entity.setConnName(rulesDbConDetailsObj.getConnName());
		entity.setConnUrl(rulesDbConDetailsObj.getConnUrl());
		entity.setPassword(rulesDbConDetailsObj.getPassword());
		entity.setUserName(rulesDbConDetailsObj.getUserName());
		entity.setDriver(rulesDbConDetailsObj.getDriver());
		entitymanager.persist(entity);
		
		rulesDbConDetailsObj.setConnId(entity.getConnId());
		return rulesDbConDetailsObj;
	}
	
	public RulesDbDataObj addDBQueryFunction(RulesDbDataObj rulesDbDataObj,EntityManager entitymanager) throws Exception{
		
		RulesDbDataEntity entity =  new RulesDbDataEntity();
		entity.setCallDetails(rulesDbDataObj.getCallDetails());
		entity.setCallType(rulesDbDataObj.getCallType());
		entity.setInputParams(rulesDbDataObj.getInputParams());
		entity.setOutputParams(rulesDbDataObj.getOutputParams());
		
		Query query = entitymanager.createQuery("select d  from RulesDbConDetailsEntity  d where d.connId=?1");
		query.setParameter(1, rulesDbDataObj.getRulesDbConnId());
		
		RulesDbConDetailsEntity dbConDetailsEntity = (RulesDbConDetailsEntity)query.getSingleResult();
		
		entity.setRules_Db(dbConDetailsEntity);
		
		
		entitymanager.persist(entity);
		
		rulesDbDataObj.setDataId(entity.getDataId());
		
		return rulesDbDataObj;
		
	}
	
	public RulesDbDataObj callDBQueryFunction(RulesDbDataObj rulesDbDataObj,EntityManager entitymanager) throws Exception{
		
		
		Query query2 = entitymanager.createQuery("select d  from RulesDbDataEntity  d where d.dataId=?1");
		query2.setParameter(1, rulesDbDataObj.getDataId());
		RulesDbDataEntity rulesDbDataEntity = (RulesDbDataEntity) query2.getSingleResult();
		System.out.println("rulesDbDataEntity"+rulesDbDataEntity.getRules_Db().getConnId());
		Query query = entitymanager.createQuery("select d  from RulesDbConDetailsEntity  d where d.connId=?1");
		query.setParameter(1, rulesDbDataEntity.getRules_Db().getConnId());
		RulesDbConDetailsEntity rulesDbConDetailsEntity = (RulesDbConDetailsEntity)query.getSingleResult();
		System.out.println("rulesDbDataEntity"+rulesDbConDetailsEntity.getDriver());
		System.out.println("rulesDbDataEntity"+rulesDbConDetailsEntity.getDriver());
		Connection conn = createDBConnection(rulesDbConDetailsEntity);
		
		if(null!=conn ){
			if(null!= rulesDbDataEntity.getCallType() && "FUNCTION".equalsIgnoreCase(rulesDbDataEntity.getCallType().toUpperCase())){
				// call method to execute function
				rulesDbDataObj.setOutputResult(executeFunction(rulesDbDataEntity, rulesDbDataObj, conn));
			
					
			}else{
				// call method to execute query
			}
		}else{
			throw new Exception("DB Connection is null");
		}
		return 	rulesDbDataObj;
	}
	
	private Map<String,String> executeFunction(RulesDbDataEntity rulesDbDataEntity,RulesDbDataObj rulesDbDataObj,Connection conn) throws Exception{
		Map<String,String> results = new HashMap<String,String>();
		Map<String,String> inputDataValues = rulesDbDataObj.getInputData();
		String []inputParams = null;
		String []outputParams = null;
		CallableStatement callableStatement = null;
		StringBuilder callableQuery = new StringBuilder("{ ? = call "+rulesDbDataEntity.getCallDetails()+"(");
		if(null!=rulesDbDataEntity.getInputParams()){
			inputParams = rulesDbDataEntity.getInputParams().split(";");
			if(null!=inputParams && inputParams.length>0){
				for (int i = 0; i < inputParams.length; i++) {
					callableQuery.append("?,");
					
				}
				callableQuery.append(")}");
				
				if(callableQuery.toString().contains(","))
				callableQuery.replace(callableQuery.lastIndexOf(","),callableQuery.lastIndexOf(",")+1,"");
				
				callableQuery = new StringBuilder(callableQuery.toString());
			}else{
				callableQuery.append(")}");
			}
		}else{
			callableQuery.append(")}");
		}
		System.out.println("CALLABLE FUNCTION :"+callableQuery);
		callableStatement =  conn.prepareCall(callableQuery.toString());
				
		if(null!=rulesDbDataEntity.getInputParams()){
			inputParams = rulesDbDataEntity.getInputParams().split(";");
			if(null!=inputParams){
				for (int i = 1; i < inputParams.length +1; i++) {
					
					callableStatement.setInt(i+1,Integer.parseInt(inputDataValues.get(inputParams[i-1])));
					
				}
				
				
				
			}
		}
		
		if(null!=rulesDbDataEntity.getOutputParams()){
			outputParams = rulesDbDataEntity.getOutputParams().split(";");
			
			if(null!=outputParams){
				for (int i = 1; i < outputParams.length +1; i++) {
					
					
					callableStatement.registerOutParameter(i, Types.INTEGER);
				}
				callableStatement.execute();
				for (int i = 1; i < outputParams.length +1; i++) {
					
					
					results.put(outputParams[i-1], callableStatement.getInt(i)+"");
				}
				
				
			}
		}
		
		return results;
	}
	
	private Connection createDBConnection(RulesDbConDetailsEntity rulesDbConDetailsEntity) throws Exception{
		
		Connection connection = null;
		try{
		Class.forName(rulesDbConDetailsEntity.getDriver());
		connection = DriverManager.getConnection(
				rulesDbConDetailsEntity.getConnUrl(),rulesDbConDetailsEntity.getUserName(), rulesDbConDetailsEntity.getPassword());
		}catch(Exception e){
			throw e;
		}
		
		return connection;
	}

	public List<HashMap<String, Object>> saveFormula(FormulaRequestModel model) {

		logger.info("inside class:FormulaEngineService.java,  method:saveFormula, message :: Starts");

		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> outputList = new ArrayList<HashMap<String, Object>>();
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		try {

			entitymanager.getTransaction().begin();

			HashMap<String, FormulaGroupObject> requestMap = model
					.getFormulaGroupInfo();

			Set set1 = requestMap.keySet();
			Iterator itr1 = set1.iterator();

			while (itr1.hasNext()) {
				String key = itr1.next().toString();
				outputMap = new HashMap<String, Object>();
				FormulaGroupObject object1 = (FormulaGroupObject) requestMap
						.get(key);
				List listObject = object1.getFormula();
				try {
					Groups group = new Groups();
					group.setFormulaGroupName(object1.getFormulaGroupName());
					group.setStartDate(object1.getStartDate());
					group.setVersion(object1.getVersion());
					logger.info(" inside class:FormulaEngineService.java,  method:saveFormula, Descp "
							+ object1.getFormulaGroupDescription());
					logger.info(" inside class:FormulaEngineService.java,  method:saveFormula, Name "
							+ object1.getFormulaGroupName());

					for (int i = 0; i < listObject.size(); i++) {
						Formula newFor = new Formula();
						FormulaObject formulaObject = (FormulaObject) listObject
								.get(i);
						newFor.setFormulaName(formulaObject
								.getFormulaDescription());
						newFor.setFormulaString(formulaObject.getFormulaStr());
						newFor.setSequenceNumber(formulaObject.getFormulaSeq());
						newFor.setGroups(group);
						entitymanager.persist(newFor);

					}
					outputMap.put("status: ", true);
					outputMap.put("formulaGroupName: ",
							object1.getFormulaGroupName());
					outputList.add(outputMap);
				} catch (Exception e) {
					outputMap.put("status: ", false);
					outputMap.put("formulaGroupName: ",
							object1.getFormulaGroupName());
					outputList.add(outputMap);
					logger.error(
							"inside class:FormulaEngineController.java,  method: saveFormula, Exception message1 ",
							e);
				}

			}

			entitymanager.getTransaction().commit();
			entitymanager.close();
			factory.close();
			logger.info("inside class:FormulaEngineService.java,  method:saveFormula, message :: Exit");
			return outputList;
		} catch (Exception e) {
			entitymanager.getTransaction().commit();
			entitymanager.close();
			factory.close();
			logger.error(
					"inside class:FormulaEngineController.java,  method: saveFormula, Exception message ",
					e);
			return outputList;
		}

	}

	public ArrayList<HashMap<String, String>> searchByGroup(
			String formulaGroup, int version) {

		logger.info("inside class: FormulaEngineService.java,  method: searchByGroup, message :: Starts");

		ArrayList<HashMap<String, String>> returnList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> returnMap = new HashMap<String, String>();
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();

		try {
			List l = entitymanager
					.createQuery(
							"select d from Groups g, Formula d"
									+ " WHERE g = d.group and g.formulaGroupName= :formulaGroupName and g.version= :version")
					.setParameter("formulaGroupName", formulaGroup)
					.setParameter("version", version).getResultList();
			for (Object object : l) {
				returnMap = new HashMap<String, String>();
				Formula formulaDetailObject = (Formula) object;
				returnMap.put("formulaGroup ", formulaGroup);
				returnMap.put("version ", Integer.toString(version));
				returnMap.put("formulaName",
						formulaDetailObject.getFormulaName());
				returnMap.put("formulaString",
						formulaDetailObject.getFormulaString());
				returnList.add(returnMap);
			}
			logger.info("inside class: FormulaEngineService.java,  method: searchByGroup, message :: Ends");
			return returnList;
		} catch (Exception e) {
			logger.error(
					"inside class:FormulaEngineController.java,  method: searchByGroup, Exception message ",
					e);
			return returnList;
		}
	}

	public String validateFormula(String formula) {

		try {
			ExpressionParser parser = new SpelExpressionParser();
			parser.parseExpression(formula.replace(" ", ""));
			return formula + " Is VALID";
		}

		catch (Exception e) {
			logger.error(
					"inside class:FormulaEngineController.java,  method: validateFormula, Exception message ",
					e);
			return formula + " Is INVALID";
		}

	}

	public List<ExecuteFormulaModel> executeFormula(String inputJSON) {

		logger.info("inside class: FormulaEngineService.java,  method: executeFormula, message :: Starts");

		// This is the JSON input which has the Formula Group
		String formulaJSON = "";

		logger.info("Input Formula Group:  " + formulaJSON);
		Gson gson = new Gson();
		// Parsing the JSON input to get the group of Formulas
		FormulaGroupObject formulaGroupsArray[] = gson.fromJson(inputJSON,
				FormulaGroupObject[].class);
		List<ExecuteFormulaModel> formulaGroupResultsList = new ArrayList<>();

		// Looping through the Formula Groups
		for (FormulaGroupObject formulaGroups : formulaGroupsArray) {
			ExecuteFormulaModel formulaGroupResults = new ExecuteFormulaModel();
			List<FormulaExecuteObject> formulaResultList = new ArrayList<>();

			logger.info("inside class: FormulaEngineService.java,  method: executeFormula, Group Name: "
					+ formulaGroups.getFormulaGroupName());

			formulaGroupResults.setFormulaGroupName(formulaGroups
					.getFormulaGroupName());
			int order = 1;

			// Looping through each Formula
			for (FormulaObject formulaName : formulaGroups.getFormula()) {
				// Setting the Formula Result
				FormulaExecuteObject formulaResult = new FormulaExecuteObject();
				logger.info("inside class: FormulaEngineService.java,  method: executeFormula, message ::Description "
						+ formulaName.getFormulaDescription());
				formulaResult.setFormulaDescription(formulaName
						.getFormulaDescription());
				formulaResult
						.setFormulaValue(getFormulaValue(formulaName) + "");
				formulaResult.setFormulaOrder(order++);

				formulaResultList.add(formulaResult);
			}
			formulaGroupResults.setFormulaResultList(formulaResultList);
			// Adding the Formula Group Results to the List
			formulaGroupResultsList.add(formulaGroupResults);
		}

		logger.info("Final JSON");
		logger.info(gson.toJson(formulaGroupResultsList));
		logger.info("inside class: FormulaEngineService.java,  method: executeFormula, message :: Exit");
		return formulaGroupResultsList;
	}

	/**
	 * This method will get the formula Value for each formula
	 * 
	 * @return
	 */
	private double getFormulaValue(FormulaObject formulaGroup) {

		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message :: Starts");

		Formula formualDetail = searchByFormulaName(formulaGroup
				.getFormulaDescription());
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message ::getFormulaText : "
				+ formualDetail.getFormulaString());
		String formulaExpr = formualDetail.getFormulaString();
		LinkedHashMap<String, String> formulaValuesMap = formulaGroup
				.getFormulaValues();
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message ::Formula Name: "
				+ formulaGroup.getFormulaDescription());
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message ::Formula:  "
				+ formulaExpr);

		for (String key : formulaValuesMap.keySet()) {
			logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message ::Key:  "
					+ key + ", Value:  " + formulaValuesMap.get(key));
			formulaExpr = formulaExpr.replace(key, formulaValuesMap.get(key));
		}
		ExpressionParser parser = new SpelExpressionParser();
		double formulaValue = parser.parseExpression(formulaExpr).getValue(
				Double.class);

		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message :: Expr Value:   "
				+ formulaExpr);
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message :: formulaValue:   "
				+ formulaValue);

		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValue, message :: Exit");
		return formulaValue;
	}

	public HashMap<String, String> searchByFormulaNameNew(String formulaGroup,
			String formulaName, int version) {

		logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameNew, message :: Starts");

		HashMap<String, String> outputMap = new HashMap<String, String>();
		Formula formulaDetail = new Formula();
		try {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("Application");
			EntityManager entitymanager = factory.createEntityManager();

			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameNew, message ::formulaGroup   "
					+ formulaGroup);
			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameNew, message ::formulaName   "
					+ formulaName);
			List l = entitymanager
					.createQuery(
							"select d from Groups g, Formula d"
									+ " WHERE g = d.group and g.formulaGroupName= :formulaGroupName"
									+ " and  d.formulaName= :formulaName  and g.version= :version ")
					.setParameter("formulaGroupName", formulaGroup)
					.setParameter("formulaName", formulaName)
					.setParameter("version", version).getResultList();
			for (Object object : l) {
				formulaDetail = (Formula) object;
				outputMap.put("formulaGroup ", formulaGroup);
				outputMap.put("version ", Integer.toString(version));
				outputMap.put("formulaName ", formulaDetail.getFormulaName());
				outputMap.put("formulaString ",
						formulaDetail.getFormulaString());
			}
			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameNew, message :: Exit");
			return outputMap;

		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: searchByFormulaNameNew, Exception message :: ",
					e);
			return null;
		}
	}

	public Formula searchByFormulaName(String formulaName) {

		logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaName, message :: Starts");

		Formula formulaDetail = new Formula();
		try {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("Application");
			EntityManager entitymanager = factory.createEntityManager();

			List l = entitymanager
					.createQuery(
							"select d from Formula d"
									+ " WHERE  d.formulaName= :formulaName")
					.setParameter("formulaName", formulaName).getResultList();
			for (Object object : l) {
				formulaDetail = (Formula) object;

			}
			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaName, message :: Exit");
			return formulaDetail;

		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: searchByFormulaName, Exception message :: ",
					e);
			return null;
		}
	}

	public Formula searchByFormulaNameForUpdate(String formulaName,
			String formulaGroup, int version) {
		Formula formulaDetail = new Formula();
		try {

			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, message :: Starts");

			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("Application");
			EntityManager entitymanager = factory.createEntityManager();

			List l = entitymanager
					.createQuery(
							"select d from Groups g, Formula d"
									+ " WHERE g = d.group and g.formulaGroupName= :formulaGroupName"
									+ " and  d.formulaName= :formulaName  and g.version= :version ")
					.setParameter("formulaGroupName", formulaGroup)
					.setParameter("formulaName", formulaName)
					.setParameter("version", version).getResultList();
			for (Object object : l) {
				formulaDetail = (Formula) object;

			}
			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, message :: Exit");
			return formulaDetail;
		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, Exception message :: ",
					e);
			return null;
		}
	}

	public HashMap<String, Object> deleteByFormulaGroup(
			FormulaRequestModel objFormulaRequestModel) {

		logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaGroup, message :: Starts");

		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		try {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("Application");
			EntityManager entitymanager = factory.createEntityManager();

			EntityTransaction deleteTransaction = entitymanager
					.getTransaction();

			deleteTransaction.begin();
			HashMap<String, FormulaGroupObject> requestMap = objFormulaRequestModel
					.getFormulaGroupInfo();

			try {

				Set set1 = requestMap.keySet();
				Iterator itr1 = set1.iterator();
				// / entitymanager.getTransaction().begin();
				while (itr1.hasNext()) {
					String key = itr1.next().toString();
					FormulaGroupObject object1 = (FormulaGroupObject) requestMap
							.get(key);
					Groups group = new Groups();
					group.setFormulaGroupName(object1.getFormulaGroupName());
					group.setVersion(object1.getVersion());

					Query query = entitymanager
							.createQuery("delete   from Formula  d   WHERE   d.group = (select g from Groups g "
									+ " WHERE g.formulaGroupName= ?1 and g.version= ?2 )  ");

					query.setParameter(1, group.getFormulaGroupName());
					query.setParameter(2, group.getVersion());

					int deletionCount = query.executeUpdate();

					if (deletionCount > 0) {

						logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaGroup, message: Done.. with formula Name");

						Query query1 = entitymanager
								.createQuery("delete   from Groups  g   WHERE g.formulaGroupName= ?1 "
										+ "and g.version= ?2 ");
						query1.setParameter(1, group.getFormulaGroupName());
						query1.setParameter(2, group.getVersion());

						int deletionCount1 = query1.executeUpdate();
						if (deletionCount1 > 0) {

							logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaGroup, message: Done... with formulaGroup");
							returnMap.put("formulaGroupName ",
									group.getFormulaGroupName());
							returnMap.put("status ", true);
						}
					}

					if (deletionCount == 0) {
						returnMap.put("formulaGroupName ",
								group.getFormulaGroupName());
						returnMap.put("status ", true);
						returnMap.put("Message ",
								"Formula Group " + group.getFormulaGroupName()
										+ " not found. ");
					}

				}

				deleteTransaction.commit();
				returnMap.put("status ", true);
				logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaGroup, message :: Exit");
				return returnMap;
			}

			catch (Exception e) {
				returnMap.put("formulaGroupName ", "");
				returnMap.put("status ", false);
				logger.error(
						"inside class: FormulaEngineService.java,  method: deleteByFormulaGroup, Exception message1 :: ",
						e);
				return returnMap;
			}

		}

		catch (Exception e) {
			returnMap.put("formulaGroupName ", "");
			returnMap.put("status ", false);
			logger.error(
					"inside class: FormulaEngineService.java,  method: deleteByFormulaGroup, Exception message2 :: ",
					e);
			return returnMap;
		}
	}

	/*
	 * Conmment By: TATA Consultancy Services Date: 06/15/2016 Description: To
	 * Delete Formula Name. To Delete a Particular Formula Name in the Group
	 */

	public HashMap<String, HashMap<String, Object>> deleteByFormulaName(
			FormulaRequestModel objFormulaRequestModel) {
		logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaName, message :: Starts");
		HashMap<String, HashMap<String, Object>> outputGroupMap = new HashMap<String, HashMap<String, Object>>();
		HashMap<String, Object> outputFormulaMap = new HashMap<String, Object>();
		try {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("Application");
			EntityManager entitymanager = factory.createEntityManager();

			EntityTransaction deleteTransaction = entitymanager
					.getTransaction();

			deleteTransaction.begin();
			HashMap<String, FormulaGroupObject> requestMap = objFormulaRequestModel
					.getFormulaGroupInfo();

			try {

				Set set1 = requestMap.keySet();
				Iterator itr1 = set1.iterator();
				while (itr1.hasNext()) {
					String key = itr1.next().toString();
					FormulaGroupObject object1 = (FormulaGroupObject) requestMap
							.get(key);
					List listObject = object1.getFormula();
					Groups group = new Groups();
					group.setFormulaGroupName(object1.getFormulaGroupName());
					group.setVersion(object1.getVersion());

					for (int i = 0; i < listObject.size(); i++) {
						FormulaObject formulaObject = (FormulaObject) listObject
								.get(i);

						Query query = entitymanager
								.createQuery("delete  from Formula  d   WHERE  d.formulaName= ?1  and "
										+ "  d.group = (select g from Groups g "
										+ " WHERE g.formulaGroupName= ?2 and g.version= ?3 )  ");

						query.setParameter(1,
								formulaObject.getFormulaDescription());
						query.setParameter(2, group.getFormulaGroupName());
						query.setParameter(3, group.getVersion());

						int deletionCount = query.executeUpdate();

						if (deletionCount > 0) {

							logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaName, message :: Done..");
							outputFormulaMap.put("formulaName",
									formulaObject.getFormulaDescription());
							outputFormulaMap.put("deleteStatus: ", true);

						}

						if (deletionCount == 0) {
							outputFormulaMap.put("formulaGroupName ",
									formulaObject.getFormulaDescription());
							outputFormulaMap.put("status ", true);
							outputFormulaMap.put("Message ", "Formula Name "
									+ formulaObject.getFormulaDescription()
									+ " not found. ");
						}
						outputGroupMap.put(group.getFormulaGroupName(),
								outputFormulaMap);
					}
				}

				entitymanager.getTransaction().commit();
				entitymanager.close();
				factory.close();
				logger.info("inside class: FormulaEngineService.java,  method: deleteByFormulaName, message :: Exit");
				return outputGroupMap;
			}

			catch (Exception e) {
				logger.error(
						"inside class: FormulaEngineService.java,  method: deleteByFormulaName, Exception message1 :: ",
						e);
				return outputGroupMap;
			}
		}

		catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: deleteByFormulaName, Exception message2 :: ",
					e);
			return outputGroupMap;
		}

	}

	public List<HashMap<String, Object>> updateFormulaByGroup(
			FormulaRequestModel objFormulaRequestModel) {
		logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: Starts");
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, FormulaGroupObject> requestMap = objFormulaRequestModel
				.getFormulaGroupInfo();
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		FormulaGroupObject object1 = null;

		try {

			Set set1 = requestMap.keySet();
			Iterator itr1 = set1.iterator();
			entitymanager.getTransaction().begin();
			while (itr1.hasNext()) {
				String key = itr1.next().toString();
				objectMap = new HashMap<String, Object>();
				object1 = (FormulaGroupObject) requestMap.get(key);
				List listObject = object1.getFormula();
				Groups group = new Groups();
				group.setFormulaGroupName(object1.getFormulaGroupName());
				group.setVersion(object1.getVersion());

				List grps = entitymanager
						.createQuery(
								"select g from Groups g"
										+ " WHERE  g.formulaGroupName= :formulaGroupName  and g.version= :version")
						.setParameter("formulaGroupName",
								object1.getFormulaGroupName())
						.setParameter("version", object1.getVersion())
						.getResultList();

				int formulaGroupId = 0;

				if (grps != null && grps.size() > 0) {

					for (Object object : grps) {

						Groups grp = (Groups) object;

						logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message ::group id --"
								+ grp.getFormulaGroupId());

						logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message ::group name --"
								+ grp.getFormulaGroupName());

						formulaGroupId = grp.getFormulaGroupId();
						group.setFormulaGroupId(formulaGroupId);
						break;

					}

				}

				for (int i = 0; i < listObject.size(); i++) {
					FormulaObject formulaObject = (FormulaObject) listObject
							.get(i);

					Formula formulaDetail = searchByFormulaNameForUpdate(
							formulaObject.getFormulaDescription(),
							object1.getFormulaGroupDescription(),
							object1.getVersion(), formulaGroupId);

					if (formulaDetail.getFormulaName() != null) {

						logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: inside else 1");

						Query query = entitymanager
								.createQuery("UPDATE Formula d set d.formulaString= ?1 where d.formulaName= ?2 and "
										+ "  d.group = (select g from Groups g "
										+ " WHERE g.formulaGroupName= ?3 and g.version= ?4 ) ");
						query.setParameter(1, formulaObject.getFormulaStr());
						query.setParameter(2,
								formulaObject.getFormulaDescription());
						query.setParameter(3, group.getFormulaGroupName());
						query.setParameter(4, group.getVersion());
						int updateCount = query.executeUpdate();
						logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: updateCount   "
								+ updateCount);
					}

					else {
						logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: inside else 2");

						List fomulaGroupList = entitymanager
								.createQuery(
										"select g from Groups g "
												+ " WHERE   g.formulaGroupName= :formulaGroupName and g.version= :version")
								.setParameter("formulaGroupName",
										object1.getFormulaGroupName())
								.setParameter("version", object1.getVersion())
								.getResultList();

						if (fomulaGroupList.size() > 0) {
							for (Object object : fomulaGroupList) {
								logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: inside else 3");
								Groups objGroups = (Groups) object;
								Formula newFor = new Formula();
								newFor.setFormulaName(formulaObject
										.getFormulaDescription());
								newFor.setFormulaString(formulaObject
										.getFormulaStr());
								newFor.setGroups(objGroups);
								entitymanager.merge(newFor);
							}
						} else {
							logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: inside else 4");
							Formula newFor = new Formula();
							newFor.setFormulaName(formulaObject
									.getFormulaDescription());
							newFor.setFormulaString(formulaObject
									.getFormulaStr());
							newFor.setGroups(group);
							entitymanager.merge(newFor);

						}

					}

				}

				objectMap.put("formulaGroupName: ",
						object1.getFormulaGroupName());
				objectMap.put("status_code: ", "200");
				objectMap
						.put("status: ",
								"Formal Group with corresponding formula's Inserted/Updated successfully");

				returnList.add(objectMap);

			}
			entitymanager.getTransaction().commit();
			entitymanager.close();
			factory.close();
		} catch (Exception e) {
			entitymanager.getTransaction().commit();
			entitymanager.close();
			factory.close();

			objectMap.put("formulaGroupName: ",
					object1 != null ? object1.getFormulaGroupName() : "");
			objectMap.put("status_code: ", "300");
			objectMap
					.put("status: ",
							"Error while Inserting/Updating Formal Group with corresponding formula's ");

			returnList.add(objectMap);

			logger.error(
					"inside class: FormulaEngineService.java,  method: updateFormulaByGroup, Exception message :: ",
					e);

		}
		logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByGroup, message :: Exit");
		return returnList;
	}

	public Formula searchByFormulaNameForUpdate(String formulaName,
			String formulaGroup, int version, int formulaGrpId) {
		logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, message :: Starts");
		Formula formulaDetail = new Formula();
		try {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("Application");
			EntityManager entitymanager = factory.createEntityManager();

			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, message :: formulaGroup   "
					+ formulaName);

			List l = entitymanager
					.createQuery(
							"select d from  Formula d"
									+ " WHERE "
									+ "  d.formulaName= :formulaName and d.group= ( select g from Groups g where g.formulaGroupId= :formulaGroupId  )")
					.setParameter("formulaName", formulaName)
					.setParameter("formulaGroupId", formulaGrpId)
					.getResultList();
			for (Object object : l) {
				formulaDetail = (Formula) object;
				logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, message :: coming inside geting formula object");
			}
			logger.info("inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, message :: Exit");
			return formulaDetail;

		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: searchByFormulaNameForUpdate, Exception message :: ",
					e);
			return null;
		}
	}

	public List<HashMap<String, Object>> updateFormulaByName(
			HashMap<String, String> inputObject) {
		logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByName, message :: Starts");
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		String formulaName = "";

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		try {

			entitymanager.getTransaction().begin();

			java.util.Set<String> formulaSet = inputObject.keySet();
			Iterator formulaItr = formulaSet.iterator();
			while (formulaItr.hasNext()) {
				try {
					objectMap = new HashMap<String, Object>();
					formulaName = formulaItr.next().toString();
					String formulaText = inputObject.get(formulaName);
					logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByName, message :: formulaName "
							+ formulaName + " formulaText   " + formulaText);

					Query query = entitymanager
							.createQuery("UPDATE Formula d SET d.formulaText= ?1 where d.formulaName= ?2");
					query.setParameter(2, formulaName);
					query.setParameter(1, formulaText);
					int updateCount = query.executeUpdate();
					objectMap.put("status ", true);
					objectMap.put("formulaName ", formulaName);
					returnList.add(objectMap);
				} catch (Exception e) {
					objectMap.put("status ", false);
					objectMap.put("formulaName ", formulaName);
					returnList.add(objectMap);
					logger.error(
							"inside class: FormulaEngineService.java,  method: updateFormulaByName, Exception message1 :: ",
							e);
				}
			}

			entitymanager.getTransaction().commit();
			entitymanager.close();
			factory.close();

		} catch (Exception e) {
			entitymanager.getTransaction().commit();
			entitymanager.close();
			factory.close();
			objectMap.put("status ", false);
			objectMap.put("formulaName ", formulaName);
			returnList.add(objectMap);
			logger.error(
					"inside class: FormulaEngineService.java,  method: updateFormulaByName, Exception message2 :: ",
					e);
		}
		logger.info("inside class: FormulaEngineService.java,  method: updateFormulaByName, message :: Exit");
		return returnList;
	}

	private double getFormulaValueExtn(FormulaObject formulaGroup,
			LinkedHashMap<String, String> formulaPrevValues) {
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValueExtn, message :: Starts");
		Formula formualDetail = searchByFormulaName(formulaGroup
				.getFormulaDescription());
		String formulaExpr = formualDetail.getFormulaString();
		LinkedHashMap<String, String> formulaValuesMap = formulaGroup
				.getFormulaValues();
		if (formulaValuesMap != null && formulaValuesMap.size() > 0) {

			for (String key : formulaValuesMap.keySet()) {
				formulaExpr = formulaExpr.replace(key,
						formulaValuesMap.get(key));
			}
		}
		if (formulaPrevValues != null && formulaPrevValues.size() > 0) {
			for (String key : formulaPrevValues.keySet()) {
				formulaExpr = formulaExpr.replace(key,
						formulaPrevValues.get(key));
			}
		}

		ExpressionParser parser = new SpelExpressionParser();
		double formulaValue = parser.parseExpression(formulaExpr).getValue(
				Double.class);
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValueExtn, message :: Exit");
		return formulaValue;
	}

	public List<ExecuteFormulaModel> executeFormulaExtn(String inputJSON) {

		logger.info("inside class: FormulaEngineService.java,  method: executeFormulaExtn, message :: Starts");
		// This is the JSON input which has the Formula Group
		String formulaJSON = "";

		logger.info("inside class: FormulaEngineService.java,  method: executeFormulaExtn, message :: Input Formula Group:  "
				+ formulaJSON);
		Gson gson = new Gson();
		// Parsing the JSON input to get the group of Formulas
		FormulaGroupObject formulaGroupsArray[] = gson.fromJson(inputJSON,
				FormulaGroupObject[].class);
		List<ExecuteFormulaModel> formulaGroupResultsList = new ArrayList<>();

		// Looping through the Formula Groups
		for (FormulaGroupObject formulaGroups : formulaGroupsArray) {
			ExecuteFormulaModel formulaGroupResults = new ExecuteFormulaModel();
			List<FormulaExecuteObject> formulaResultList = new ArrayList<>();

			logger.info("inside class: FormulaEngineService.java,  method: executeFormulaExtn, message :: Group Name: "
					+ formulaGroups.getFormulaGroupName());

			formulaGroupResults.setFormulaGroupName(formulaGroups
					.getFormulaGroupName());
			int order = 1;
			LinkedHashMap<String, String> formulaPrevValues = new LinkedHashMap<>();
			// Looping through each Formula
			for (FormulaObject formulaName : formulaGroups.getFormula()) {
				// Setting the Formula Result
				FormulaExecuteObject formulaResult = new FormulaExecuteObject();
				logger.info("inside class: FormulaEngineService.java,  method: executeFormulaExtn, message :: Description "
						+ formulaName.getFormulaDescription());
				formulaResult.setFormulaDescription(formulaName
						.getFormulaDescription());
				formulaResult.setFormulaValue(getFormulaValueExtn(formulaName,
						formulaPrevValues) + "");
				formulaResult.setFormulaOrder(order++);

				formulaResultList.add(formulaResult);
				formulaPrevValues.put(formulaResult.getFormulaDescription(),
						formulaResult.getFormulaValue());

			}

			for (String key : formulaPrevValues.keySet()) {
				logger.info("inside class: FormulaEngineService.java,  method: executeFormulaExtn, message :: Key---:  "
						+ key + ", Value---:  " + formulaPrevValues.get(key));
			}

			formulaGroupResults.setFormulaResultList(formulaResultList);
			// Adding the Formula Group Results to the List
			formulaGroupResultsList.add(formulaGroupResults);
		}

		logger.info("Final JSON");
		logger.info(gson.toJson(formulaGroupResultsList));
		logger.info("inside class: FormulaEngineService.java,  method: executeFormulaExtn, message :: Exit");
		return formulaGroupResultsList;

	}

	int i = 0;

	public int scanTokenExtn(String inputString, String operatorChars) {
		while (i < inputString.length()) {
			if (i + 1 < inputString.length()) {
				String pair = inputString.substring(i, i + 2);
				logger.info(pair);
				int ttype = 0;
				if (pair.equals("<="))
					ttype = Token.TT_LE;
				else if (pair.equals(">="))
					ttype = Token.TT_GE;
				else if (pair.equals("<>"))
					ttype = Token.TT_NE;
				if (0 != ttype) {

					return ttype;
				}
			}
			i++;
		}
		return i;
	}

	public void formulToken() {

		String expression = "if(InternalRepair>= ExternalRepair),Internal*05,100)";
		String operatorChars = "*/+-^<>=,()";
		try {
			Scanner tokens = new Scanner(expression, operatorChars);

			Token token = tokens.nextToken();
			while (!tokens.atEnd()) {
				logger.info("Type " + token.ttype);
				if (token.ttype == token.TT_WORD) {
					logger.info(token.sval);
					if (!token.sval.equals("if"))
						logger.info("Found :  " + token.sval);

				}

				if (token.ttype == token.TT_LE)
					logger.info("TT_LE");
				if (token.ttype == token.TT_NE)
					logger.info("TT_NE");
				if (token.ttype == token.TT_GE)
					logger.info("TT_GE");

				token = tokens.nextToken();
			}
		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: formulToken, Exception message :: ",
					e);
		}
	}

	private void replaceLexicalTokens(String expression,
			HashMap<String, Double> map1, HashMap<String, String> agrValueMap,
			HashMap<String, Date> dateValueMap) {

		final String operatorChars = "*/+-^<>=,()";
		List<String> unaryOperators = Arrays.asList("abs", "acos", "asin",
				"atan", "ceil", "cos", "exp", "floor", "log", "round", "sin",
				"sqrt", "tan", "rint", "neg");

		List<String> binaryOperators = Arrays.asList("atan2", "max", "min",
				"and", "or");
		List<String> agrrgationOperators = Arrays.asList("maximum", "minimum",
				"average", "count", "sum");
		Scanner tokens = new Scanner(expression, operatorChars);
		Token token = tokens.nextToken();
		while (!tokens.atEnd()) {
			if (token.ttype == token.TT_WORD) {

				if (!token.sval.equals("if")) {
					if (!unaryOperators.contains(token.sval)
							&& !binaryOperators.contains(token.sval)
							&& !agrrgationOperators.contains(token.sval)) {
						if (agrValueMap.containsKey(token.sval)) {
							Variable.make(token.sval).setValue(
									variableValueMapperStr(token.sval,
											agrValueMap));
						} else if (dateValueMap.containsKey(token.sval)) {
							Variable.make(token.sval).setValue(
									variableValueMapperDate(token.sval,
											dateValueMap));
						} else if (map1.containsKey(token.sval)) {
							Variable.make(token.sval).setValue(
									variableValueMapper(token.sval, map1));
						}
					}
				}
			}
			token = tokens.nextToken();
		}
	}

	private double variableValueMapper(String token,
			HashMap<String, Double> map1) {
		// ICAM:AvgOfLLP_Cost:Cost:QT
		double returnValue = 0;

		java.util.Set<String> set1 = map1.keySet();

		String[] args = token.split(":");
		switch (args[0]) {

		default:
			returnValue = 10;
			break;
		}
		return map1.get(token);

	}

	private String variableValueMapperStr(String token,
			HashMap<String, String> formulaValuesMap) {
		String returnValue = "";

		java.util.Set<String> set1 = formulaValuesMap.keySet();

		String[] args = token.split(":");
		switch (args[0]) {

		default:
			returnValue = "";
			break;
		}
		return formulaValuesMap.get(token);
	}

	private Date variableValueMapperDate(String token,
			HashMap<String, Date> dateValuesMap) {
		Date returnValue = null;

		String[] args = token.split(":");
		switch (args[0]) {

		default:
			returnValue = null;
			break;
		}
		return dateValuesMap.get(token);
	}

	public HashMap<String, List<HashMap<String, Object>>> executeFormulaMthd(
			String inputJSON, EntityManagerFactory factory,
			EntityManager entitymanager) {

		logger.info("inside class: FormulaEngineService.java,  method: executeFormulaMthd, message :: Starts");
		// This is the JSON input which has the Formula Group
		HashMap<String, List<HashMap<String, Object>>> resultMap = new HashMap<String, List<HashMap<String, Object>>>();
		List<HashMap<String, Object>> outputList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		int version = 0;
		try {

			Gson gson = new Gson();
			// Parsing the JSON input to get the group of Formulas
			FormulaGroupObject formulaGroupsArray[] = gson.fromJson(inputJSON,
					FormulaGroupObject[].class);
			outputList = new ArrayList<HashMap<String, Object>>();
			for (FormulaGroupObject formulaGroups : formulaGroupsArray) {

				ExecuteFormulaModel formulaGroupResults = new ExecuteFormulaModel();

				formulaGroupResults.setFormulaGroupName(formulaGroups
						.getFormulaGroupName());
				LinkedHashMap<String, String> formulaPrevValues = new LinkedHashMap<>();
				for (FormulaObject formula : formulaGroups.getFormula()) {
					outputMap = new HashMap<String, Object>();

					FormulaExecuteObject formulaResult = new FormulaExecuteObject();
					formulaResult.setFormulaDescription(formula
							.getFormulaDescription());
					double answer = getFormulaValueExpr(formula,
							formulaGroups.getFormulaGroupName(),
							formulaPrevValues, factory, entitymanager,
							formulaGroups.getVersion());
					formulaResult.setFormulaValue(answer + "");
					formulaPrevValues.put(
							formulaResult.getFormulaDescription(),
							formulaResult.getFormulaValue());
					outputMap.put("formulaName",
							formula.getFormulaDescription());
					outputMap.put("value", answer);
					outputList.add(outputMap);
					resultMap.put(formulaGroups.getFormulaGroupName(),
							outputList);
				}
			}
			logger.info("inside class: FormulaEngineService.java,  method: executeFormulaMthd, message :: Exit");
			return resultMap;
		} catch (Exception e) {
			outputMap.put("formulaName", "");
			outputMap.put("value", 0.0);
			outputList.add(outputMap);
			resultMap.put("formulaGroup", outputList);
			logger.error(
					"inside class: FormulaEngineService.java,  method: executeFormulaMthd, message :: ",
					e);
			return resultMap;
		}

	}

	private double getFormulaValueExpr(FormulaObject objFormula,
			String formulaGroup,
			LinkedHashMap<String, String> formulaPrevValues,
			EntityManagerFactory factory, EntityManager entitymanager,
			int version) {

		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValueExpr, message :: Starts");

		Expr expr = null;

		Formula formualDetail = getFormulaString(
				objFormula.getFormulaDescription(), formulaGroup, version,
				factory, entitymanager);

		String formulaExpr = formualDetail.getFormulaString();
		HashMap<String, String> formulaValuesMap = objFormula
				.getFormulaValues();

		HashMap<String, Double> valueMap = new HashMap<String, Double>();
		HashMap<String, String> agrValueMap = new HashMap<String, String>();
		HashMap<String, Date> dateValueMap = new HashMap<String, Date>();

		if (formulaValuesMap != null && formulaValuesMap.size() > 0) {
			Set baseSet = formulaValuesMap.keySet();
			Iterator irr = baseSet.iterator();

			while (irr.hasNext()) {

				String key = (String) irr.next();
				String string = formulaValuesMap.get(key);

				if (string.contains(",")) {
					agrValueMap.put(key.replaceAll("\\s+", ""), string);
				} else if (string.contains("/")) {
					try {
						String dateString = string;
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
						Date date = df.parse(dateString);
						dateValueMap.put(key.replaceAll("\\s+", ""), date);
					} catch (ParseException e) {
						System.out
								.println("Exception occured while parsing Dates for "
										+ string);
					}
				} else {
					String value = formulaValuesMap.get(key);
					valueMap.put(key.replaceAll("\\s+", ""), new Double(value));
				}
			}
		}
		if (formulaPrevValues != null && formulaPrevValues.size() > 0) {
			Set baseSet1 = formulaPrevValues.keySet();
			Iterator irr1 = baseSet1.iterator();

			while (irr1.hasNext()) {

				String key1 = (String) irr1.next();
				String value1 = formulaPrevValues.get(key1);

				valueMap.put(key1.replaceAll("\\s+", ""), new Double(value1));

			}
		}

		try {
			expr = Parser.parse(formulaExpr.replaceAll("\\s+", ""));
		} catch (SyntaxException e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: getFormulaValueExpr, message :: ",
					e);
		}

		replaceLexicalTokens(formulaExpr.replaceAll("\\s+", ""), valueMap,
				agrValueMap, dateValueMap);
		double answer = (double) expr.value();
		logger.info("inside class: FormulaEngineService.java,  method: getFormulaValueExpr, message :: Exit");
		return answer;

	}

	public List<HashMap<String, Object>> addFormulaVersion(
			FormulaRequestModel model) {
		logger.info("inside class: FormulaEngineService.java,  method: addFormulaVersion, message :: Starts");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Application");
		EntityManager entitymanager = factory.createEntityManager();
		entitymanager.getTransaction().begin();
		List<HashMap<String, Object>> outputList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> outMap = new HashMap<String, Object>();

		int groupId = 0;
		int formulaId = 0;
		Formula formulaDetail = new Formula();
		ArrayList<Formula> listFormula = new ArrayList<Formula>();
		Groups group = new Groups();
		try {

			HashMap<String, FormulaGroupObject> requestMap = model
					.getFormulaGroupInfo();

			Set set1 = requestMap.keySet();
			Iterator itr1 = set1.iterator();

			while (itr1.hasNext()) {
				String key = itr1.next().toString();

				FormulaGroupObject object1 = (FormulaGroupObject) requestMap
						.get(key);
				List listObject = object1.getFormula();

				String formulaGroupName = object1.getFormulaGroupName();
				String startDate = object1.getStartDate();
				List entityList = entitymanager
						.createQuery(
								"select g , d from Groups g, Formula d"
										+ " WHERE g = d.group and g.formulaGroupName= :formulaGroupName")
						.setParameter("formulaGroupName", formulaGroupName)
						.getResultList();

				Object[] fetchListArr = entityList.toArray();

				for (int i = 0; i < fetchListArr.length; i++) {

					Object object = fetchListArr[i];
					Object[] objectArray = (Object[]) object;

					Groups objGroups = (Groups) objectArray[0];
					Formula objFormula = (Formula) objectArray[1];
					groupId = objGroups.getFormulaGroupId();
					formulaDetail = new Formula();
					formulaDetail.setFormulaName(objFormula.getFormulaName());
					formulaDetail.setFormulaString(objFormula
							.getFormulaString());
					group.setFormulaGroupName(objGroups.getFormulaGroupName());
					group.setVersion(objGroups.getVersion() + 1);
					formulaDetail.setGroups(group);
					entitymanager.persist(formulaDetail);
				}
				entitymanager.getTransaction().commit();
				entitymanager.close();
				factory.close();

				outMap.put("FormulaGroup", formulaGroupName);
				outMap.put("VersionStatus", true);
				outputList.add(outMap);
			}
			logger.info("inside class: FormulaEngineService.java,  method: addFormulaVersion, message :: Exit");
			return outputList;
		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: addFormulaVersion, Exception message :: ",
					e);
			return null;
		}

	}

	private Formula getFormulaString(String formulaName, String formulaGroup,
			int version, EntityManagerFactory factory,
			EntityManager entitymanager) {

		Formula formulaDetail = new Formula();
		try {

			List l = entitymanager
					.createQuery(
							"select d from Groups g, Formula d"
									+ " WHERE g = d.group and g.formulaGroupName= :formulaGroupName and g.version =:version "
									+ " and  d.formulaName= :formulaName ")
					.setParameter("formulaGroupName", formulaGroup)
					.setParameter("formulaName", formulaName)
					.setParameter("version", version).getResultList();

			for (Object object : l) {
				formulaDetail = (Formula) object;

			}
			return formulaDetail;

		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: getFormulaString, Exception message :: ",
					e);
			return null;
		}

	}

	public RulesEntity getRuleGroupObject(int ruleId,
			EntityManager entityManager) {
		RulesEntity objRulesGroupEntity = new RulesEntity();

		try {

			/*List l = entityManager
					.createQuery(
							"select r  from  RulesGroupEntity rg , RulesEntity  r where rg=r.rulesGroupEntity and  rg.rulesGroupId= :rulesGroupIdVar  ")
					.setParameter("rulesGroupIdVar",
							Integer.parseInt(ruleGroupId)).getResultList();*/
			
			List l = entityManager
					.createQuery(
							"select r  from  RulesEntity  r where  r.ruleId= :ruleIdVar  ")
					.setParameter("ruleIdVar",ruleId).getResultList();

			for (Object object : l) {
				objRulesGroupEntity = (RulesEntity) object;

			}
			return objRulesGroupEntity;

		} catch (Exception e) {
			logger.error(
					"inside class: FormulaEngineService.java,  method: getFormulaString, Exception message :: ",
					e);
			return null;
		}

	}

	public RuleServiceEntity getRuleServiceDetails(int ruleId,
			EntityManager entityManager) {
		RuleServiceEntity objRuleServiceEntity = new RuleServiceEntity();
		try {
			List l = entityManager
					.createQuery(
							"select s  from  RuleServiceEntity s, RulesEntity r  where s.ruleserviceId=r.externalServiceId and  r.ruleId= :ruleIdVar ")
					.setParameter("ruleIdVar",
							ruleId).getResultList();
			for (Object object : l) {
				objRuleServiceEntity = (RuleServiceEntity) object;

			}
			return objRuleServiceEntity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
