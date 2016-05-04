Ext.define('WebclientApp.view.main.chart.Chart', {
    extend: 'Ext.Panel',
    xtype: 'chart-panel',
    controller: 'chart',

    requires: [
      'WebclientApp.view.main.chart.DateChooser',
      'WebclientApp.view.main.chart.ProbeChooser'
    ],

    layout: 'fit',

    items:  [],

    hcContainer: "highchartcontainer",


    masterRes: 300,
    detailRes: 1000,

    hcMasterContainer: "master-container",
    hcDetailContainer: "detail-container",

    hcMasterContainerSel: null,
    hcDetailContainerSel: null,

    demo: false,
    probeId: 'PROBE002',

    datechooser: null,
    probelist : null,
    selectedProbe: null,


    getChartData: function(dFrom, dTo, resolution, cb){

        $.getJSON('http://localhost:8080/measurements/' + (this.demo ? 'demo/' : '' ) + this.probeId +'/from/' + dFrom + '/to/' + dTo + '/' + resolution, function (data) {
            if(cb){
                cb(data);
            }
        });

    },


    onDateChanged: function(evt, data) {

        if(data == null ||
            data.startDate == "" || typeof data.startDate == "undefined" ||
            data.endDate == "" || typeof data.endDate == "undefined" ) {
            return;
        }

        var $scope = this;

        console.log("onDateChanged", arguments);

        var dFrom = (new Date(data.startDate + ' ' + (data.starTime || ''))).getTime();
        var dTo = (new Date(data.endDate + ' ' + (data.endTime || '' ))).getTime();

        this.getChartData(dFrom, dTo, this.masterRes, function (data) {
            if ( data != null && data.constructor === Array && data.length != 0 ) {
                $scope.updateMasterChart(data);
            }
        });

        /*
        $.getJSON('http://localhost:8080/measurements/' + (this.demo ? 'demo/' : '' ) + this.probeId +'/from/' + dFrom + '/to/' + dTo + '/600', function (data) {

            if ( data != null && data.constructor === Array && data.length != 0 ) {
                $scope.updateMasterChart(data);
            }

        });
        */
    },

    onProbeSelected: function(evt,data){
        if(data == null || data.probe == "" || data.probe == "undefined"){
            return;
        }
        
        var $scope = this;
        
        console.log("onProbeSelected", arguments);
        
        selectedProbe = data.probe;
        
        $getJSON('http://localhost:8080/measurements/'+selectedProbe+'/'+dFrom+'/to/'+dTo+'/',function (data){
            $scope.updateMasterChart(data);
        });
    },
    
    initComponent: function(){

        this.callParent();

        this.hcContainerSel = "#" + this.hcContainer;

        this.hcMasterContainerSel = "#" + this.hcContainer + " > ." + this.hcMasterContainer;
        this.hcDetailContainerSel = "#" + this.hcContainer + " > ." + this.hcDetailContainer;

        this.probelist = new WebclientApp.view.main.chart.ProbeChooser({
            listenrs: {
                select: {
                    scope: this,
                    fn: this.onProbeSelected
                }
            }
        });
        this.add(this.probelist);

        this.datechooser = new WebclientApp.view.main.chart.DateChooser({
            listeners: {
                datechanged: {
                    scope: this,
                    fn: this.onDateChanged
                }
            }
        });

        this.add(this.datechooser);

        this.add([{
            xtype: 'panel',
            title: 'p0013 <i>(jeanluc)</i>',
            layout: 'fit',
            height: 800,
            monitorResize: true,
            html: '<div id="'+ this.hcContainer + '" style="min-width: 600px; height: 400px; margin: 0 auto"></div>'
        } ]);
    },

    detailChart: null,
    masterChart: null,

    updateMasterChart: function(data) {

        this.masterChart.series[0].update({
            pointStart: data[0][0][0],
            data: data[0]
        }, true);

        this.masterChart.series[1].update({
            pointStart: data[1][0][0],
            data: data[1]
        }, true);

        this.updateDetailChart(data[0]);

        this.doSelect(data[0][0][0], data[0][data[0].length -1][0], this.masterChart.xAxis[0]);
    },


    updateDetailChart: function(data) {

        // prepare the detail chart
        var detailData = [ [], [] ],
        detailStart = [ data[0][0], data[1][0] ];

        $.each(this.masterChart.series[0].data, function () {
            if (this.x >= detailStart[0]) {
              detailData[0].push(this.y);
            }
        });

        $.each(this.masterChart.series[1].data, function () {
            if (this.x >= detailStart[1]) {
              detailData[1].push(this.y);
            }
        });

        this.detailChart.series[0].update({
            pointStart: detailStart[0],
            data: detailData[0]
        }, true);

        this.detailChart.series[1].update({
            pointStart: detailStart[1],
            data: detailData[1]
        }, true);
    },


    doSelect: function(__min, __max, __xAxis) {
        var $scope = this;

        var min = __min,
            max = __max,
            detailData = [[],[]],
            xAxis = __xAxis;

        /*
        // reverse engineer the last part of the data
        $.each($scope.masterChart.series[0].data, function () {
            if (this.x > min && this.x < max) {
                detailData[0].push([this.x, this.y]);
            }
        });

        $.each($scope.masterChart.series[1].data, function () {
            if (this.x > min && this.x < max) {
                detailData[1].push([this.x, this.y]);
            }
        });
        */

        $scope.getChartData(min, max, this.detailRes, function(data){

            $scope.detailChart.series[0].setData(data[0]);
            $scope.detailChart.series[1].setData(data[1]);
        });

        var fromSeriesData = $scope.masterChart.series[0].data;

        // move the plot bands to reflect the new detail span
        xAxis.removePlotBand('mask-before');
        xAxis.addPlotBand({
            id: 'mask-before',
            from: fromSeriesData[0].x,
            to: min,
            color: 'rgba(0, 0, 0, 0.2)'
        });

        xAxis.removePlotBand('mask-after');
        xAxis.addPlotBand({
            id: 'mask-after',
            from: max,
            to: fromSeriesData[fromSeriesData.length - 1].x,
            color: 'rgba(0, 0, 0, 0.2)'
        });

        return false;
    },


    createMaster: function(fn) {

        var $scope = this;

        var data = [[[]],[[]]];

        $scope.masterChart = $($scope.hcMasterContainerSel).highcharts({
            chart: {
                reflow: false,
                borderWidth: 0,
                backgroundColor: null,
                marginLeft: 50,
                marginRight: 20,
                zoomType: 'x',
                events: {

                    // listen to the selection event on the master chart to update the
                    // extremes of the detail chart
                    selection: function (event) {

                        var extremesObject = event.xAxis[0],
                            min = extremesObject.min,
                            max = extremesObject.max;

                        $scope.doSelect(min, max, $scope.masterChart.xAxis[0]);

                        return false;
                    }
                }
            },
            title: {
                text: null
            },
            xAxis: {
                type: 'datetime',
                showLastTickLabel: true,
                maxZoom: 64 * 24 * 3600000, // fourteen days
                plotBands: [{
                    id: 'mask-before',
                    from: data[0][0],
                    to: data[data.length - 1][0],
                    color: 'rgba(0, 0, 0, 0.2)'
                }],
                title: {
                    text: null
                }
            },
            yAxis: {
                gridLineWidth: 0,
                labels: {
                    enabled: false
                },
                title: {
                    text: null
                },
                min: 0.6,
                showFirstLabel: false
            },
            tooltip: {
                formatter: function () {
                    return false;
                }
            },
            legend: {
                enabled: false
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                series: {
                    fillColor: {
                        linearGradient: [0, 0, 0, 70],
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, 'rgba(255,255,255,0)']
                        ]
                    },
                    lineWidth: 1,
                    marker: {
                        enabled: false
                    },
                    shadow: false,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    enableMouseTracking: false
                }
            },

            series: [{
                type: 'area',
                name: 'USD to EUR',
                pointInterval: 24 * 3600 * 1000,
                pointStart: data[0][0][0],
                data: data[0]
            },{
                type: 'area',
                name: 'USD to EUR',
                pointInterval: 24 * 3600 * 1000,
                pointStart: data[1][0][0],
                data: data[1]
            } ],

            exporting: {
                enabled: false
            }

        }, function (masterChart) {
            console.log("masterChart created", masterChart);
            $scope.createDetail(masterChart, function(detailChart){
                if (typeof fn === "function") fn();
            });
        })
            .highcharts(); // return chart instance
    },


    createDetail: function(masterChart, fn) {

        var data = [[]];

        // prepare the detail chart
        var detailData = [[],[]],
        detailStart = [ data[0][0], data[0][0] ];

        $.each(masterChart.series[0].data, function () {
            if (this.x >= detailStart[0]) {
              detailData[0].push(this.y);
            }
        });
        $.each(masterChart.series[1].data, function () {
            if (this.x >= detailStart[1]) {
              detailData[1].push(this.y);
            }
        });

        // create a detail chart referenced by a global variable
        this.detailChart = $(this.hcDetailContainerSel).highcharts({
            chart: {
              marginBottom: 120,
              reflow: false,
              marginLeft: 50,
              marginRight: 20,
              style: {
                  position: 'absolute'
              }
            },
            credits: {
              enabled: false
            },
            title: {
              text: 'Bandwidth statistic'
            },
            subtitle: {
              text: 'Select an area by dragging across the lower chart'
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: null
                },
                maxZoom: 0.1
            },
            tooltip: {
                formatter: function () {
                    // debugger

                    var y1 = this.points[0].series.chart.series[0].yData[this.points[0].point.index];
                    var y2 = this.points[0].series.chart.series[1].yData[this.points[0].point.index];

                    var sOneVisible = this.points[0].series.chart.series[0].visible;
                    var sSecondVisible = this.points[0].series.chart.series[1].visible;

                    var name1 = this.points[0].series.chart.series[0].name;
                    var name2 = this.points[0].series.chart.series[1].name;

                    var v1 = ("     " + Highcharts.numberFormat(y1, 2)).slice(-5);
                    var v2 = ("     " + Highcharts.numberFormat(y2, 2)).slice(-5);

                    var names = [];
                    if (sOneVisible) names.push(name1);
                    if (sSecondVisible) names.push(name2);

                    var values = [];
                    if (sOneVisible) values.push('<b> '+ v1 + '</b>');
                    if (sSecondVisible) values.push('<b> '+ v2 + '</b>');

                    return '<b>'
                                + names.join('/') + '</b>' + '<br/>'
                                + Highcharts.dateFormat('%Y %m %d, %H:%M:%S', this.x) + '<br/>'
                                + values.join('/') + ' kB/s';
                },
                shared: true,
                crosshairs: true,
                followPointer: true
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    marker: {
                      enabled: false,
                      states: {
                          hover: {
                              enabled: false,
                              radius: 1,
                              lineWidth: 2
                          }
                      }
                    }
                },
                line: {
                    states: {
                        hover: {
                            enabled: false
                        }
                    }
                }
            },
            series: [{
              name: 'Upload',
              pointStart: detailStart[0],
              pointInterval: 24 * 3600 * 1000,
              data: detailData[0],
              lineWidth: 2
            } , {
              name: 'Download',
              pointStart: detailStart[1],
              pointInterval: 24 * 3600 * 1000,
              data: detailData[1],
              lineWidth: 2
            } ],

            exporting: {
              enabled: false
            }

        }, function(detailChart){
            console.log("detailChart created", detailChart);
            if (typeof fn === "function") fn();
        }).highcharts(); // return chart
    },


    listeners: {

        resize: function() {

            var $scope = this;

            console.log("painted");

            // make the container smaller and add a second container for the master chart
            var $container = $(this.hcContainerSel)
                .css('position', 'relative');

            $('<div class="' + this.hcDetailContainer + '">')
                .appendTo($container);

            $('<div class="' + this.hcMasterContainer + '">')
                .css({
                      position: 'absolute',
                      top: 300,
                      height: 100,
                      width: '100%'
                  }) 
                .appendTo($container);

            // create master and in its callback, create the detail chart
            $scope.createMaster(function(){
                console.log("masterChart, detailChart created");

                /*
                $.getJSON('http://localhost:8080/measurements/1/from/1456790400000/to/1461494069283/', function (data) {
                    $scope.updateMasterChart(data);
                });
                */
                
            });
        }
    }

});
