/**
 * Load controllers, directives, filters, services before bootstrapping the application.
 * NOTE: These are named references that are defined inside of the config.js RequireJS configuration file.
 */
define([
    'jquery',
    'angular',
    'main',
    'routes',
    'interceptors',
    'px-datasource',
    'ng-bind-polymer',
    'angular-sanitize',
    'jquery-ui'
], function($, angular) {
    'use strict';

    /**
     * Application definition
     * This is where the AngularJS application is defined and all application dependencies declared.
     * @type {module}
     */
    var predixApp = angular.module('predixApp', [
        'app.routes',
        'app.interceptors',
        'sample.module',
        'predix.datasource',
        'px.ngBindPolymer',
        'ngSanitize',
        'queryBuilder',
        'dateBuilder'
    ]);

    /**
     * Main Controller
     * This controller is the top most level controller that allows for all
     * child controllers to access properties defined on the $rootScope.
     */
    predixApp.controller('MainCtrl', ['$scope', '$rootScope', 'PredixUserService', '$sanitize', function($scope, $rootScope, predixUserService, $sanitize) {

        //Global application object
        window.App = $rootScope.App = {
            version: '1.0',
            name: 'Predix Seed',
            session: {},
            tabs: [
                { icon: 'fa-tachometer', state: 'dashboards', label: 'Dashboards' }, {
                    icon: 'fa-file-o',
                    state: 'blankpage',
                    label: 'Formula Engine',
                    subitems: [
                        { state: 'blanksubpage', label: 'Blank Sub Page' }
                    ]
                }
            ]
        };

        $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, error) {
            if (angular.isObject(error) && angular.isString(error.code)) {
                switch (error.code) {
                    case 'UNAUTHORIZED':
                        //redirect
                        predixUserService.login(toState);
                        break;
                    default:
                        //go to other error state
                }
            } else {
                // unexpected error
            }
        });
    }]);

    predixApp.controller('QueryBuilderCtrl', ['$scope', function($scope) {
        //Jatin
        //For edit functionality we can just populate the cond variable as shown below 
        //and then all the UI elements will be initiated accordingly
        //var cond = '{"condition": "=", "field": "formula1", "data": "2"}, {"group": {"operator": "AND", "rules": [{"condition": "<>","field": "formula2","data": "3"}]}}';
        
        var cond = '';
        var data = '{"group": {"operator": "AND","rules": [' + cond + ']}}';

        function htmlEntities(str) {
            return String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;');
        }

        function computed(group) {
            if (!group) return "";
            for (var str = "(", i = 0; i < group.rules.length; i++) {
                i > 0 && (str += " <strong>" + group.operator + "</strong> ");
                str += group.rules[i].group ?
                    computed(group.rules[i].group) :
                    group.rules[i].field + " " + htmlEntities(group.rules[i].condition) + " " + group.rules[i].data;
            }

            return str + ')';
        }

        $scope.json = null;
        $scope.queryLogic = null;

        $scope.finalStr = null;

        $scope.filter = JSON.parse(data);

        $scope.final = {action1:null,
                        action2:null}

        $scope.$watchCollection(
            'final', function(newValue, oldValue){
                if(newValue !== oldValue){
                    $scope.output = "( " + $scope.queryLogic + " , " + newValue.action1 + " , " + newValue.action2 + " )"
                    $scope.finalStr = $scope.output.replace(new RegExp('&lt;', 'g'), '<')
                                                    .replace(new RegExp('&gt;', 'g'), '>')
                                                    .replace(new RegExp('<strong>', 'g'), '')
                                                    .replace(new RegExp('</strong>', 'g'), '')
                    console.log('$scope.finalStr:' + $scope.finalStr)
                    console.log('$scope.validTo:' + $scope.validTo)
                }
            }
        );

        $scope.$watch('filter', function(newValue) {
            $scope.json = JSON.stringify(newValue, null, 2);
            $scope.queryLogic = computed(newValue.group)
            $scope.output = "( " + $scope.queryLogic + " , " + $scope.final.action1 + " , " + $scope.final.action2 + " )"
            
            $scope.finalStr = $scope.output.replace(new RegExp('&lt;', 'g'), '<')
                        .replace(new RegExp('&gt;', 'g'), '>')
                        .replace(new RegExp('<strong>', 'g'), '')
                        .replace(new RegExp('</strong>', 'g'), '')
                console.log('$scope.finalStr:' + $scope.finalStr)
            }, true);


            //Jatin
            //Service can populate these
            $scope.actions1 = [
                                { name: 'ACTION1' },
                                { name: 'ACTION2' },
                                { name: 'ACTION3' }
                            ];

            $scope.actions2 = [
                                { name: 'ACTION1' },
                                { name: 'ACTION2' },
                                { name: 'ACTION3' }
                            ];
            $scope.changedValue1 = function(action1){
                console.log('action1' + String(action1.name))
                $scope.final.action1 = String(action1.name)
            }

            $scope.changedValue2 = function(action2){
                console.log('action2' + String(action2.name))
                $scope.final.action2 = String(action2.name)
            }

    }]);


    var queryBuilder = angular.module('queryBuilder', []);
    queryBuilder.directive('queryBuilder', ['$compile', function($compile) {
        return {
            restrict: 'E',
            scope: {
                group: '='
            },
            templateUrl: '/queryBuilderDirective.html',
            compile: function(element, attrs) {
                var content, directive;
                content = element.contents().remove();
                return function(scope, element, attrs) {
                    scope.operators = [
                        { name: 'AND' },
                        { name: 'OR' }
                    ];

                    //Jatin
                    //Service can populate these
                    scope.fields = [
                        { name: 'formula1' },
                        { name: 'formula2' },
                        { name: 'formula3' },
                        { name: 'formula4' },
                        { name: 'formula5' }
                    ];

                    scope.conditions = [
                        { name: '=' },
                        { name: '<>' },
                        { name: '<' },
                        { name: '<=' },
                        { name: '>' },
                        { name: '>=' }
                    ];

                    scope.addCondition = function() {
                        scope.group.rules.push({
                            condition: '=',
                            field: 'formula1',
                            data: ''
                        });
                    };

                    scope.removeCondition = function(index) {
                        scope.group.rules.splice(index, 1);
                    };

                    scope.addGroup = function() {
                        scope.group.rules.push({
                            group: {
                                operator: 'AND',
                                rules: []
                            }
                        });
                    };

                    scope.removeGroup = function() {
                        "group" in scope.$parent && scope.$parent.group.rules.splice(scope.$parent.$index, 1);
                    };

                    directive || (directive = $compile(content));

                    element.append(directive(scope, function($compile) {
                        return $compile;
                    }));
                }
            }
        }
    }]);

    angular.module('dateBuilder', []).directive('jqdatepicker', function() {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function(scope, element, attrs, ngModelCtrl) {
                element.datepicker({
                    dateFormat: 'DD, d  MM, yy',
                    onSelect: function(date) {
                        scope.date = date;
                        scope.$apply();
                    }
                });
            }
        };
    });


    //Set on window for debugging
    window.predixApp = predixApp;

    //Return the application  object
    return predixApp;
});
