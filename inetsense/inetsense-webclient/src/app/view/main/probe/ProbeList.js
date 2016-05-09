Ext.define('WebclientApp.view.main.probe.ProbeList', {
    extend: 'Ext.grid.Panel',
    xtype: 'probelist',

    requires: [
        'WebclientApp.store.Probes'
    ],
    
    title: 'Probes',
    
    initComponent: function(){

        Ext.apply(this,{
            width: 400,
            height: 800,
            store: {
                type: 'probes'
            },
            
            columns:[{
                header: 'Id',
                dataIndex: 'authId',
                flex: 1
            },{
                header: 'Created',
                dataIndex: 'createdOn',
                flex: 2
            }],
            tbar: [{
                text: 'Add Probe',
                scope: this,
                handler: this.onAddClick
            }]
        });

        this.callParent();
    },
    
    onAddClick: function(){
     
        Ext.Ajax.request({
            url: WebclientApp.CONFIG.baseUrl + '/probes',
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            failure: function (response){
                console.log(response);
            }
        });
        this.store.load();
    }

});
