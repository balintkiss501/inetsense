
/**
 * The Basic Column Chart displays a set of random data in a column series. The "Reload Data"
 * button will randomly generate a new set of data in the store.
 *
 * Tapping or hovering a column will highlight it.
 * Dragging a column will change the underlying data.
 * Try dragging below 65 degrees Fahrenheit.
 */
Ext.define('TutorialApp.view.HighChart', {
    extend: 'Ext.Panel',
    xtype: 'highchart-panel',
    controller: 'highchart',

    layout: 'fit',

    items: [{
        xtype: 'panel',
        title: 'p0013 <i>(jeanluc)</i>',
        layout: 'fit',
        monitorResize: true,
        html: '<div id="highchart-container" style="min-width: 600px; height: 400px; margin: 0 auto"></div>'
    }],

    listeners: {

        resize: function() {
            console.log("painted");

            $('#highchart-container').highcharts({
                chart: {
                  type: 'column'
                },
                title: {
                  text: 'Bandwidth statistic'
                },
                subtitle: {
                  text: '11 April, 2016  to 17 April, 2016'
                },
                xAxis: {
                  type: 'category'
                },
                yAxis: {
                  title: {
                    text: 'Bandwidth (kB/s)'
                  }

                },
                legend: {
                  enabled: false
                },
                plotOptions: {
                  series: {
                    borderWidth: 0,
                    dataLabels: {
                      enabled: true,
                      format: '{point.y:.1f}'
                    }
                  }
                },

                tooltip: {
                  headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                  pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
                },

                series: [{
                  name: 'Napok',
                  // colorByPoint: true,
                  data: [
                    ['Hétfő', 56, 'Monday'],
                    ['Kedd', 24, 'Tuesday'],
                    ['Szerda', 10, 'Wednesday'],
                    ['Csütörtök', 15, 'Thursday'],
                    ['Péntek', 17, 'Friday'],
                    ['Szombat', 45, 'Saturday'],
                    ['Vasárnap', 35, 'Sunday']
                  ],
                  keys: ['name', 'y', 'drilldown']
                }],
                drilldown: {
                  series: [{
                    name: 'Hétfő',
                    id: 'Monday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: 'Kedd',
                    id: 'Tuesday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: 'Szerda',
                    id: 'Wednesday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: 'Csütörtök',
                    id: 'Thursday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: 'Péntek',
                    id: 'Friday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: 'Szombat',
                    id: 'Saturday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: 'Vasárnap',
                    id: 'Sunday',
                    data: [
                      ['8-12h', 12, '8h'],
                      ['12-16h', 16, '12h'],
                      ['16-20h', 20, '16h'],
                      ['20-24h', 24, '20h'],
                      ['0-4h', 4, '0h'],
                      ['4-8h', 8, '4h']
                    ],
                    keys: ['name', 'y', 'drilldown']
                  }, {
                    name: '8-12h',
                    id: '8h',
                    data: [
                      ['8h', 8],
                      ['9h', 9],
                      ['10h', 10],
                      ['11h', 11]
                    ]
                  }, {
                    name: '12-16h',
                    id: '12h',
                    data: [
                      ['12h', 12],
                      ['13h', 13],
                      ['14h', 14],
                      ['15h', 15]
                    ]
                  }, {
                    name: '16-20h',
                    id: '16h',
                    data: [
                      ['16h', 16],
                      ['17h', 17],
                      ['18h', 18],
                      ['19h', 19]
                    ]
                  }, {
                    name: '20-24h',
                    id: '20h',
                    data: [
                      ['20h', 20],
                      ['21h', 21],
                      ['22h', 22],
                      ['23h', 23]
                    ]
                  }, {
                    name: '0-4h',
                    id: '0h',
                    data: [
                      ['0h', 0],
                      ['1h', 1],
                      ['2h', 2],
                      ['3h', 3]
                    ]
                  }, {
                    name: '4-8h',
                    id: '4h',
                    data: [
                      ['4h', 4],
                      ['5h', 5],
                      ['6h', 6],
                      ['7h', 7]
                    ]
                  }]
                }
              });
        }

    }

});