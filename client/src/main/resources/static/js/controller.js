var app = angular.module('clientApp', ['ngRoute', 'ngCookies']);

app.config(function($routeProvider) {
    // Routes
    $routeProvider
    .when('/', {
        resolve: {
            "check": function($location, $cookies) {
                if($cookies.loggedIn == "true") {
                    $location.path('/upload');
                }
            }
        },
        templateUrl: 'login.html'
    })
    .when('/upload', {
        resolve: {
            "check": function($location, $cookies) {
                if($cookies.loggedIn == "false") {
                    $location.path('/');
                }
            }
        },
        templateUrl: 'upload.html'
    })
    .when('/login', {
        resolve: {
            "check": function($location, $cookies) {
                if($cookies.loggedIn == "true") {
                    $location.path('/logout');
                }
            }
        },
        templateUrl: 'login.html'
    })
    .when('/logout', {
        resolve: {
            "check": function($location, $cookies) {
                if($cookies.loggedIn == "false") {
                    $location.path('/login');
                }
            }
        },
        templateUrl: 'logout.html'
    })
    .otherwise({
        redirectTo: '/'
    });
});

app.controller('loginController', function($scope, $location, $rootScope, $cookies, $http) {
    // Submit zip file
    $scope.submit = function() {
        if($cookies.loggedIn == "true") {
            $location.path('/upload');
        }

        var data = {
            username : $scope.username,
            password : $scope.password
        };

        var res = $http.post("/check", data);
        res.success(function(data, status, headers, config) {
            if(data.result == "true") {
                $cookies.loggedIn = true;
                $location.path('/upload');
            } else {
                alert("Please check you credentials!");
            }
        });
    }

    $scope.logout = function() {
        if($cookies.loggedIn == "true") {
            $cookies.loggedIn = false;
        }
        $location.path('/login');
    }
})

app.directive('fileModel', ['$parse', function ($parse) {
            return {
               restrict: 'A',
               link: function(scope, element, attrs) {
                  var model = $parse(attrs.fileModel);
                  var modelSetter = model.assign;

                  element.bind('change', function(){
                     scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                     });
                  });
               }
            };
         }]);

app.controller('uploadController', function($scope, $location, $rootScope, $cookies, $http) {
    // Upload file to server
    $scope.uploadFile = function() {
        var data = new FormData();
        data.append('file', $scope.zip);
        $scope.isloading = true;
        $http.post("/uploadFile", data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(data, status){
            $rootScope.result = [];
            for(var i=0;i<data.result.length;i++) {
              $rootScope.result.push(data.result[i]);
            }
            $scope.isloading = false;
        })
        .error(function(data, status){
              $rootScope.result = [];
              for(var i=0;i<data.result.length;i++) {
                $rootScope.result.push(data.result[i]);
              }
              $scope.isloading = false;
          });
    }
})