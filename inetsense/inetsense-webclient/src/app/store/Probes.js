Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.Store',

    alias: 'store.probes',

    fields: [
        'authId', 'createdOn'
    ],

    mode: 'probe',
    

    // demo
    /*
    data: {
        items: [{
            alias: 'Egyetem',
            probeId: 'PROBE001'
        }, {
            alias: 'Otthoni mobil',
            probeId: 'PROBE002'
        }]
    },
    
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }*/

    autoLoad: true,
    // autoSync: true,

    // server
    proxy: {
        type: 'rest',
        url : 'http://localhost:8080/probes'
    }
    
});
