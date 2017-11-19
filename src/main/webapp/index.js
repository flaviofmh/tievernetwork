'use strict';

angular.module('tiever.index', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/index', {
    templateUrl: 'index.html',
    controller: 'IndexCtrl'
  });
}])

.controller('IndexCtrl', function($scope) {

	$scope.nome = 'tag to get boxed layout. Width of box can be changed to your own.';
	
});