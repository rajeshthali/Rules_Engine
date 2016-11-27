define(['angular', './sample-module'], function (angular, controllers) {
    'use strict';
    return controllers.controller('RulesDetailsController', ['$scope','$state','$stateParams','RulesDisplayService', function($scope,$state, $stateParams, RulesDisplayService) {
    	 initController();
         
         function initController() {
        	 $scope.isLoading = true;
        	 RulesDisplayService.getRuleDetails($stateParams.rulesId,function(res){
 				$scope.rulesDetails= angular.copy(res.ruleEngineList[0]);
 				console.log('************'+$scope.rulesDetails.ruleName);
 				$scope.isLoading = false;
 			  });
 			};
 			
 			$scope.forwardToEdit= function() {
 				$state.go('editRule', {rulesId: $stateParams.rulesId});
 			}
        
    }]);
});
