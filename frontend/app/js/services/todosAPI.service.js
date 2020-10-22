(function () {
    "use strict";

    angular.module('zeTrello')
        .service('todosAPI', function ($http, constants) {
            this.lookUp = function (method, url='', data) {
                return $http({
                    method: method.toUpperCase(),
                    url: constants.TODOS_BASE_URL + url,
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

            // this.createRestaurant = function (restaurant) {
            //     return this.yelpLookUp('post', '', restaurant)
            // }

            // this.deleteRestaurant = function (id) {
            //     return this.yelpLookUp('delete', `/${id}/`)
            // }

            // this.getRestaurantById = function (id) {
            //     return this.yelpLookUp('get', `/${id}/`)
            // }
            
            // this.updateRestaurant = function (id, data) {
            //     return this.yelpLookUp('put', `/${id}/`, data)
            // }

            // this.addReview = function (id, review) {
            //     return this.yelpLookUp('post', `/${id}/addreview`, review)
            // }
            
        })
})();