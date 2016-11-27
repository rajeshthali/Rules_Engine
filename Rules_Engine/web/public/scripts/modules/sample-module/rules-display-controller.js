define(['angular', './sample-module'], function (angular, controllers) {
    'use strict';
    return controllers.controller('RulesDisplayController', ['$scope','RulesDisplayService', function($scope, RulesDisplayService) {
    	
    	 
        //$scope.dummyItems = _.range(1, 151); // dummy array of items to be paged
    	$scope.pager = {};
    	//$scope.setPage = setPage;
     
        initController();
        
        $scope.openRuleExucutionPopup = function(ruleId) {
        	console.log("test--------"+ruleId);
            var modalInstance = $modal.open({
                templateUrl:'rule-execution.html',
                controller: RuleExuecutionCtrl
               /* resolve: {
                    items: function () {
                        return $scope.items;
                    }
                }*/
            });

            modalInstance.result.then(function (selectedItem) {
                $scope.selected = selectedItem;
            }, function () {
                //cancel
            });
        };

        function initController() {
            // initialize to page 1
            //setPage1(1);
        	$scope.isLoading = true;
    		RulesDisplayService.getRulesList(function(res){
				$scope.isLoading = false;
				$scope.rulesList= angular.copy(res[0]);
				//console.log($scope.rulesList.ruleGroupObject.ruleGroupDesc);
				 $scope.pager = getPager($scope.rulesList.ruleEngineList.length, 1,5);
				 $scope.rulesList1 = $scope.rulesList.ruleEngineList.slice($scope.pager.startIndex, $scope.pager.endIndex + 1);
				 console.log("test1");
			  });
           
            // get current page of items
            //$scope.items = $scope.dummyItems.slice($scope.pager.startIndex, $scope.pager.endIndex + 1);
           
        };
        $scope.setPage = function(page) {
        	setPage1(page);
        };
        var setPage1 = function(page) {
            if (page < 1 || page > $scope.pager.totalPages) {
                return;
            }
     
            // get pager object from service
            
            loadData();
            $scope.pager = getPager($scope.rulesList.ruleEngineList.length, page,5);
            // get current page of items
            //$scope.items = $scope.dummyItems.slice($scope.pager.startIndex, $scope.pager.endIndex + 1);
            $scope.rulesList1 = $scope.rulesList.ruleEngineList.slice($scope.pager.startIndex, $scope.pager.endIndex + 1);
            console.log("test2");
        };
    	
    	var loadData = function() {
    		$scope.isLoading = true;
    		RulesDisplayService.getRulesList(function(res){
				$scope.isLoading = false;
				$scope.rulesList= angular.copy(res[0]);
				console.log($scope.rulesList.ruleGroupObject.ruleGroupDesc);
			  });
			
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
	        	
	 
	        // return object with all pager properties required by the view
	        
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
		//loadData();
		
		/*var getRulesList = function() {
			$scope.isLoading = true;
			RulesDisplayService.getRulesList1(function(res){
				$scope.isLoading = false;
				$scope.rulesList= angular.copy(res);
			  });
			};*/
    }]);
});
