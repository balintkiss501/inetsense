Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.Store',

    alias: 'store.probes',

    fields: [
        'name', 'age'
    ],
    
    data: {
        items: [{
            name: 'Tom',
            age: 20
        }, {
            name: 'Peter',
            age: 30
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
