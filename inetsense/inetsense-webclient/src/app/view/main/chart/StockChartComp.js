Ext.define('WebclientApp.view.main.chart.StockChartComp', {
    extend: 'Ext.Panel',

    xtype: 'stockchart-comp',

    layout: 'fit',

    loadMask: null,

    probeId: null,
    demo: false,
    masterDateFrom: new Date('2016-04-15').getTime(),
    masterDateTo: new Date().getTime(),


    updateChart: function() {

        var $scope = this;

        var chart = $('.chartContainer').highcharts();
        if(typeof chart === "undefined" || this.probeId == null || this.probeId == "") return;

        chart.destroy();
        $scope.loadMask.show();
        var $scope = this;
        $scope.getChartData( $scope.masterDateFrom, $scope.masterDateTo, 100, function(data){
            $scope.createChartFN(data);
        });

    },


    listeners: {
        afterrender: function() {

            var $scope = this;

            this.loadMask.show();

            this.getChartData(
                $scope.masterDateFrom, $scope.masterDateTo, 100, function(data){
                $scope.createChartFN(data);
            });

        }
    },


    getChartData: function(dFrom, dTo, resolution, cb){

        dFrom = (dFrom + "").split(".")[0];
        dTo = (dTo + "").split(".")[0];

        $.getJSON(WebclientApp.CONFIG.baseUrl + '/measurements/' + (this.demo ? 'demo/' : '' ) + this.probeId +'/from/' + dFrom + '/to/' + dTo + '/' + resolution, function (data) {
            if(cb){
                cb(data);
            }
        });

    },


    createChartFN: function(initialData) {

        var $scope = this;

        /**
         * Load new data depending on the selected min and max
         */
        function afterSetExtremes(e) {

            var chart = $('.chartContainer').highcharts();

            chart.showLoading('Loading data from server...');
            $scope.getChartData( Math.round(e.min), Math.round(e.max), 100, function(data){

                chart.series[0].setData(data[0]);
                chart.series[1].setData(data[1]);

                chart.hideLoading();
            });
        }

        // create the chart
        $('.chartContainer').highcharts('StockChart', {
            chart : {

                events: {
                    load: function () {
                        $scope.loadMask.hide();
                    }
                },

                zoomType: 'x'
            },

            navigator : {
                adaptToUpdatedData: false
            },

            tooltip: {
                pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> MB/s<br/>',
                valueDecimals: 2,
                shared: true
            },

            scrollbar: {
                liveRedraw: false
            },

            title: {
                text: 'Probe bandwidth'
            },

            subtitle: {
                text: ''
            },

            rangeSelector : {
                buttons: [{
                    type: 'minute',
                    count: 20,
                    text: '20M'
                }, {
                    type: 'hour',
                    count: 1,
                    text: '1h'
                }, {
                    type: 'day',
                    count: 1,
                    text: '1d'
                }, {
                    type: 'month',
                    count: 1,
                    text: '1m'
                }, {
                    type: 'year',
                    count: 1,
                    text: '1y'
                }, {
                    type: 'all',
                    text: 'All'
                }],
                inputEnabled: false, // it supports only days
                selected : 5 // all
            },

            xAxis : {
                events : {
                    afterSetExtremes : afterSetExtremes
                },
                minRange: 3600 * 1000 / 3 // one hour
            },

            yAxis: {
                floor: 0
            },

            series : [{
                name: 'Download',
                data : initialData[0],
                dataGrouping: {
                    enabled: false
                }
            },{
                name: 'Upload',
                data : initialData[1],
                dataGrouping: {
                    enabled: false
                }
            }]
        });

    },


    initComponent: function(){

        this.callParent();

        var charComp = new Ext.panel.Panel({
            layout: 'fit',
            monitorResize: true,
            html: '<div class="chartContainer" style="min-width: 600px; height: 400px; margin: 0 auto"></div>'
        });

        this.loadMask = new Ext.LoadMask({
            msg    : 'Please wait...',
            target : charComp
        });

        this.add(charComp);
    }


});
