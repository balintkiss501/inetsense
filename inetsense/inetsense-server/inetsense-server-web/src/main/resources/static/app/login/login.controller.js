'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:LoginController
 * @description
 * # LoginController
 * Controller of the login-register form
 */
angular.module('inetsense')
    .controller('LoginController', function($scope, $cacheFactory, $http, $location) {
        var self = this;

        self.credentials = {};
        self.login = function() {
            self.loginError = false;
            self.registerError = false;
            self.registerSuccess = false;

            var req = {
                method: 'POST',
                url: 'login',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: $.param({ email: self.credentials.email, password: self.credentials.password }),
            };

            $http(req).then(
                function(response) {
                    var reqUser = {
                        method: 'GET',
                        url: 'user'
                    };
                    $http(reqUser).then(
                        function(response) {
                            var cF = $cacheFactory('myCache');
                            console.log("User's role: " + response.data.authorities["0"].authority);
                            cF.put('roleCache', response.data.authorities["0"].authority);
                            console.log("Read from cache: " + cF.get('roleCache'));
                            console.log("Login succeeded");
                            $location.path("/dashboard");
                        },
                        function(response) {
                            console.log("Login failed");
                            self.loginError = true;
                        })
                },
                function(response) {
                    console.log("Login failed");
                    self.loginError = true;
                }
            );
        };

        self.register = function() {
            self.loginError = false;
            self.registerError = false;
            self.registerSuccess = false;

            $http.post('register', self.credentials).then(function(result) {
                var resultString = result.data;
                console.log("Register result: " + resultString);

                switch (resultString) {
                case "OK":
                    self.registerSuccess = true;
                    break;
                case "USER_ALREADY_EXISTS":
                    self.registerError = true;
                    self.registerErrorMsg = "A megadott e-mail címmel már létezik felhasználó a rendszerben.";
                    break;
                default:
                    self.registerError = true;
                    self.registerErrorMsg = "Hiba történt a regisztráció során.";
                }
            }, function() {
                console.log("Register failed");
                self.registerError = true;
                self.registerErrorMsg = "Hiba történt a regisztráció során."
            });
        };
    });
