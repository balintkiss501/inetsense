
/**
 * The Basic Column Chart displays a set of random data in a column series. The "Reload Data"
 * button will randomly generate a new set of data in the store.
 *
 * Tapping or hovering a column will highlight it.
 * Dragging a column will change the underlying data.
 * Try dragging below 65 degrees Fahrenheit.
 */
Ext.define('HighChart', {
    extend: 'Ext.Panel',
    xtype: 'highchart-panel',
    controller: 'highchart',

    layout: 'fit',

//    width: 650,
//    height: 500,

    items: [{
        xtype: 'panel',
        title: 'Hello',
        width: 650,
        layout: 'fit',
        html: '<div id="my-highchart-container"></div>'
    }]

});