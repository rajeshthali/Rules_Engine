define([ 'angular', './services-module'], function(angular, module) {
	'use strict';
	module.factory('Config', ['$http', function($http) {
		return {
			baseUrl : 'https://ehs-rmd-datasource-rajesh.run.aws-usw02-pr.ice.predix.io',
		};
	} ]);
});