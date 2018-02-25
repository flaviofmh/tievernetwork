'use strict';

angular.module('tiever.register', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/register', {
    templateUrl: 'register.html',
    controller: 'RegisterCtrl'
  });
}])

.controller('RegisterCtrl', function($scope, $http, $location, $window, app) {

	$scope.usuario = {};
	$scope.user = {
			username: '',
			password: ''
		}
	
	$scope.registrar = function() {
		var verboMetodo = 'POST';
		if($scope.usuario.id) {
			verboMetodo = 'PUT';
		}
		$http({
			  method: verboMetodo,
			  url: app.domain + "/usuario",
			  data: $scope.usuario
			}).then(function successCallback(response) {
			    $scope.mensagem = 'Dados Enviados Com Sucesso';
			    $scope.usuario = {};
			}, function errorCallback(response) {
				$scope.mensagemerror = response;
			});
	};
	
	$scope.login = function(valid) {
		if (valid) {
			$http.post(app.domain + "/login", $scope.user)
		    .then(function(response) {
		    	var header = response.headers();
		        sessionStorage.setItem("token", header['authorization']); 		        
		        delete $scope.user;
		    	$window.location.reload();
		    });
		    
		}
	}
	
	
});