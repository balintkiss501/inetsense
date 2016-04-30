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
        iconCls: 'fa-home'
    }, {
        title: 'Users',
        iconCls: 'fa-user',
        items: [{
            xtype: 'userlist'
        }]
    },  {
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
            xtype: 'chart-panel'
        }]
    }]

});
