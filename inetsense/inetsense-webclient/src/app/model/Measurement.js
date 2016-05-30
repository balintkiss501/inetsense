Ext.define('WebclientApp.model.Measurement',{
    extend: 'Ext.data.Model',
    
    alias: 'model.measurement',
    
    fields:[
        {name: 'completedOn', type: 'date', dateformat: 'Y-m-d H:i:s'},
        {name: 'lat', type: 'float'},
        {name: 'lng', type: 'float' },
        {name: 'downloadSpeed'},
        {name: 'uploadSpeed'}
    ]

});