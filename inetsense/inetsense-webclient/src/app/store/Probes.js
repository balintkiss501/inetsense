Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.ArrayStore',

    alias: 'store.probes',

    fields: [
        'identifier'
    ],
    


    //model: 'WebclientApp.model.Probe',
    storeId: 'probes',
    
    data: { items: [
        { identifier: "PROBE001" },
        { identifier: "PROBE002" },
        { identifier: "PROBE003" },
        { identifier: "PROBE004" }
    ]},
    
   proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }
    
});
