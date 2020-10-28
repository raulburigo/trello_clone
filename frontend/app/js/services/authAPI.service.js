(function () {
    "use strict";

    angular.module('zeTrello')
        .service('todosAPI', function ($http, constants) {
            this.lookUp = function (method, url='', data) {
                return $http({
                    method: method.toUpperCase(),
                    url: constants.AUTH_BASE_URL + url,
                    data: data
                })
                .then(function (results) {
                    return results;
                })
                .catch(function (e) {
                    console.log(e);
                    return
                });
            }

            this.getAllCategories = function () {
                return this.lookUp('get', '/categories')
            }

        })
})();