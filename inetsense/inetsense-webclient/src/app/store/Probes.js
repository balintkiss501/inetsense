Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.Store',

    alias: 'store.probes',

    fields: [
        'identifier', 'owner', 'gps', 'lastSeen'
    ],

    data: { items: [
        { identifier: 'p0012', owner: 'jeanluc',    gps: "41.40338, 2.17403", lastSeen: "2016-04-27T10:22:37Z" },
        { identifier: 'p0013', owner: 'jeanluc',    gps: "41.42338, 2.17403", lastSeen: "2016-04-27T10:22:37Z" },
        { identifier: 'p0020', owner: 'data',       gps: "41.46338, 2.17403", lastSeen: "2016-02-27T10:22:37Z" },
        { identifier: 'p0021', owner: 'data',       gps: "41.48338, 2.17403", lastSeen: "2016-02-27T10:22:37Z" }
    ]},

    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }
});
