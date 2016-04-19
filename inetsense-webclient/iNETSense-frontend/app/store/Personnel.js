Ext.define('TutorialApp.store.Personnel', {
    extend: 'Ext.data.Store',

    alias: 'store.personnel',

    fields: [
        'name', 'email', 'username', 'phone'
    ],

    data: { items: [
        { name: 'Jean Luc', username: 'jeanluc',  email: "jeanluc.picard@enterprise.com", phone: "555-111-1111" },
        { name: 'Worf',     username: 'worf',     email: "worf.moghsson@enterprise.com",  phone: "555-222-2222" },
        { name: 'Deanna',   username: 'deanna',   email: "deanna.troi@enterprise.com",    phone: "555-333-3333" },
        { name: 'Data',     username: 'data',     email: "mr.data@enterprise.com",        phone: "555-444-4444" }
    ]},

    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }
});
