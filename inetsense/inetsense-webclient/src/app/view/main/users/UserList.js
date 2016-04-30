Ext.define('WebclientApp.view.main.users.UserList', {
    extend: 'Ext.grid.Panel',
    xtype: 'userlist',

    requires: [
        'WebclientApp.store.Users'
    ],

    title: 'Users',

    store: {
        type: 'users'
    },

    columns: [
        { text: 'Name',  dataIndex: 'name' },
        { text: 'Phone', dataIndex: 'username', flex: 1 },
        { text: 'Email', dataIndex: 'email', flex: 1 },
        { text: 'Phone', dataIndex: 'phone', flex: 1 }
    ],

    listeners: {
        select: 'onItemSelected'
    }
});
