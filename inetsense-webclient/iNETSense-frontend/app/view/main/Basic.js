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

    width: 650,
    height: 500,

    tbar: [
        '->',
        /*{
            text: 'Preview',
            platformConfig: {
                desktop: {
                    text: 'Download'
                }
            },
            handler: 'onDownload'
        },*/
        {
            text: 'Reload Data',
            handler: 'onReloadData'
        }
    ],

    items: {
        xtype: 'cartesian',
        reference: 'chart',
        // renderTo: Ext.get('body'),
        store: {
            type: 'climate'
        },
        insetPadding: {
            top: 40,
            bottom: 40,
            left: 20,
            right: 40
        },
        interactions: {
            type: 'itemedit',
            tooltip: {
                renderer: 'onEditTipRender'
            }
            // ,
            // renderer: 'onColumnEdit'
        },
        axes: [ {
            type: 'numeric',
            position: 'left',
            minimum: 30,
            titleMargin: 20,
            title: {
                text: 'Bandwidth'
            },
            listeners: {
                rangechange: 'onAxisRangeChange'
            }
        }, {
            type: 'category',
            position: 'bottom'
        } ],
        animation: Ext.isIE8 ? false : true,
        series: {
            type: 'bar',
            xField: 'month',
            yField: 'highF',
            style: {
                minGapWidth: 20
            },
            highlight: {
                strokeStyle: 'black',
                fillStyle: 'gold'
            },
            label: {
                field: 'highF',
                display: 'insideEnd',
                renderer: 'onSeriesLabelRender'
            }
        },
        sprites: {
            type: 'text',
            text: 'Bandwidth statistic',
            fontSize: 22,
            width: 100,
            height: 30,
            x: 40, // the sprite x position
            y: 20  // the sprite y position
        },
        listeners: {
            afterrender: 'onAfterRender',
            beginitemedit: 'onBeginItemEdit',
            enditemedit: 'onEndItemEdit'
        }
    }

});