angular.module("zeTrello", ['angularjs-autogrow', 'ngRoute']);

angular.element(document).ready(function () {
    window._keycloak = Keycloak({
        "auth-server-url": "http://127.0.0.1:8180/auth/",
        "realm": "zetrello",
        "clientId": "angularjs-frontend",
        "public-client": true,
        "ssl-required": "external",
      });

    window._keycloak.init({
        // onLoad: 'login-required',
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/silent-check-sso.html',
        pkceMethod: 'S256',
        enableLogging: true
    })
        .then(function (authenticated) {
            if(authenticated) {
                window._keycloak.loadUserProfile()
                    .then(function(profile){
                        angular.bootstrap(document, ['zeTrello']);
                    })
                    .catch(function(err) {
                        console.log(err)
                    });
            }
            else {
                console.log("sem user")
                angular.bootstrap(document, ['zeTrello']);
            }
        })
});

angular.module("zeTrello")
    .config(['$httpProvider', function($httpProvider) {
        var token = window._keycloak.token;
        if (token)
            $httpProvider.defaults.headers.common['Authorization'] = 'BEARER ' + token;
    }]);



// export TKN=$(curl -X POST 'http://localhost:8180/auth/realms/master/protocol/openid-connect/token' \
//  -H "Content-Type: application/x-www-form-urlencoded" \
//  -d "username=admin" \
//  -d 'password=Pa55w0rd' \
//  -d 'grant_type=password' \
//  -d 'client_id=admin-cli' | jq -r '.access_token')

// curl -X GET 'http://localhost:8180/auth/admin/realms/zetrello/users' \
// -H "Accept: application/json" \
// -H "Authorization: Bearer $TKN" | jq .
