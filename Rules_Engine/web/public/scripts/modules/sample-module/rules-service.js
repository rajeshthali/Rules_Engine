define(['angular', './sample-module'], function(angular, sampleModule) {
    'use strict';

    sampleModule.factory('RulesService', ['$http', function($http) {
    	 return {
    		 getRulesList : function(cb) {
   	          return $http({
   						method : 'GET',
   						url : 'https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/retrieveAllRules',
   					}).success(function(response) {
   						}).success(function(response) {
   						if (cb)
   							cb(response);
   					});
                  },
                  
             getRuleDetails : function(ruleId , cb) {
      	          return $http({
 						method : 'GET',
 						url : 'https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/ruleDetailsByRuleId/'+ruleId,
 					}).success(function(response) {
 						}).success(function(response) {
 						if (cb)
 							cb(response);
 					});
                },
                getRuleExecuteDetails : function(ruleId,ruleOperator, cb) {
                	    return $http({
   						method : 'GET',
   						url : 'https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/parameterList/'+ruleId+'/'+ruleOperator,
   						  
   					}).success(function(response) {
   						}).success(function(response) {
   						if (cb)
   							cb(response);
   					});
                  },
                  
                executeRule: function(ruleId,ruleName,ruleFormulaString,conditionFormulaString,ruleOperator,serviceURL,serviceType,ruleValues,serviceParam, cb) {
                	var _paramValues = {
                			'ruleId':ruleId, 'ruleName': ruleName,'ruleFormulaString':ruleFormulaString,'conditionFormulaString':conditionFormulaString,
                			'ruleOperator':ruleOperator,'serviceURL':serviceURL,'serviceType':serviceType,'ruleValues':ruleValues,'serviceParam':serviceParam};
                	
                	console.log(angular.toJson(_paramValues));
                	
            		return $http({
                	  method : 'POST',
						url : 'https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/executeRule',
						data: angular.toJson(_paramValues),
					    headers: {
                        'Content-Type': 'application/json'
                     }
                	 }).success(function(response) {
						}).success(function(response) {
						if (cb)
							cb(response);
					});
                }
         };
    }]);
});
