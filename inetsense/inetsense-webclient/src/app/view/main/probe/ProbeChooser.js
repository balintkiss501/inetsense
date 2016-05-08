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
                    queryMode: 'local',
                    fieldLabel: 'Select Probe',
                    displayField: 'authId',
                    valueField: 'authId',
                    store: {
                        type: 'probes'
                    },
                    listeners:{
                        render: function(combobox){
                           // Ext.getCmp('asd').getStore().load();
                           this.getStore().load();
                        }
                    }

                }]
            }]
        });
        this.callParent();
    }
    
    
    
    
    
});