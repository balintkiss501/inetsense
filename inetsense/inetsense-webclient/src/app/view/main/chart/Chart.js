Ext.define('WebclientApp.view.main.chart.Chart', {
    extend: 'Ext.Panel',
    xtype: 'chart-panel',
    controller: 'chart',

    requires: [
      'WebclientApp.view.main.chart.DateChooser'
    ],

    layout: 'fit',

    items:  [],

    datechooser: null,

    onDateChanged: function() {
        console.log("onDateChanged", arguments);
    },

    initComponent: function(){

        this.callParent();

        this.datechooser = new WebclientApp.view.main.chart.DateChooser({
            listeners: {
                datechanged: this.onDateChanged
            }
        });

        this.add(this.datechooser);

        this.add([{
            xtype: 'panel',
            title: 'p0013 <i>(jeanluc)</i>',
            layout: 'fit',
            height: 600,
            monitorResize: true,
            html: '<div id="highchart-container" style="min-width: 600px; height: 400px; margin: 0 auto"></div>'
        } ]);
    },

    masterContainer: "#master-container",
    detailChart: null,
    masterChart: null,

    updateMasterChart: function(data) {

        this.masterChart.series[0].update({
            pointStart: data[0][0],
            data: data
        }, true);

        this.updateDetailChart(data);
    },


    updateDetailChart: function(data) {

        // prepare the detail chart
        var detailData = [],
        detailStart = data[0][0];

        $.each(this.masterChart.series[0].data, function () {
            if (this.x >= detailStart) {
              detailData.push(this.y);
            }
        });

        this.detailChart.series[0].update({
            pointStart: detailStart,
            data: detailData
        }, true);
    },


    createMaster: function(fn) {

        var $scope = this;

        var data = [[]];

        $scope.masterChart = $($scope.masterContainer).highcharts({
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
                            max = extremesObject.max,
                            detailData = [],
                            xAxis = this.xAxis[0];

                        // reverse engineer the last part of the data
                        $.each(this.series[0].data, function () {
                            if (this.x > min && this.x < max) {
                                detailData.push([this.x, this.y]);
                            }
                        });

                        // move the plot bands to reflect the new detail span
                        xAxis.removePlotBand('mask-before');
                        xAxis.addPlotBand({
                            id: 'mask-before',
                            from: data[0][0],
                            to: min,
                            color: 'rgba(0, 0, 0, 0.2)'
                        });

                        xAxis.removePlotBand('mask-after');
                        xAxis.addPlotBand({
                            id: 'mask-after',
                            from: max,
                            to: data[data.length - 1][0],
                            color: 'rgba(0, 0, 0, 0.2)'
                        });


                        $scope.detailChart.series[0].setData(detailData);

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
                pointStart: data[0][0],
                data: data
            }],

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
        var detailData = [],
        detailStart = data[0][0];

        $.each(masterChart.series[0].data, function () {
            if (this.x >= detailStart) {
              detailData.push(this.y);
            }
        });

        // create a detail chart referenced by a global variable
        this.detailChart = $('#detail-container').highcharts({
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
                  var point = this.points[0];
                  return '<b>' + point.series.name + '</b><br/>' + Highcharts.dateFormat('%A %B %e %Y', this.x) + ':<br/>' +
                      Highcharts.numberFormat(point.y, 2) + ' kB/s';
              },
              shared: true
            },
            legend: {
              enabled: false
            },
            plotOptions: {
              series: {
                  marker: {
                      enabled: false,
                      states: {
                          hover: {
                              enabled: true,
                              radius: 3
                          }
                      }
                  }
              }
            },
            series: [{
              name: 'Avarage bandwidth',
              pointStart: detailStart,
              pointInterval: 24 * 3600 * 1000,
              data: detailData
            }],

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
            var $container = $('#highchart-container')
                .css('position', 'relative');

            $('<div id="detail-container">')
                .appendTo($container);

            $('<div id="master-container">')
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

                $.getJSON('http://localhost:8080/measurements/1/from/1456790400000/to/1461494069283/', function (data) {
                    $scope.updateMasterChart(data);
                });
                
            });
        }
    }

});
