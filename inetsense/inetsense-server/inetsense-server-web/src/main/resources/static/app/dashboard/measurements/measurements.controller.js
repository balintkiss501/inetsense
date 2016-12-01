'use strict';
/**
 * @ngdoc function
 * @name inetsense.controller:MeasurementsController
 * @description
 * # MeasurementsController
 * Controller of the measurements screen
 */
angular.module('inetsense', ['chart.js'])
    .controller('MeasurementsController', function ($scope, $http) {
        var self = this;

        self.probes = {};
        self.downloadData = {};
        self.uploadData = {};

        self.isLoading = false;
        self.masterChart = {};
        self.downloadSlaveChart = {};
        self.uploadSlaveChart = {};

        self.getProbes = function () {
            getProbeData();
        }

        self.getMeasurements = function(probeAuthId) {
            $http.get("measurements", {params: {"authId": probeAuthId}})
                .then(function(value) {
                if (value.data) {
                    self.downloadData = value.data.map(getDownloadData);
                    self.uploadData = value.data.map(getUploadData);

                    self.uploadSlaveChart = createSlaveChart('uploadChartContainer', 'Feltöltési sebesség',
                        self.uploadSlaveChart, self.uploadData);
                    self.downloadSlaveChart = createSlaveChart('downloadChartContainer', 'Letöltési sebesség',
                        self.downloadSlaveChart, self.downloadData);
                    createMasterChart();

                };
            })
        }

        getProbeData();

        function getProbeData() {
            $http.get("probes").then(function(value) {
                self.probes = value.data;
            })
        }

        function getDownloadData(element) {
            return { x : new Date(element.completedOn).getTime(), y : element.downloadSpeed }
        }

        function getUploadData(element) {
            return { x : new Date(element.completedOn).getTime(), y : element.uploadSpeed }
        }

        function createMasterChart() {
            if (self.downloadData && self.uploadData) {
                if (self.masterChart) {
                    $(self.masterChart).remove();
                }

                self.mainChart = Highcharts.stockChart('masterChartContainer', {
                    title: {
                        text: 'Le- és feltöltési sebesség'
                    },

                    xAxis: {
                        events: {
                            setExtremes: function(e) {
                                setTimeout(function () {
                                    if (!self.isLoading) {
                                        self.isLoading = true;
                                        self.downloadSlaveChart.xAxis[0].setExtremes(e.min, e.max);
                                        self.uploadSlaveChart.xAxis[0].setExtremes(e.min, e.max);
                                        self.isLoading = false;
                                    }
                                }, 1000);
                            }
                        }
                    },

                    series: [{
                        name: 'Feltöltési sebesség',
                        data: self.uploadData,
                        tooltip: {
                            valueSuffix: ' byte/s'
                        }
                    },
                    {
                        name: 'Letöltési sebesség',
                        data: self.downloadData,
                        tooltip: {
                            valueSuffix: ' byte/s'
                        }
                    }]
                });
            }
        }

        function createSlaveChart(containerName, chartName, chartObject, data) {
            if (data) {
                if (chartObject) {
                    $(chartObject).remove();
                }
            }
            return Highcharts.stockChart(containerName, {
                title: {
                    text: chartName
                },

                rangeSelector: {
                    enabled: false
                },

                navigator: {
                    enabled: false
                },

                scrollbar: {
                    enabled: false
                },

                series: [{
                    name: chartName,
                    data: data,
                    tooltip: {
                        valueSuffix: ' byte/s'
                    }
                }]
            });
        }
    });
