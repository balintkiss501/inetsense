Ext.define('WebclientApp.view.main.chart.Chart', {
    extend: 'Ext.Panel',

    xtype: 'stock-chart-panel',


    onDateChanged: function(){

    	console.log(">>> onDateChanged", arguments);
    },


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

        this.add(this.datechooser);
    }

});

