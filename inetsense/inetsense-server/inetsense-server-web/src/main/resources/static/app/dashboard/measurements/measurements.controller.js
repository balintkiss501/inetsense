'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:MeasurementsController
 * @description
 * # MeasurementsController
 * Controller of the measurements screen
 */
angular.module('inetsense')
    .controller('MeasurementsController', function ($scope, $cacheFactory, $http) {
        var cF = $cacheFactory('myCache');
        console.log("User's role in Meas: " + cF.get('roleCache'));
});
