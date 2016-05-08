Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.ArrayStore',

    alias: 'store.probes',

    autoLoad: true,
    autoSync: true,
    model: 'WebclientApp.model.ProbeModel',
    proxy: {
        type: 'rest',
        url: 'http://localhost:8080/probe',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
    
});
