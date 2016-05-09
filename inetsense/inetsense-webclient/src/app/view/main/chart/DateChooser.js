Ext.define('WebclientApp.view.main.chart.DateChooser', {
    extend: 'Ext.form.Panel',
    xtype: 'datechooser',

    bodyPadding: 10,
    layout: 'form',

    myrec: null,

    onFieldChange: function() {

        var values = this.getForm().getValues();

        if(values.startDate === undefined || values.startDate == ""
            || values.endDate === undefined || values.endDate == ""){
            return;
        }

        this.fireEvent('datechanged', this, values);
    },

    listeners: {
        afterrender: function() {

            this.myrec = this.getForm().loadRecord(Ext.create('WebclientApp.model.DateEntry', {
                'startDate': '2016-04-15',
                'endDate'  : new Date().toISOString().slice(0,10),
                'startTime': '',
                'endTime'  : ''
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
                    margin: '0 5 0 0',
                    format: 'Y-m-d'
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
                    margin: '0 5 0 0',
                    format: 'Y-m-d'
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
