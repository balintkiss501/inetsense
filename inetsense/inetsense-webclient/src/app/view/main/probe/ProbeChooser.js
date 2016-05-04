Ext.define('WebclientApp.view.main.chart.ProbeChooser',{
    extend: 'Ext.form.Panel',
    xtype: 'probelist',
    
    requires: [
        'WebclientApp.store.Probes'
    ],
    
    title: 'Probes',
    width: 400,
    layout: 'form',
    //viewModel: {},
    

    
    initComponent: function(){
        Ext.apply(this,{
                items: [{
                xtype: 'fieldset',
                layout: 'anchor',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'Selected probe',
                    bind: '{probes.value}'
                },{
                    xtype: 'combobox',
                    style: 'font: normal 12px courier',
                    reference: 'probes',
                    publishes: 'value',
                    fieldLabel: 'Select Probe',
                    displayField: 'probe',
                    anchor: '-15',
                    forselecton: true,
                    store: {
                        style: 'font: normal 12px courier',
                        type: 'probes'
                    },
                    minChars: 0,
                    //queryMode: 'local',
                    typeAhead: true
                    }]
            }] 
        });
        this.callParent();
    }
    
    
    
    
    
});