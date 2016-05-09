
Ext.define('WebclientApp.Application.CONFIG',{
    singleton: true,
    baseUrl: 'http://localhost:8080'
});


Ext.define('WebclientApp.view.main.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'app-main',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        // 'WebclientApp.view.main.probe.ProbeSelector',

        'WebclientApp.view.main.MainController',
        'WebclientApp.view.main.MainModel'
    ],

    controller: 'main',
    viewModel: 'main',

    ui: 'navigation',

    tabBarHeaderPosition: 1,
    titleRotation: 0,
    tabRotation: 0,

    header: {
        layout: {
            align: 'stretchmax'
        },
        title: {
            bind: {
                text: '{name}'
            },
            flex: 0
        },
        iconCls: 'fa-th-list'
    },

    tabBar: {
        flex: 1,
        layout: {
            align: 'stretch',
            overflowHandler: 'none'
        }
    },

    responsiveConfig: {
        tall: {
            headerPosition: 'top'
        },
        wide: {
            headerPosition: 'left'
        }
    },

    defaults: {
        bodyPadding: 20,
        tabConfig: {
            plugins: 'responsive',
            responsiveConfig: {
                wide: {
                    iconAlign: 'left',
                    textAlign: 'left'
                },
                tall: {
                    iconAlign: 'top',
                    textAlign: 'center',
                    width: 120
                }
            }
        }
    },

    items: [{
        title: 'Home',
        iconCls: 'fa-home',
        html: [
            '<p height="300px"><center><font size="80"><b>iNETSense</b></font></center></p>',
            '<p vertical-align="middle" padding="40px"><center><font size="40">iNETSense az internet sávszélességének mérésére.</font></center></p>',
            '<a href="https://github.com/zdtorok/inetsense/wiki/Webclient-Felhaszn%C3%A1l%C3%B3i-dokument%C3%A1ci%C3%B3"><center>User Guide</center></a>'
        ]
    }, {
        title: 'Probe selector',
        iconCls: 'fa-cog',
        items: [{
            xtype: 'probelist'
        }]
    },{
        title: 'Statistic',
        iconCls: 'fa-cog',
        items: [{
            xtype: 'stock-chart-panel'
        }]
    }]

});
