'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:DashboardController
 * @description
 * # DashboardController
 * Controller of the dashboard
 */
angular.module('inetsense')
    .controller('DashboardController', function($scope) {
        var self = this;

        self.toggleWrapper = function(event) {
            event.preventDefault();
            $("#wrapper").toggleClass("toggled");
        };
    });
