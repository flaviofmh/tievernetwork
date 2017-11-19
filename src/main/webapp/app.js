'use strict';

angular.module('tiever', [
  'ngRoute',
  'tiever.index',
  'tiever.register'
])
.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
	
	
  $routeProvider.otherwise({redirectTo: '/index'});
}]);