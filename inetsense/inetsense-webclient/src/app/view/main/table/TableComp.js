Ext.define('WebclientApp.view.main.table.TableComp', {
    extend: 'Ext.grid.Panel',
    xtype: 'measurementtable',
    
    probeId: null,
    store: null,
    dateFrom: new Date('2016-04-15').getTime(),
    dateTo: new Date().getTime(),
    mode: 0,
    agg: 'avg',
    
    updateStore: function(evt){

        this.getStore().getProxy().setUrl(WebclientApp.CONFIG.baseUrl+'/measurements/table/'+this.probeId+'/from/'+this.dateFrom+'/to/'+this.dateTo+'/res/'+this.mode+'/'+this.agg);
        this.getStore().load();
        console.log(this.getStore().getProxy().getUrl());
    },
   
    initComponent: function(){
        var $scope = this;
        
        this.store = Ext.create('Ext.data.Store',{
            model: 'WebclientApp.model.Measurement',
            autoLoad: false,
            proxy: {
                type: 'rest',
                url: 'http://localhost:8080/measurements/table/PROBE001/from/1461535200000/to/1464559140000/resolution/1/avg'
                }
        });
        var height = (Ext.getBody().getViewSize().height)-500;
        var width = Ext.getBody().getViewSize().width;
        Ext.apply(this,{
           width: width,
           height: height,
           store: this.store,
           columns:[{
               header: 'Date',
               dataIndex: 'completedOn',
               flex: 1,
               renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')
           },{
               header: 'Download',
               dataIndex: 'downloadSpeed',
               flex: 2,
               renderer: function(value){
                   if(value==(-1)){
                       return 'null';
                   }else{
                       return value;
                   }
               }
           },{
               header: 'Upload',
               dataIndex: 'uploadSpeed',
               flex: 3,
               renderer: function(value){
                   if(value==(-1)){
                       return 'null';
                   }else{
                       return value;
                   }
               }
           }],
           tbar:[{
                text: 'Raw',
                scope: this,
                handler: function(){
                    this.mode =0; 
                }
           },{
               text: '24h',
               scope: this,
               handler: function(){
                   this.mode = 24;
               }
           },{
               text: '12h',
               scope: this,
               handler: function(){
                   this.mode = 12;
               }
           },{
               text: '4h',
               scope: this,
               handler: function() {
                   this.mode = 4;
               }
           },{
               text: '1h',
               scope: this,
               handler: function(){
                   this.mode = 1;
               }
           },{
               text: '10 min',
               scope: this,
               handler: function(){
                   this.mode = 10;
               }
           },{
               text: 'Min',
               scope: this,
               handler: function() {
                   this.agg = 'min';
               }
           },{
               text: 'Avg',
               scope: this,
               handler: function(){
                   this.agg = 'avg';
               }
           },{
               text: 'Max',
               scope: this,
               handler: function(){
                   this.agg = 'max';
               }
           }]
        });
        
        this.callParent();
    }


});
