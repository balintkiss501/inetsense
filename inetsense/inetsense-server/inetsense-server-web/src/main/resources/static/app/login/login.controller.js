'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:LoginController
 * @description
 * # LoginController
 * Controller of the login-register form
 */
angular.module('inetsense')
    .controller('LoginController', function($rootScope, $http, $location) {
        var self = this;

        var authenticate = function(credentials, callback) {

            var headers = credentials ? {
                authorization : "Basic "
                    + btoa(credentials.email + ":"
                        + credentials.password)
            } : {};

            $http.get('user', {
                headers : headers
            }).then(function(response) {
                var authenticated = response.data.name ? true : false;
                callback && callback(authenticated);
            }, function() {
                callback && callback(false);
            });
        }

        authenticate();

        self.credentials = {};
        self.login = function() {
            self.loginError = false;
            self.registerError = false;
            self.registerSuccess = false;

            authenticate(self.credentials, function(authenticated) {
                if (authenticated) {
                    console.log("Login succeeded");
                    $location.path("/dashboard");
                } else {
                    console.log("Login failed");
                    $location.path("/login");
                    self.loginError = true;
                }
            })
        };

        self.register = function() {
            self.loginError = false;
            self.registerError = false;
            self.registerSuccess = false;

            $http.post('register', self.credentials).then(function() {
                console.log("Register succeeded");
                self.registerSuccess = true;
            }, function() {
                console.log("Register failed");
                self.registerError = true;
            });
        };

        $rootScope.logout = function() {
            $http.post('logout', {}).then(function() {
                console.log("Logout succeeded");
                $location.path("/");
            }, function() {
                console.log("Logout failed");
            });
        };
    });
