define(['angular', './sample-module'], function(angular, sampleModule) {
    'use strict';

    sampleModule.factory('RulesDisplayService', ['$http', function($http) {
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
                }
         };
    }]);
});
