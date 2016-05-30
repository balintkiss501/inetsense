Ext.define('WebclientApp.model.Probe', {
    extend: 'Ext.data.Model',

    alias: 'model.probe',

    fields: [
        {name: 'authId', type: 'string' },
        {name: 'createdOn',type:'string', convert: function(v,record){
            return Ext.date.Format(new Date(v),'Y/m/d');
        } }
    ]

});