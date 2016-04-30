Ext.define('WebclientApp.view.main.probe.ProbeList', {
    extend: 'Ext.grid.Panel',
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
    }

});
