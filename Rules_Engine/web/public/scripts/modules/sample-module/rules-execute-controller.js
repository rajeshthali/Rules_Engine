define(['angular', './sample-module'], function (angular, controllers) {
    'use strict';
    return controllers.controller('RulesExecuteController', ['$scope','$state','$stateParams','RulesService', function($scope,$state, $stateParams, RulesService) {
    	 initController();
         
         function initController() {
        	 $scope.isLoading = true;
        	 $scope.isCalculateExecute=false;
        	 $scope.isServiceExecute=false;
        	 $scope.isDbFunction=false;
        	 $scope.isDbQuery=false;
        	 $scope.successExecution = false;
        	 RulesService.getRuleExecuteDetails($stateParams.rulesId,$stateParams.ruleOperator ,function(res){
 				$scope.rulesDetails= angular.copy(res);
 				//create a map for conditionParameter and formulaParameter
        		 $scope.conditionParameterMap = [] // or var map = {};
        		 $scope.formulaParameterMap =[]; // or var map = {};
 				angular.forEach(res.conditionParameter, function(value, key){
 					var temp = {'key': value, 'value':''};
 					$scope.conditionParameterMap.push(temp);
 				});
 				
 				angular.forEach(res.formulaParameter, function(value, key){
 					var temp1 = {'key': value, 'value':''};
 					$scope.formulaParameterMap.push(temp1);
				 });
 				$scope.isLoading = false;
 				if($stateParams.ruleOperator === 'SERVICES') {
 					$scope.isServiceExecute = true;
 				}else if($stateParams.ruleOperator === 'CALCULATE') {
 					$scope.isCalculateExecute = true;
 				}else if($stateParams.ruleOperator === 'EXECUTE' && res.qName !== null  ) {
 					$scope.isDbQuery = true;
 				}else if($stateParams.ruleOperator === 'EXECUTE' && res.fName !== null  ) {
 					$scope.isDbFunction = true;
 				}
 			  });
 			};
 			
 			$scope.cancel = function() {
 				$state.go('dashboards');
 			};
 			
 			$scope.ruleExecute = function() {
 				//$scope.conditionParameterMap;
 				//$scope.formulaParameterMap;
 				$scope.isLoading = true;
 				var ruleValues = {};
 				var serviceParam ={};
 				angular.forEach($scope.conditionParameterMap, function(value, key){
 					ruleValues[value.key] = value.value;
 				});
 				if($stateParams.ruleOperator === 'SERVICES') {
 					angular.forEach($scope.formulaParameterMap, function(value, key){
 						serviceParam[value.key] = value.value;
 	 				});
 				}else{
 					angular.forEach($scope.formulaParameterMap, function(value, key){
 	 					ruleValues[value.key] = value.value;
 	 				});
 				}
 				
 				RulesService.executeRule($scope.rulesDetails.ruleId,$scope.rulesDetails.ruleName,$scope.rulesDetails.ruleFormulaString,$scope.rulesDetails.conditionFormulaString,$stateParams.ruleOperator,
 						$scope.rulesDetails.serviceURL,$scope.rulesDetails.serviceType,$scope.rulesDetails.dataId,ruleValues,serviceParam ,function(res){
 				$scope.rulesExecutionDetails= angular.copy(res);
 				$scope.successExecution = true;
 				$scope.isLoading = false;
 			    });
 			
 			
	 			$scope.forwardToEdit= function() {
	 				$state.go('editRule', {rulesId: $stateParams.rulesId});
	 			};
 			};
    }]);
});
