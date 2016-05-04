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
    // viewModel: {},

    initComponent: function() {

        console.log("helloworld>>>>>>>>");

        /*
        Ext.regModel('Probesx', {
            fields: [
                {type: 'string', name: 'identifier'}
            ]
        });

        // The data store holding the states
        var store = Ext.create('Ext.data.Store', {
            model: 'Probesx',
            data: [
                { identifier: "PROBE001" },
                { identifier: "PROBE002" },
                { identifier: "PROBE003" },
                { identifier: "PROBE004" }
            ]
        });
        */

        var data = [{
            name: 'Tom',
            age: 20
        }, {
            name: 'Peter',
            age: 30
        }];

        var store = Ext.create('Ext.data.Store', {
            fields: ['name', 'age'],
            proxy: {
                type: 'memory',
                reader: {
                    type: 'json'
                }
            }
        });

    // loadRawData happens at an other place in the application
    // only here for the example
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
                    /// reference: 'probes',
                    // publishes: 'value',
                    queryMode: 'local',
                    fieldLabel: 'Select Probe',
                    displayField: 'name',
                    valueField: 'age',
                    // anchor: '-15',
                    store: store,
                    // minChars: 0,
                    //queryMode: 'local',
                    // typeAhead: true
                }]
            }]
        });

    
        this.callParent();
    }

});
