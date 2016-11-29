define(['angular', './sample-module'], function (angular, controllers) {
    'use strict';
    return controllers.controller('RulesExecuteController', ['$scope','$state','$stateParams','RulesService', function($scope,$state, $stateParams, RulesService) {
    	 initController();
         
         function initController() {
        	 $scope.isLoading = true;
        	 RulesService.getRuleExecuteDetails($stateParams.rulesId,$stateParams.ruleOperator ,function(res){
 				$scope.rulesDetails= angular.copy(res);
 				//create a map for conditionParameter and formulaParameter
        		 $scope.conditionParameterMap = [] // or var map = {};
        		 $scope.formulaParameterMap =[]; // or var map = {};
 				angular.forEach(res.conditionParameter, function(value, key){
 					var temp = {value:''};
 					$scope.conditionParameterMap.push(temp);
 				});
 				
 				angular.forEach(res.formulaParameter, function(value, key){
 					//$scope.formulaParameterMap[value] = '';
 					var temp = {value:''};
 					$scope.formulaParameterMap.push(temp);
				 });
 				//map[myKey1] = myObj1;
 				//map[myKey2] = myObj2;
 				$scope.isLoading = false;
 			  });
 			};
 			
 			$scope.forwardToEdit= function() {
 				$state.go('editRule', {rulesId: $stateParams.rulesId});
 			}
        
    }]);
});
