'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:ProbesController
 * @description
 * # ProbesController
 * Controller of the probes screen
 */
angular.module('inetsense')
    .controller('ProbesController', function ($scope, $rootScope, $http) {
        var self = this;

        self.freeProbesCount = 0;
        self.probes = {};

        var $probeRegBtn = $("#probeRegBtn");

        var reloadProbes = function() {
            var getUrl = "probes";
            if ($rootScope.userRole == "ADMIN") {
                getUrl = "probes/listForAdmin";
            }
            $http.get(getUrl).then(function(value) {
                self.probes = value.data;
                return $http.get("probes/probeCountLimit");
            }).then( function(value) {
                self.freeProbesCount = value.data - self.probes.length;

                if (self.freeProbesCount <= 0 || $rootScope.userRole == "ADMIN") {
                    self.freeProbesCount = 0;
                    $probeRegBtn.prop("disabled", true);
                } else {
                    $probeRegBtn.prop("disabled", false);
                }
            });
        };

        reloadProbes();

        self.addProbe = function() {
            $http.post('probes', {}).then(function() {
                console.log("Adding probe succeeded");
                reloadProbes();
            }, function() {
                console.log("Adding probe failed");
            });
        };
});
