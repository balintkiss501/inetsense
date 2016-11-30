'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:MeasurementsController
 * @description
 * # MeasurementsController
 * Controller of the measurements screen
 */
angular.module('inetsense')
    .controller('MeasurementsController', function ($scope, $rootScope, $window, $http) {
    console.log("Role (Meas): " + $rootScope.userRole);
    if ($rootScope.userRole == "ADMIN") {
        console.log("Admin...");
        $window.location.href = "#/dashboard";
    }
});
