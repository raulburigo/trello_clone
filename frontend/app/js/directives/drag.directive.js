angular
  .module('zeTrello')
  .directive('dragMe', dragMe)
  .directive('dropOnMe', dropOnMe);

dragMe.$inject = [];

function dragMe() {
  var DDO = {
    restrict: 'A',
    link: function(scope, element, attrs) {
      scope.$root.overElId = null;
      element.prop('draggable', true);
      element.on('dragstart', function(event) {
        event.dataTransfer.setData("text", attrs.id)
        element.addClass("bg-secondary")
      });
      element.on('dragend', function(event) {
        element.removeClass("bg-secondary")
      });
      element.on('dragover', function(event) {
        event.preventDefault();
        scope.$root.overElId = attrs.id;
      })
    }
  };
  return DDO;
}

dropOnMe.$inject = [];
function dropOnMe() {
  var DDO = {
    restrict: 'A',
    link: function(scope, element, attrs) {
      element.on('dragover', function(event) {
        event.preventDefault();
      });
      element.on('drop', function(event) {
        event.preventDefault();
        var dropTarget = null;
        var nextTarget = event.target;
        while (!dropTarget) {
          if ("drop-on-me" in nextTarget.attributes) {
            dropTarget = nextTarget;
          } else {
            nextTarget = nextTarget.parentElement;
          }
        }
        var overElId = scope.$root.overElId;
        var draggedElId = event.dataTransfer.getData("text");
        var categoryId = attrs.id.split("-")[1];
        var todoId = draggedElId.split("-")[1]
        var beforeEl = document.getElementById(overElId);
        var dragEl = document.getElementById(draggedElId);
        if (beforeEl.parentNode != dropTarget) {
          beforeEl = document.getElementById("button-cat-" + categoryId);
        }
        dropTarget.insertBefore(dragEl, beforeEl)
        console.log("API CALL", categoryId, "/", todoId)
        // API call
      });
    }
  };
  return DDO;
}