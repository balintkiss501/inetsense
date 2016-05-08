Ext.define('WebclientApp.view.main.chart.ProbeChooser',{
    extend: 'Ext.form.Panel',
    xtype: 'probechooser',
    
    requires: [
        'WebclientApp.store.Probes'
    ],
    
    title: 'Probes',
    width: 400,
    layout: 'form',
    viewModel: {},
    

    
    initComponent: function(){
         var data = [{
           id: 'PROBE001'
        }, {
            id: 'PROBE002'
        },{
            id: 'PROBE003'
        }];

        var store = Ext.create('Ext.data.Store', {
            fields: ['id']
        });
        
        store.loadRawData(data, false);
        Ext.apply(this, {
            items: [{
                xtype: 'fieldset',
                layout: 'anchor',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'Selected probe',
                    bind: '{probes.value}'
                },{
                    xtype: 'combobox',
                    // style: 'font: normal 12px courier',
                    // reference: 'probes',
                    // publishes: 'value',
                    queryMode: 'local',
                    fieldLabel: 'Select Probe',
                    displayField: 'id',
                    valueField: 'id',
                    // anchor: '-15',
                    store: store
                    // minChars: 0,
                    //queryMode: 'local',
                    // typeAhead: true
                }]
            }]
        });
        this.callParent();
    }
    
    
    
    
    
});