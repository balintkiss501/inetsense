'use strict';
/**
 * @ngdoc overview
 * @name inetsense
 * @description
 * # inetsense
 *
 * Main module of the application.
 */
angular.module('inetsense', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/login', {
        templateUrl : 'app/login/login.html',
        controller : 'login',
        controllerAs : 'controller'
    }).when('/dashboard', {
        templateUrl : 'app/dashboard/dashboard.html',
        controller : 'dashboard',
        controllerAs : 'controller'
    }).otherwise('/login');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('login',

    function($rootScope, $http, $location, $route) {

        var self = this;

        self.tab = function(route) {
            return $route.current && route === $route.current.controller;
        };

        var authenticate = function(credentials, callback) {

            var headers = credentials ? {
                authorization : "Basic "
                    + btoa(credentials.username + ":"
                        + credentials.password)
            } : {};

            $http.get('user', {
                headers : headers
            }).then(function(response) {
                if (response.data.name) {
                    $rootScope.authenticated = true;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback($rootScope.authenticated);
            }, function() {
                $rootScope.authenticated = false;
                callback && callback(false);
            });

        }

        authenticate();

        self.credentials = {};
        self.login = function() {
            authenticate(self.credentials, function(authenticated) {
                if (authenticated) {
                    console.log("Login succeeded")
                    $location.path("/dashboard");
                    self.error = false;
                    $rootScope.authenticated = true;
                } else {
                    console.log("Login failed")
                    $location.path("/login");
                    self.error = true;
                    $rootScope.authenticated = false;
                }
            })
        };

        $rootScope.logout = function() {
            $http.post('logout', {}).success(function() {
                $rootScope.authenticated = false;
                $location.path("/");
            }).error(function(data) {
                $rootScope.authenticated = false;
            });
        };

    }).controller('dashboard', function($http) {
        // TODO
});
