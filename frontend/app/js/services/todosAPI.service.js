(function () {
    "use strict";

    angular.module('zeTrello')
        .service('todosAPI', function ($http, constants, trelloFactory) {
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

            this.getTodoById = function (id) {
                return this.lookUp('get', `/todos/${id}`)
            }
            
            this.updateTodo = function (todoId, todo) {
                return this.lookUp('put', `/todos/${todoId}/update`, todo)
            }

            this.createTodo = function (todo) {
                return this.lookUp('post', '/todos', todo)
            }

            this.createCategory = function (category) {
                return this.lookUp('post', '/categories', category)
            }
            
            this.deleteTodo = function (id) {
                return this.lookUp('delete', `/todos/${id}/delete`)
            }

            this.deleteCategory = function (id) {
                return this.lookUp('delete', `/categories/${id}/delete`)
            }

        })
})();