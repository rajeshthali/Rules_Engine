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
                	var _paramValues = {
                			'ruleId':ruleId, 'ruleOperator': ruleOperator
            			};
        	          return $http({
   						method : 'POST',
   						url : 'https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/parameterList',
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
