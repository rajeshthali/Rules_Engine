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
                { icon: 'fa-tachometer', state: 'dashboards', label: 'Dashboards' }, 
                {
                    icon: 'fa-file-o',
                    state: 'addNewRuleGroup',
                    label: 'Add New Rule Group'
                 }, {
                    icon: 'fa-file-o',
                    state: 'viewRules',
                    label: 'View Executed Rules'
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

    predixApp.controller('QueryBuilderCtrl', ['$scope', '$http', '$stateParams', function($scope, $http, $stateParams) {
        var initialCond = '';
        
        var param1 = $stateParams.rulesId;
        console.log('param0:' + param1);

        if(param1 != undefined && param1 != null && param1 != ''){
            console.log('param1:' + param1);
            $http.get("https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/ruleDetailsByRuleId/" + param1).success(function(data, status) {
                $scope.existingRuleDetails = data
                console.log('existingRuleDetails1:' + cleanIt(JSON.stringify($scope.existingRuleDetails.ruleEngineList[0].ruleJson)))
                initialCond = JSON.stringify($scope.existingRuleDetails.ruleEngineList[0].ruleJson)
                
                var myEscapedJSONString = initialCond.replace(/\\n/g, "\\n")
                                          .replace(/\\'/g, "\\'")
                                          .replace(/\\"/g, '\\"')
                                          .replace(/\\&/g, "\\&")
                                          .replace(/\\r/g, "\\r")
                                          .replace(/\\t/g, "\\t")
                                          .replace(/\\b/g, "\\b")
                                          .replace(/\\f/g, "\\f"); 


                console.log('initialCond1:' + JSON.parse(JSON.stringify(initialCond)))
                $scope.ruleJson = cleanIt(JSON.stringify($scope.existingRuleDetails))
                console.log('ruleJson:' + $scope.ruleJson)
                
            })
        } else {
            $scope.ruleGroupName = 'group name';
            $scope.ruleGroupDescription = 'group desc';
            $scope.ruleGroupVersion = 'group version';
            $scope.ruleGroupStartDate = '2016-11-01';

            $scope.ruleName = 'rule name';
            $scope.ruleDescription = 'rule desc';
            $scope.ruleVersion = 'rule version';
            $scope.ruleValidFrom = '2016-11-01';
            $scope.ruleValidTo = '2016-11-15';


            //$scope.selectedFormula1 = 'Formula2';
            //$scope.selectedFormula2 = 'Formula3';

            //$scope.selectedAction1 = 'Execute';

            //Jatin
            //This can be populated by the edit rule service
            //var initialCond = '{"group": {"operator": "OR","rules": [{"condition": "=", "field": "LabourCost", "data": "2"}, {"group": {"operator": "AND", "rules": [{"condition": "<>","field": "InternalRepair","data": "3"}]}}]}}';

            initialCond = '{"group": {"operator": "OR","rules": []}}';


            //This is where the json data is injected in UI elements
            $scope.jsonObjCond = JSON.parse(initialCond);
        }

        console.log('initialCond3:' + initialCond)
        console.log('existingRuleDetails2:' + JSON.stringify($scope.existingRuleDetails))
        

        function cleanIt(str){
            return String(str).replace(/\\n/g, '').replace(/\\/g, '')
        }

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

        function computed_2(group) {
            if (!group) return "";
            for (var str_2 = "(", i = 0; i < group.rules.length; i++) {
                i > 0 && (str_2 += " <strong>" + group.operator + "</strong> ");
                str_2 += group.rules[i].group ?
                    computed_2(group.rules[i].group) :
                    '$' + group.rules[i].field + "" + htmlEntities(group.rules[i].condition) + "" + group.rules[i].data;
            }

            return str_2 + ')';
        }

        function removeHtmlTags(condStr) {
            return condStr.replace(new RegExp('&lt;', 'g'), '<')
                .replace(new RegExp('&gt;', 'g'), '>')
                .replace(new RegExp('<strong>', 'g'), '')
                .replace(new RegExp('</strong>', 'g'), '');
        };

        function getLabel(selectedValue){
            var actions = []
            if ($scope.selectedAction1 == 'EXECUTE QUERY') {
                actions = $scope.dropdowns['QUERIES'];
            } else if ($scope.selectedAction1 == 'EXECUTE FUNCTION') {
                actions = $scope.dropdowns['FUNCTIONS'];
            } else if ($scope.selectedAction1 == 'SERVICES') {
                actions = $scope.dropdowns['SERVICES'];
            } else {
                actions = $scope.dropdowns['FORMULAS'];
            }

            for(var i = 0; i < actions.length; i++){
                if(selectedValue == actions[i].id){
                    return actions[i].name;
                }
            }
        }

        $http.get("https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/retrieveAllDropdowns").success(function(data, status) {
            $scope.dropdowns = data
            console.log('data:' + JSON.stringify(data))
        })

        $scope.jsonStrCond = null;
        $scope.htmlCond = null;
        $scope.noHtmlCond = null;

        $scope.htmlCond_2 = null;
        $scope.noHtmlCond_2 = null;
        $scope.output_2 = '';

        $scope.$watchGroup(['selectedAction1', 'selectedFormula1', 'selectedFormula2'], function(newValue, oldValue) {
            //if (newValue !== oldValue) {
            $scope.output = "( <strong>IF</strong> " + $scope.htmlCond + " <strong>THEN</strong> " + getLabel(newValue[1]) + " <strong>ELSE</strong> " + getLabel(newValue[2]) + " )"
            $scope.output_2 = "(if" + $scope.htmlCond_2 + " THEN " + '$' + getLabel(newValue[1]) + "," + '$' + getLabel(newValue[2]) + ")"

            $scope.noHtmlCond = removeHtmlTags($scope.htmlCond)
            $scope.noHtmlCond_2 = removeHtmlTags($scope.htmlCond_2)
                //}
            console.log(newValue[1] + " " + newValue[2]+ "    new values");
        });

        $scope.$watch('jsonObjCond', function(newValue) {
            $scope.jsonStrCond = JSON.stringify(newValue, null, 2);
            $scope.htmlCond = computed(newValue.group)
            $scope.htmlCond_2 = computed_2(newValue.group)

            $scope.output = "( <strong>IF</strong> " + $scope.htmlCond + " <strong>THEN</strong> " + getLabel($scope.selectedFormula1) + " <strong>ELSE</strong> " + getLabel($scope.selectedFormula2) + " )"
            $scope.output_2 = "(if" + $scope.htmlCond_2 + " THEN " + '$' + getLabel($scope.selectedFormula1) + "," + '$' + getLabel($scope.selectedFormula2) + ")"

            $scope.noHtmlCond = removeHtmlTags($scope.htmlCond)
            $scope.noHtmlCond_2 = removeHtmlTags($scope.htmlCond_2)
        }, true);


        $scope.ruleAction = [
            { label: 'SERVICES', value: 'SERVICES' },
            { label: 'EXECUTE QUERY', value: 'EXECUTE QUERY' },
            { label: 'EXECUTE FUNCTION', value: 'EXECUTE FUNCTION' },
            { label: 'CALCULATE', value: 'CALCULATE' }
        ];

        //$scope.selectedFormula1.value = $scope.dropdowns
        $scope.onActionChange1 = function(selectedAction1) {
            console.log('onActionChange1...')
            var arr = []
            var actions = [];
            $scope.ruleFormula = [];
            console.log($scope.dropdowns + "dropdown");
            console.log(selectedAction1 + "selectedAction1");

            if (selectedAction1 == 'EXECUTE QUERY') {
                actions = $scope.dropdowns['QUERIES'];
            } else if (selectedAction1 == 'EXECUTE FUNCTION') {
                actions = $scope.dropdowns['FUNCTIONS'];
            } else if (selectedAction1 == 'SERVICES') {
                actions = $scope.dropdowns['SERVICES'];
            } else {
                actions = $scope.dropdowns['FORMULAS'];
            }
            console.log(actions);
            if (actions === undefined || actions === null || actions === "") {
                actions = [];
            }


            for (var i = 0; i < actions.length; i++) {
                if (actions[i].name !== undefined && actions[i].name !== null && actions[i].name !== "") {
                    arr.push({ label: actions[i].name, value: actions[i].id })
                }
                console.log(arr);
                $scope.ruleFormula = arr
            }
            $scope.selectedFormula1 = $scope.selectedFormula2 = "";
        }
            //$scope.hello = { name: "helloworld1" };



        $scope.sampleData = {
            "ruleGroupObject": {
                "ruleFormulaGroupId": null,
                "ruleGroupName": null,
                "ruleGroupDesc": "SalesCost",
                "startDate": "23/11/2016",
                "version": "1.0.0"
            },
            "ruleEngineList": [{
                "ruleId": null,
                "ruleName": "Calc Sales Cost",
                "ruleDesc": "Calc Sales Cost",
                "ruleValidFrom": "23/11/2016",
                "ruleValidTo": "28/11/2016",
                "ruleVersion": "1.0.0",
                "ruleText": "IF (MaterialCost>100) THEN InternalRepair , LabourCost",
                "ruleOperator": "CALCULATE",
                "ruleConditionText": "if($MaterialCost>100)",
                "ruleActionText": "$InternalRepair,$LabourCost",
                "formulaGroupId": 0,
                "ruleFormulaGroup": null,
                "ruleFormulaGroupVersion": null,
                "ruleEmailList": null,
                "externalServiceUrlId": 0,
                "dbfunctinonId": 0,
                "dbqueryId": 0,
                "ruleJson": "SampleJSON",
                "otherParameters": null
            }]
        };

        $scope.submitRule = function() {
            console.log($scope.selectedFormula1)
                switch($scope.selectedAction1){
                    case "EXECUTE FUNCTION" :
                        $scope.selectedAction1 = "FUNCTIONS";
                        break;
                    case "EXECUTE QUERY" :
                        $scope.selectedAction1 = "QUERIES";
                        break;
                    default : 
                }
                    $scope.submitData = {

                        ruleGroupObject: {
                            "ruleFormulaGroupId": null,
                            "ruleGroupName": $scope.ruleGroupName,
                            "ruleGroupDesc": $scope.ruleGroupDescription,
                            "startDate": $scope.ruleGroupStartDate,
                            "version": $scope.ruleGroupVersion
                        },
                        ruleEngineList: [{
                            "ruleId": null,
                            "ruleName": $scope.ruleName,
                            "ruleDesc": $scope.ruleDescription,
                            "ruleValidFrom": $scope.ruleValidFrom, //http://jsfiddle.net/kevinj/TAeNF/2/
                            "ruleValidTo": $scope.ruleValidTo,
                            "ruleVersion": $scope.ruleVersion,
                            "ruleText": removeHtmlTags($scope.output_2),
                            "ruleOperator":  $scope.selectedAction1,
                            "ruleConditionText": "if"+ $scope.noHtmlCond_2,
                            "ruleActionText": ('$' + getLabel($scope.selectedFormula1) + "," + '$' + getLabel($scope.selectedFormula2)),
                            "formulaGroupId": 0,
                            "ruleFormulaGroup": null,
                            "ruleFormulaGroupVersion": null,
                            "ruleEmailList": null,
                            "externalServiceUrlId": ($scope.selectedAction1 === "SERVICE")? getLabel($scope.selectedFormula1) : 0,
                            "dbfunctinonId": ($scope.selectedAction1 === "FUNCTIONS")? getLabel($scope.selectedFormula1) : 0,
                            "dbqueryId": ($scope.selectedAction1 === "QUERIES")? getLabel($scope.selectedFormula1) : 0,
                            "ruleJson": cleanIt($scope.jsonStrCond),
                            "otherParameters": null

                        }]
                    };
                    console.log('$scope.ruleValidFrom:' + $scope.ruleValidFrom)

                    //Jatin
                    //Use service to save/update the rule
                    console.log("submitData:" + JSON.stringify($scope.submitData));

                   

            $http.post("https://predix-formula-rule-engine-2.run.aws-usw02-pr.ice.predix.io/rulesEngine/saveRule", JSON.stringify($scope.submitData)).success(function(data, status) {
                alert('data sent');
                console.log(data);
            });


        };


    }]);


    var queryBuilder = angular.module('queryBuilder', []);
    queryBuilder.directive('queryBuilder', ['$compile', function($compile) {
        return {
            restrict: 'E',
            scope: {
                group: '='
                    ,
                    dropdowns: '=dropdowns'
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

                    //console.log('----' + JSON.stringify(scope.dropdowns))
               /*     var actions = scope.dropdowns['FORMULAS']
                    var arr = []

                    for(var i = 0; i < actions.length; i++){
                        arr.push({label:actions[i].name, value:actions[i].name})
                    }
                    scope.fields = arr*/

                    scope.fields = [
                        { name: 'LabourCost' },
                        { name: 'InternalRepair' },
                        { name: 'MaterialCost' }
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

    angular.module('dateBuilder', []).directive('datepicker', function() {
        return {
            restrict: 'A',
            link: function(scope, el, attr) {
                el.datepicker({
                    dateFormat: 'yy-mm-dd'
                });
            }
        };
    });


    //Set on window for debugging
    window.predixApp = predixApp;

    //Return the application  object
    return predixApp;
});
