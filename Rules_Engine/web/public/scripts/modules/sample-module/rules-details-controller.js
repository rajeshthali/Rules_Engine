define(['angular', './sample-module'], function (angular, controllers) {
    'use strict';
    return controllers.controller('RulesDetailsController', ['$scope','$state','$stateParams','RulesService', function($scope,$state, $stateParams, RulesService) {
    	 initController();
         
         function initController() {
        	 $scope.isLoading = true;
        	 RulesService.getRuleDetails($stateParams.rulesId,function(res){
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
