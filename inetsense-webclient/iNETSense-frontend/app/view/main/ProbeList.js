/**
 * This view is an example list of people.
 */
Ext.define('TutorialApp.view.main.ProbeList', {
    extend: 'Ext.grid.Panel',
    xtype: 'probelist',

    requires: [
        'TutorialApp.store.Probes'
    ],

    title: 'Probes',

    store: {
        type: 'probes'
    },

    // 'identifier', 'owner', 'gps', 'lastSeen'

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
