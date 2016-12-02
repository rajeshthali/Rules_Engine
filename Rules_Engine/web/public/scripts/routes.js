/**
 * Router Config
 * This is the router definition that defines all application routes.
 */
define(['angular', 'angular-ui-router'], function(angular) {
    'use strict';
    return angular.module('app.routes', ['ui.router']).config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider) {

        //Turn on or off HTML5 mode which uses the # hash
        $locationProvider.html5Mode(true).hashPrefix('!');

        /**
         * Router paths
         * This is where the name of the route is matched to the controller and view template.
         */
        $stateProvider
            .state('secure', {
                template: '<ui-view/>',
                abstract: true,
                resolve: {
                    authenticated: ['$q', 'PredixUserService', function ($q, predixUserService) {
                        var deferred = $q.defer();
                        predixUserService.isAuthenticated().then(function(userInfo){
                            deferred.resolve(userInfo);
                        }, function(){
                            deferred.reject({code: 'UNAUTHORIZED'});
                        });
                        return deferred.promise;
                    }]
                }
            })
            .state('dashboards', {
                //parent: 'secure',
                url: '/dashboards',
                templateUrl: 'views/display-rules.html',
                controller : 'RulesDisplayController'
            })
            .state('addNewRuleGroup', {
                url: '/addNewRuleGroup',
                templateUrl: 'views/add-rule.html',
                controller : 'QueryBuilderCtrl'
            })
            .state('viewRules', {
                url: '/viewRules',
                templateUrl: 'views/blank-sub-page.html'
            })
            .state('executeRule', {
                url: '/executeRule/{rulesId}/{ruleOperator}',
                views: {
                     '': {
                     	templateUrl: 'views/rule-execution.html',
                     	controller : 'RulesExecuteController'
                     }
                 }
               })
             .state('editRule', {
                url: '/editRule/{rulesId}',
                 views: {
                    '': {
                    	templateUrl: 'views/add-rule.html',
                        controller: 'RulesDetailsController'
                    }
                }
              })
            .state('rulesDetails', {
                url: '/rulesDetails/{rulesId}',
                views: {
                    '': {
                    	templateUrl: 'views/rules-details.html',
                    	controller : 'RulesDetailsController'
                    }
                }
               });
            


        $urlRouterProvider.otherwise(function ($injector) {
            var $state = $injector.get('$state');
            document.querySelector('px-app-nav').markSelected('/dashboards');
            $state.go('dashboards');
        });

    }]);
});
