Ext.define('WebclientApp.view.main.probe.ProbeSelector', {
    extend: 'Ext.form.Panel',
    xtype: 'probeselector',
    
    /*
    requires: [
        'WebclientApp.store.Probes'
    ],
    */
    
    layout: 'form',


    onFieldChange: function(evt, dataValue) {
        console.log("onFieldChange", "probechanged triggered");

        this.fireEvent('probechanged', this, dataValue, {origParam: arguments});
    },


    initComponent: function() {

        console.log(">>> initComponent", arguments);

        Ext.apply(this, {
            items: [{
                xtype: 'fieldcontainer',
                layout: 'anchor',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'Selected probe',
                    bind: '{selectedProbe}',
                    hidden: true
                },{
                    xtype: 'combobox',
                    queryMode: 'remote',
                    fieldLabel: 'Select Probe',
                    displayField: 'authId',
                    valueField: 'authId',
                    autoSelect: true,
                    autoLoadOnValue: true,
                    listeners: {
                        change: {
                            scope: this,
                            fn: this.onFieldChange
                        }
                    },
                    store: {
                        type: 'probes'
                    },
                    bind: {
                        value: '{selectedProbe}'
                    }
                }]
            }]
        });

        this.callParent();
    }

});
