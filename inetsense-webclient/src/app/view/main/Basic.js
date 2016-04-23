/**
 * The Basic Column Chart displays a set of random data in a column series. The "Reload Data"
 * button will randomly generate a new set of data in the store.
 *
 * Tapping or hovering a column will highlight it.
 * Dragging a column will change the underlying data.
 * Try dragging below 65 degrees Fahrenheit.
 */
Ext.define('KitchenSink.view.charts.column.Basic', {
    extend: 'Ext.Panel',
    xtype: 'column-basic',
    controller: 'column-basic',

    layout: 'fit',

    items: [{
        xtype: 'panel',
        title: 'p0013 <i>(jeanluc)</i>',
        layout: 'fit',
        monitorResize: true,
        html: '<div id="highchart-container2" style="min-width: 600px; height: 400px; margin: 0 auto"></div>'
    }]

});