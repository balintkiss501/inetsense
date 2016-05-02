
Ext.define('WebclientApp.view.main.chart.DateChooser', {
    extend: 'Ext.form.Panel',
    xtype: 'datechooser',

    width: 400,

    bodyPadding: 10,
    layout: 'form',

    title: 'Pick the desired date',

    onFieldChange: function() {
        this.fireEvent('datechanged', this, this.getForm().getRecord().data);
    },

    listeners: {
        afterrender: function() {

            this.getForm().loadRecord(Ext.create('WebclientApp.model.DateEntry', {
                'startDate': '01/10/2003',
                'startTime': null,
                'endDate'  : '12/11/2009',
                'endTime'  : null
            }));
        }
    },

    initComponent: function() {

        Ext.apply(this, {
            items: [ {
                xtype: 'fieldcontainer',
                fieldLabel: 'From',
                combineErrors: true,
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    flex: 1,
                    hideLabel: true,
                    listeners: {
                        scope: this,
                        change: this.onFieldChange
                    }
                },
                items: [{
                    xtype: 'datefield',
                    name: 'startDate',
                    margin: '0 5 0 0'
                    // allowBlank: false
                }, {
                    xtype: 'timefield',
                    name: 'startTime',
                    format: 'H:i'
                }]
            } , {
                xtype: 'fieldcontainer',
                fieldLabel: 'To',
                combineErrors: true,
                msgTarget : 'side',
                layout: 'hbox',
                defaults: {
                    flex: 1,
                    hideLabel: true,
                    listeners: {
                        scope: this,
                        change: this.onFieldChange
                    }
                },
                items: [{
                    xtype: 'datefield',
                    name: 'endDate',
                    margin: '0 5 0 0'
                    // allowBlank: false
                }, {
                    xtype: 'timefield',
                    name: 'endTime',
                    format: 'H:i'
                }]
            } ]
        });

        this.callParent();
    }

});
