Ext.define('WebclientApp.view.main.chart.StockChart', {
    extend: 'Ext.Panel',

    xtype: 'stock-chart-panel',

    title: 'Bandwidth statistic',

    requires: [
        'WebclientApp.view.main.chart.DateChooser',
        'WebclientApp.view.main.chart.StockChartComp'
    ],


    onDateChanged: function(ctr, val){
    	console.log(">>> onDateChanged", arguments);

    	this.stockChartComp.masterDateFrom = new Date(val.startDate + " " + (val.startTime || "")).getTime();
    	this.stockChartComp.masterDateTo = new Date(val.endDate + " " + (val.endTime || "")).getTime();
        this.tableComp.dateFrom = new Date (val.startDate + " "+(val.startTime || "")).getTime();
        this.tableComp.dateTo = new Date (val.endDate + " "+(val.endTime || "")).getTime();

    },


    onProbeChanged: function(evt, val){
		console.log(">>> onProbeChanged", arguments);
        this.probeId = val;
		this.stockChartComp.probeId = val;
        this.tableComp.probeId = val;
    },
    
    
    probeSelector: null,
    datechooser: null,
    stockChartComp: null,
    showMeasurements: null,
    formatChooser: null,
    tableComp: null,
    probeId: null,
    
    initComponent: function(){
        var $scope = this;
        
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

        this.formatChooser = new Ext.form.RadioGroup({
           fieldLabel: 'Choose format',
           layout: 'hbox',
           bodyPadding: 10,
           items: [{
                    xtype:      'radiofield',
                    boxLabel  : 'Graph',
                    name      : 'format',
                    inputValue: 'graph',
                    checked : true,
                    handler: function(checkbox, checked){
                            if(checked){
                                $scope.items.getAt(4).show();
                                $scope.items.getAt(5).hide();
                            }else{
                                $scope.items.getAt(4).hide();
                                $scope.items.getAt(5).show();
                            }
                       
                    }
           },{
                    xtype:  'radiofield',
                    boxLabel  : 'Table',
                    name      : 'format',
                    inputValue: 'table'
           }]
        });
        
        this.showMeasurements = new Ext.Button({
            text: 'Show Measurements',
            handler: function(){
                console.log('update');
                if($scope.probeId===null){
                    Ext.MessageBox.alert('Probe','Please select a Probe', function(btn,text){},this);
                }else{
                    $scope.stockChartComp.updateChart();
                    $scope.tableComp.updateStore();
                }
            }
        });
        
		this.stockChartComp = new WebclientApp.view.main.chart.StockChartComp({
		});

        this.tableComp = new WebclientApp.view.main.table.TableComp({
        });
        
		this.add(this.probeSelector);
		this.add(this.datechooser);
        this.add(this.showMeasurements);
        this.add(this.formatChooser);
		this.add(this.stockChartComp);
        this.add(this.tableComp);
        this.items.getAt(5).hide();
    }

});

