angular
  .module('zeTrello')
  .directive('dragMe', dragMe)
  .directive('dropOnMe', dropOnMe);

dragMe.$inject = ['$timeout'];

function dragMe($timeout) {
  var DDO = {
    restrict: 'A',
    link: function(scope, element, attrs) {
      element.prop('draggable', true);
      element.on('dragstart', function(event) {
        event.dataTransfer.setData("text", attrs.id)
        $timeout(function() {
          element.addClass("d-none");
        }, 0);
        event.stopPropagation();
      });
      element.on('dragend', function(event) {
        element.removeClass("d-none")
      });
      element.on('dragenter', function(event) {
        event.preventDefault();
        scope.lastEnter = event.target
        scope.$root.overElId = attrs.id;
      });
      element.on('dragleave', function(event) {
        event.preventDefault();
        if (scope.lastEnter === event.target) {
          scope.$root.overElId = null;
        }
      });
    }
  };
  return DDO;
}

dropOnMe.$inject = ['todosAPI'];
function dropOnMe(todosAPI) {
  var DDO = {
    restrict: 'A',
    scope: {
      cat: '='
    },
    link: function(scope, element, attrs) {
      scope.placeholder = document.createElement("DIV");
      scope.placeholder.classList.add("placeholder");
      scope.removePlaceholders = function () {
        Array.from(document.getElementsByClassName("placeholder"))
          .forEach(function (el) {
            el.remove()
          })
      }
      element.on('dragover', function(event) {
        event.preventDefault();
      });
      element.on('dragenter', function(event) {
        event.preventDefault();
        if (scope.$root.overElId) {
          element[0].insertBefore(
            scope.placeholder, document.getElementById(scope.$root.overElId)
            )
          } else {
            element[0].appendChild(scope.placeholder)
        }
        scope.lastEnter = event.target
      });
      element.on('dragleave', function(event) {
        event.preventDefault();
        if (scope.lastEnter === event.target) {
          scope.removePlaceholders();
        }
      });
      element.on('drop', function(event) {
        event.preventDefault();
        var draggedElId = event.dataTransfer.getData("text");
        var dragEl = document.getElementById(draggedElId);
        element[0].insertBefore(dragEl, scope.placeholder)
        scope.removePlaceholders();
        // essa parte não se aproveita
        var newPostion = Array.from(element[0].children).indexOf(scope.placeholder) - 1;
        var categoryId = scope.cat.id;
        var todoId = draggedElId.split("-")[1]
        todosAPI.updateTodo(todoId, 
          {
            "categoryId": categoryId,
            "position": newPostion,
          }
        )
        scope.$root.overElId = null;
      });
    }
  };
  return DDO;
}