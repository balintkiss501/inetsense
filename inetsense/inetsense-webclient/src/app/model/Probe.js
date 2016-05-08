Ext.define('WebclientApp.model.Probe', {
    extend: 'Ext.data.Model',

    alias: 'model.probe',

    fields: [
        { name: 'authId', type: 'string', useNull: true },
        'createdOn'
    ]

});
