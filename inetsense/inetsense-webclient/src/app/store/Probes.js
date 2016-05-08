Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.Store',

    alias: 'store.probes',

    fields: [
        'alias', 'probeId'
    ],
    
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
    }
    
});
