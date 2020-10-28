angular
  .module('zeTrello')
  .directive('modalDialog', modalDialog);
  
modalDialog.$inject = ['todosAPI'];

function modalDialog(todosAPI) {
  return {
    restrict: 'E',
    scope: {
      show: '=',
      todo: '='
    },
    replace: true,
    link: function(scope, element, attrs) {
      scope.dialogStyle = {};
      if (attrs.width)
        scope.dialogStyle.width = attrs.width;
      if (attrs.height)
        scope.dialogStyle.height = attrs.height;
      scope.hideModal = function() {
        scope.show = false;
      };
      scope.handleDeleteTodoBtn = function (id) {
        todosAPI.deleteTodo(id)
          .then(function () {
            scope.hideModal();
          })
      };
      scope.checkCompleted = function () {
        todosAPI.updateTodo(scope.todo.id, { completed: scope.todo.completed })
          .then(function (result) {
            scope.todo.activities = result.data.activities
          })
      };
    },
    templateUrl: 'templates/modal.tpl.html'
  
  };
};