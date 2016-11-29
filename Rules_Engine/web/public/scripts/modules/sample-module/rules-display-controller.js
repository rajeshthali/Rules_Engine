define(['angular', './sample-module'], function (angular, controllers) {
    'use strict';
    return controllers.controller('RulesDisplayController', ['$scope','RulesService', function($scope, RulesService) {
    	
    	 
        $scope.pager = {};
    	
        initController();
        
        $scope.openRuleExucutionPopup = function(ruleId) {
          var modalInstance = $modal.open({
                templateUrl:'rule-execution.html',
                controller: RuleExuecutionCtrl
               });

            modalInstance.result.then(function (selectedItem) {
                $scope.selected = selectedItem;
            }, function () {
                //cancel
            });
        };

        function initController() {
         	$scope.isLoading = true;
        	RulesService.getRulesList(function(res){
				$scope.isLoading = false;
				$scope.rulesList = [];
				console.log(res.length);
				for (var groupCount = 0; groupCount < res.length; groupCount++) { 
					for(var ruleCount=0;ruleCount<res[groupCount].ruleEngineList.length;ruleCount++){
						var temp ={'ruleGroup':res[groupCount].ruleGroupObject, 'ruleDetails':res[groupCount].ruleEngineList[ruleCount]};
						$scope.rulesList.push(temp);  
						}
				}
				 $scope.pager = getPager($scope.rulesList.length, 1,5);
				 $scope.rulesList1 = $scope.rulesList.slice($scope.pager.startIndex, $scope.pager.endIndex + 1);
			  });
           
          };
        $scope.setPage = function(page) {
        	setPage1(page);
        };
        var setPage1 = function(page) {
            if (page < 1 || page > $scope.pager.totalPages) {
                return;
            }
     
             $scope.pager = getPager($scope.rulesList.length, page,5);
             $scope.rulesList1 = $scope.rulesList.slice($scope.pager.startIndex, $scope.pager.endIndex + 1);
        };
    	
    	 var getPager = function(totalItems, currentPage, pageSize) {
	        // default to first page
	        currentPage = currentPage || 1;
	 
	        // default page size is 10
	        pageSize = pageSize || 10;
	 
	        // calculate total pages
	        var totalPages = Math.ceil(totalItems / pageSize);
	 
	        var startPage, endPage;
	        if (totalPages <= 10) {
	            // less than 10 total pages so show all
	            startPage = 1;
	            endPage = totalPages;
	        } else {
	            // more than 10 total pages so calculate start and end pages
	            if (currentPage <= 6) {
	                startPage = 1;
	                endPage = 10;
	            } else if (currentPage + 4 >= totalPages) {
	                startPage = totalPages - 9;
	                endPage = totalPages;
	            } else {
	                startPage = currentPage - 5;
	                endPage = currentPage + 4;
	            }
	        }
	 
	        // calculate start and end item indexes
	        var startIndex = (currentPage - 1) * pageSize;
	        var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
	 
	        // create an array of pages to ng-repeat in the pager control
	        //var pages = _.range(startPage, endPage + 1);
	        var pages = [];
	        for(var i=startPage;i<endPage + 1;i++) {
	        	pages.push(i);
	        }
	        	
	        return {
	            totalItems: totalItems,
	            currentPage: currentPage,
	            pageSize: pageSize,
	            totalPages: totalPages,
	            startPage: startPage,
	            endPage: endPage,
	            startIndex: startIndex,
	            endIndex: endIndex,
	            pages: pages
	        };
	       // console.log("test");
		 };
	    }]);
});
