Ext.define('WebclientApp.view.main.probe.ProbeList', {
    /*extend: 'Ext.grid.Panel',
    xtype: 'probelist',

    requires: [
        'WebclientApp.store.Probes'
    ],

    title: 'Probes',

    store: {
        type: 'probes'
    },

    columns: [
        { text: 'Identifier',   dataIndex: 'identifier' },
        { text: 'Owner',        dataIndex: 'owner', flex: 1 },
        { text: 'GPS',          dataIndex: 'gps', flex: 1 },
        { text: 'LastSeen',     dataIndex: 'lastSeen', flex: 1 }
    ],

    listeners: {
        select: 'onItemSelected'
    }*/

    
    extend: 'Ext.form.Panel',
    xtype: 'probelist',
    
    requires: [
        'WebclientApp.store.Probes'
    ],
    
    title: 'Probes',
    width: 500,
    layout: 'form',
    viewModel: {},
    
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
