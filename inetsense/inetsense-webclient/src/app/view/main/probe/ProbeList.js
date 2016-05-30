Ext.define('WebclientApp.view.main.probe.ProbeList', {
    extend: 'Ext.grid.Panel',
    xtype: 'probelist',

/*
    requires: [
        'WebclientApp.store.Probes'
    ],    
*/

    title: 'Probes',
    
    initComponent: function(){
    
        var store = new WebclientApp.store.Probes();
        var height = (Ext.getBody().getViewSize().height)-50;
        var width = (Ext.getBody().getViewSize().width);
        
        Ext.apply(this,{
            xtype: 'spreadsheet',
            width: width,
            height: height,
            store: store,
            listeners: {
              selectionchange: this.onSelectionChange 
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
        var $scope = this;
        Ext.MessageBox.prompt('Id','Please enter a Probe Id:', function(btn, text){
            if(btn === 'ok'){
                console.log(text);
                 Ext.Ajax.request({
                    url: WebclientApp.CONFIG.baseUrl + '/probes?authId='+text,
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    failure: function (response){
                        console.log(response);
                    }
                });
                $scope.getStore().load();
            }
        }, this);
    
        this.getStore().load();
        
    },
    
    onSelectionChange: function( grid, selection){
      if(selection.length>0){
          var selectedProbe = selection[0].getData().authId;
          var $scope = this;
            Ext.MessageBox.prompt('Change Id', 'Please enter a new Probe Id',function(btn,text){
               if(btn === 'ok'){
                   Ext.Ajax.request({
                        url: WebclientApp.CONFIG.baseUrl + '/probes?authId='+selectedProbe+'&newId='+text,
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        succes: function(response){
                          console.log('succ: '+response);  
                        },
                        failure: function (response){
                            console.log(response);
                        }
                    });
                    $scope.getStore().load();
               } 
            }, this);
            //this.getStore().load();
        
      }
    }
    

});
