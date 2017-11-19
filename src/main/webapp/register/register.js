'use strict';

angular.module('tiever.register', ['ngRoute'])

.controller('RegisterCtrl', function($scope, $http) {

	$scope.usuario = {};
	
	$scope.registrar = function() {
		var verboMetodo = 'POST';
		if($scope.usuario.id) {
			verboMetodo = 'PUT';
		}
		$http({
			  method: verboMetodo,
			  url: "http://localhost:8080/usuario",
			  data: $scope.usuario
			}).then(function successCallback(response) {
			    $scope.mensagem = 'Dados Enviados Com Sucesso';
			    $scope.usuario = {};
			}, function errorCallback(response) {
				$scope.mensagemerror = response;
			});
	};
	
});