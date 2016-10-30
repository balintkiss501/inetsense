'use strict';
/**
 * @ngdoc overview
 * @name inetsense
 * @description
 * # inetsense
 *
 * Main module of the application.
 */
angular.module('inetsense', [
        'oc.lazyLoad',
        'ui.router',
        'ui.bootstrap',
        'angular-loading-bar'
    ])
    .config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $httpProvider) {

        $ocLazyLoadProvider.config({
            debug : true,
            events : true,
        });

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/login');

        $stateProvider
            .state('dashboard', {
                controller : 'DashboardController as controller',
                url : '/dashboard',
                templateUrl : 'app/dashboard/dashboard.html',
                resolve : {
                    loadMyFiles : function($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name : 'dashboard',
                            files : [
                                'app/dashboard/dashboard.controller.js'
                            ]
                        })
                    }
                }
            })
            .state('login', {
                controller : 'LoginController as controller',
                url : '/login',
                templateUrl : 'app/login/login.html',
                resolve : {
                    loadMyFiles : function($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name : 'login',
                            files : [
                                'app/login/login.controller.js'
                            ]
                        })
                    }
                }
            });
    });
