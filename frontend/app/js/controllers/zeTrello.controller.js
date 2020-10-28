angular.module("zeTrello")
    .controller("zeTrelloCtrl", function($scope, $rootScope, todosAPI, getCategories) {

        $rootScope.userProfile = window._keycloak

        $scope.loginBtn = function () {
            window._keycloak.login();
        }

        $scope.registerBtn = function () {
            window._keycloak.login({action: 'register'});
        }

        $scope.logoutBtn = function () {
            window._keycloak.logout();
        }

        $scope.categories = getCategories.data;

        $scope.showNewTodoForm = {}

        $scope.newTodo = '';

        $scope.toggleNewTodoForm = function (categoryId) {
            if ($scope.showNewTodoForm[categoryId]) {
                $scope.showNewTodoForm[categoryId] = false;
            } else {
                $scope.showNewTodoForm = {}
                $scope.showNewTodoForm[categoryId] = true;
            }
        }

        $scope.state = {
            modalTodo: null
        }

        $scope.modalShown = false;
        $scope.toggleModal = function(todo) {
            $scope.modalShown = !$scope.modalShown;
            $scope.state.modalTodo = todo
        };

        $scope.addTodo = function (newTodo, categoryId) {
            const todoToCreate = {
                title: newTodo,
                categoryId
            }
            todosAPI.createTodo(todoToCreate)
                .then(function (result) {
                    console.log(result.data)
                    let todoCreated = result.data;
                    for (cat of $scope.categories) {
                        if (cat.id == categoryId)
                            cat.todos.push(todoCreated)
                    }
                    delete $rootScope.newTodo
                })
                .catch(function (err) {
                    console.log(err)
                })
        }

        $scope.handleDeleteTodoBtn = function (id) {
            $scope.modalShown = false;
        }

        $scope.addCategory = function (newCategory) {
            const categoryToCreate = {
                name: newCategory
            }
            todosAPI.createCategory(categoryToCreate)
                .then(function (result) {
                    let createdCategory = result.data;
                    createdCategory.todos = []
                    $scope.categories.push(createdCategory)
                    $scope.showNewCatForm = false;
                    delete $scope.newCategory
                })
        }

        $scope.teste = function () {
            console.log("checked")
        }
    });