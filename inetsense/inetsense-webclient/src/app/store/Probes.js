Ext.define('WebclientApp.store.Probes', {
    extend: 'Ext.data.Store',

    alias: 'store.probes',

    fields: [
        'authId', 'createdOn'
    ],

    model: 'WebclientApp.model.Probe',
    
    autoLoad: true,

    // server
    proxy: {
        type: 'rest',
        url : WebclientApp.Application.CONFIG.baseUrl + '/probes'
    }

});
