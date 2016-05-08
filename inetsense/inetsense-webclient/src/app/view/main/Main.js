Ext.define('WebclientApp.view.main.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'app-main',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        'WebclientApp.view.main.chart.Chart',
        'WebclientApp.view.main.chart.ChartController',

        'WebclientApp.view.main.probe.ProbeList',
        'WebclientApp.view.main.users.UserList',

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
    }, /*{
        title: 'Users',
        iconCls: 'fa-user',
        items: [{
            xtype: 'userlist'
        }]
    },*/ {
        title: 'Probes',
        iconCls: 'fa-cog',
        items: [{
            xtype: 'probelist'
        }]
    },
    {
        title: 'Bandwidth statistic',
        iconCls: 'fa-cog',
        items: [{
            xtype: 'chart-panel',
            hcContainer: 'highchart-container'
        }]
    } /*{
        title: 'DEMO Bandwidth statistic',
        iconCls: 'fa-cog',
        items: [{
            xtype: 'chart-panel',
            demo: true,
            hcContainer: 'highchart-container-demo'
        }]
    }*/
    ]

});
