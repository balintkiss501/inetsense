Ext.define('WebclientApp.view.main.probe.ProbeList', {
    extend: 'Ext.grid.Panel',
    xtype: 'probelist',

    requires: [
        'WebclientApp.store.Probes'
    ],

    title: 'Probes',

    initComponent: function(){
        console.log('helloworld>>>>>>>>');
        
        Ext.apply(this,{
            width: 200,
            store: {
                type: 'probes'
            },
            
        columns:[{
            header: 'Id',
            dataIndex: 'identifier',
            flex: 1
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
            url: '/probe',
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            succes: function (response){
                console.log('s' +response);
            },
            failure: function (response){
                console.log(response);
            }
        });
    
       
       $.getJSON('http://localhost:8080/probe', function (data) {
            dLen = data.length;
          for (i=0; i<dLen; i++){
              console.log(data[i]);
          }
          var x = data[0];
          console.log(x.authId);
        });
       
    }
    
    
});
