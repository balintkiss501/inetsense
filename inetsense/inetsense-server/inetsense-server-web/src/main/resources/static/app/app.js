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
    'ui.bootstrap'
]).config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $httpProvider) {

    $ocLazyLoadProvider.config({
        debug : false,
        events : true,
    });

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    $urlRouterProvider.otherwise('/login');

    $stateProvider
        .state('login', {
            controller : 'LoginController as controller',
            url : '/login',
            templateUrl : 'app/login/login.html',
            resolve : {
                loadMyFiles : function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name : 'inetsense',
                        files : [
                            'app/login/login.controller.js'
                        ]
                    });
                }
            }
        }).state('dashboard', {
            controller : 'DashboardController as ctrl',
            url : '/dashboard',
            templateUrl : 'app/dashboard/dashboard.html',
            resolve : {
                loadMyFiles : function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name : 'inetsense',
                        files : [
                            'app/dashboard/dashboard.controller.js'
                        ]
                    });
                }
            }
        }).state('dashboard.probes', {
            url : '/probes',
            templateUrl : 'app/dashboard/probes/probes.html',
            controller : 'ProbesController as ctrl',
            resolve : {
                loadMyFiles : function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name : 'inetsense',
                        files : [
                            'app/dashboard/probes/probes.controller.js'
                        ]
                    });
                }
            }
        }).state('dashboard.measurements', {
            url : '/measurements',
            templateUrl : 'app/dashboard/measurements/measurements.html',
            controller : 'MeasurementsController as ctrl',
            resolve : {
                loadMyFiles : function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name : 'inetsense',
                        files : [
                            'app/dashboard/measurements/measurements.controller.js'
                        ]
                    });
                }
            }
        });
});
