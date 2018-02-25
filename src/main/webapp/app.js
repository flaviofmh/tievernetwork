'use strict';

angular.module('tiever', [
  'ngRoute',
  'tiever.index',
  'tiever.register'
])
.service('app', function () {
  this.domain = 'http://localhost:8080';
})
/**
.run(function($rootScope, $location, System) {
  $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute) {
    var logged = sessionStorage.getItem('token') ? true : false;
    var appTo = currRoute.$$route.originalPath;  
    if (appTo != '/register' && !logged) {
      event.preventDefault();
      $location.path('/register');
    } 
    if (appTo == '/register' && logged) {
      $location.path('/index');
    }
  });
})
**/
.config(function ($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  $routeProvider.otherwise({redirectTo: '/index'});
})
.config(function ($httpProvider) {
	$httpProvider.interceptors.push('MyAuthRequestInterceptor');
})
.factory('MyAuthRequestInterceptor',
    function ($q, $location) {
        return {
            'request': function (config) {
                if (sessionStorage.getItem('token')) {
                    config.headers.authorization = sessionStorage.getItem('token');
                }
                return config || $q.when(config);
            },
            responseError: function(rejection) {
                if (rejection.status == 401) {
                    $location.path('/register');
                }
                return $q.reject(rejection);
            }
        }
    }
);