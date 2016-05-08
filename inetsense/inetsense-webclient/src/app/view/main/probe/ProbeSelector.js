Ext.define('WebclientApp.view.main.probe.ProbeSelector', {    
    extend: 'Ext.form.Panel',
    xtype: 'probeselector',
    
    requires: [
        'WebclientApp.store.Probes'
    ],
    
    title: 'Probes',
    layout: 'form',

    onFieldChange: function() {
        console.log("onFieldChange", "probechanged triggered");

        this.fireEvent('probechanged', this, this.getForm().getValues());
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
                    bind: '{selectedProbe}'
                },{
                    xtype: 'combobox',
                    queryMode: 'local',
                    fieldLabel: 'Select Probe',
                    displayField: 'alias',
                    valueField: 'probeId',
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
