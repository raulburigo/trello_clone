(function () {
    "use strict";

    angular.module('zeTrello')
        .config(function ($routeProvider, $locationProvider) {
            $locationProvider.hashPrefix('');

            $routeProvider
            .when('/', {
                templateUrl: 'templates/dashboard.tpl.html',
                controller:  "zeTrelloCtrl",
                resolve: {
                    getCategories: function (todosAPI) {
                        return todosAPI.getAllCategories()
                    },
                    getModalTodo: function () {
                        return null;
                    }
                }
            })  
            // .when('/:id', {
            //     templateUrl: 'templates/dashboard.tpl.html',
            //     controller:  "zeTrelloCtrl",
            //     resolve: {
            //         getCategories: function (todosAPI) {
            //             return todosAPI.getAllCategories()
            //         },
            //         getModalTodo: function (todosAPI, $route) {
            //             return todosAPI.getTodoById($route.current.params.id);
            //         }
            //     }
            // })  
            // .otherwise({
            //     redirectTo:'/404'
            // });
        })    
    
})();