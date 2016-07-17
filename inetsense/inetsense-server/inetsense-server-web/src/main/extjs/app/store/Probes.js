Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.Store',

    alias: 'store.probes',
    storeId: 'probes',

    requires: [
        'WebclientApp.model.Probe'
    ],

    fields: [
        'authId', 'createdOn'
    ],

    model: 'WebclientApp.model.Probe',
    
    autoLoad: true,


    // server
    proxy: {
        type: 'rest',
        url : WebclientApp.CONFIG.baseUrl + '/probes'
    }

});
