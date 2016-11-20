'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:DashboardController
 * @description
 * # DashboardController
 * Controller of the dashboard
 */
angular.module('inetsense')
    .controller('DashboardController', function($scope, $http, $location) {
        var self = this;

        self.logout = function() {
            $http.post('logout', {}).then(function() {
                console.log("Logout succeeded");
                $location.path("/");
            }, function() {
                console.log("Logout failed");
            });
        };

        self.toggleWrapper = function(event) {
            event.preventDefault();
            $("#wrapper").toggleClass("toggled");
        };
    });
