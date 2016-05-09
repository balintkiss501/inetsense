Ext.define('WebclientApp.view.main.chart.ProbeChooser',{
    extend: 'Ext.form.Panel',
    xtype: 'probechooser',
    
    requires: [
        'WebclientApp.store.Probes'
    ],
    
    title: 'Probes',
    width: 400,
    layout: 'form',
    viewModel: {},
    
    onSelected: function(combo, record, index){
        this.fireEvent('selected',this,record.get(combo.valueField));
    },
    
 
    initComponent: function(){
    
        var $scope = this;
        
        Ext.apply(this, {
            items: [{
                xtype: 'fieldset',
                layout: 'anchor',
                items: [{
                    xtype: 'combobox',
                    queryMode: 'local',
                    fieldLabel: 'Select Probe',
                    displayField: 'authId',
                    valueField: 'authId',
                    store: {
                        type: 'probes'
                    },
                    listeners:{
                        render: function(combobox){
                           this.getStore().load();
                        },
                        select: function(combobox,record,index){
                          $scope.onSelected(combobox, record, index);
                        }
                    }

                }]
            }]
        });
        this.callParent();
    }
    
    
    
    
    
    
    
});