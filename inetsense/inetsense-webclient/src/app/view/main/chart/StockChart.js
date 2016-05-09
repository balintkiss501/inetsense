Ext.define('WebclientApp.view.main.chart.Chart', {
    extend: 'Ext.Panel',

    xtype: 'stock-chart-panel',

    title: 'Bandwidth statistic',


    onDateChanged: function(ctr, val){
    	console.log(">>> onDateChanged", arguments);

    	this.stockChartComp.masterDateFrom = new Date(val.startDate).getTime();
    	this.stockChartComp.masterDateTo = new Date(val.endDate).getTime();

    	this.stockChartComp.updateChart();
    },


    onProbeChanged: function(evt, val){
		console.log(">>> onProbeChanged", arguments);

		this.stockChartComp.probeId = val;
		this.stockChartComp.updateChart();
    },


    probeSelector: null,
    datechooser: null,
    stockChartComp: null,


    initComponent: function(){

		this.callParent();

		this.datechooser = new WebclientApp.view.main.chart.DateChooser({
            listeners: {
                datechanged: {
                    scope: this,
                    fn: this.onDateChanged
                }
            }
        });
        
		this.probeSelector = new WebclientApp.view.main.probe.ProbeSelector({
			listeners: {
				probechanged: {
					scope: this,
					fn: this.onProbeChanged
				}
			}
		});

		this.stockChartComp = new WebclientApp.view.main.chart.StockChartComp({
		});

		this.add(this.probeSelector);
		this.add(this.datechooser);
		this.add(this.stockChartComp);
    }

});

