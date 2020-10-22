angular.module("zeTrello", []);

angular.module("zeTrello")
    .controller("zeTrelloCtrl", function($scope, todosAPI) {

        $scope.categories = [
            {
                id: 1,
                name: "todo",
                boardPosition: 1,
                todos: [
                    {
                        completed: false,
                        createdAt: "2020-10-21T23:24:24.763946",
                        id: 2,
                        title: "Teste id 2",
                        updatedAt: "2020-10-22T00:03:07.873563"
                    },
                    {
                        completed: false,
                        createdAt: "2020-10-21T23:24:25.282821",
                        id: 3,
                        title: "Teste id 3",
                        updatedAt: "2020-10-22T00:20:27.514914"
                    }
                ]
            },
            {
                id: 2,
                name: "done",
                boardPosition: 2,
                todos: [
                    {
                        completed: true,
                        createdAt: "2020-10-21T23:24:24.268696",
                        id: 1,
                        title: "Teste id 1",
                        updatedAt: "2020-10-22T00:01:12.192022"
                    }
                ]
            }
        ]

        $scope.modalShown = false;
        $scope.toggleModal = function(id) {
            $scope.modalShown = !$scope.modalShown;
            console.log(id)
            $scope.id = id
        };

    });