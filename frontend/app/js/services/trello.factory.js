(function () {
    "use strict";

    angular.module('zeTrello')
        // faltou o $inject aqui pq deu pau...
        .factory('trelloFactory', function ($rootScope) {
        
            function _createMessage(type, message) {
                // $rootScope.messages = $rootScope.messages || [];
                // $rootScope.messages.push(
                //     {timestamp: new Date(), color: type, text: message}
                // )
                console.log("envia mensagem")
            }
            
            return {
                createMessage: _createMessage,
            }
        })

})();
