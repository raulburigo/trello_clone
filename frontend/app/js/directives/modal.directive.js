angular
  .module('zeTrello')
  .directive('modalDialog', function() {
    return {
      restrict: 'E',
      scope: {
        show: '=',
        id: '='
      },
      replace: true,
    //   transclude: true,
      link: function(scope, element, attrs) {
        scope.dialogStyle = {};
        if (attrs.width)
          scope.dialogStyle.width = attrs.width;
        if (attrs.height)
          scope.dialogStyle.height = attrs.height;
        scope.hideModal = function() {
          scope.show = false;
        };
      },
      templateUrl: 'templates/modal.tpl.html' // See below
    };
  });