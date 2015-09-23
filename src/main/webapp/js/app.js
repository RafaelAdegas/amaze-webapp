var app = angular.module('znAngularApp', ['ngRoute', 
                                        'ngCookies',
                                        'ngMaterial',
                                        'znAngularApp.directives',
                                        'znAngularApp.services',
                                        'homeCtrl',
                                        'znAngularApp.establishmentService',
                                        'beaconsCtrl',
                                        'znAngularApp.beaconService',
                                        'usersCtrl',
                                        'znAngularApp.userService'
                                        ]);
app.controller('AppCtrl', function ($scope,
		$http,
		$location,
		$mdSidenav,
		$mdDialog,
		$mdToast,
		$window,
		$rootScope) {

	$scope.toggleSidenav = function(menuId) {
		$mdSidenav(menuId).toggle();
	};
	
	$rootScope.menuSelected = 'dashboard';

	$rootScope.redirect = function(url, refresh) {
	    if(refresh || $scope.$$phase) {
	    	$location.path(url);
	    } else {
	        $location.path(url);
	        $scope.$apply();
	    }
	}
	
	$rootScope.showSucessSaveToast = function(phrase) {
	  $mdToast.show(
	    $mdToast.simple()
	      .content((phrase ? phrase : 'Registro') + ' alterado com sucesso!')
	      .position($rootScope.getToastPosition())
	      .hideDelay(5000)
	  );
	};	
	$rootScope.showCancelSaveToast = function() {
      $mdToast.show(
        $mdToast.simple()
          .content('Alteração cancelada!')
          .position($rootScope.getToastPosition())
          .hideDelay(5000)
      );
    };

    $scope.showConfirmLogout = function(ev) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.confirm()
              .title('Atenção')
              .content('Deseja realmente sair?')
              .ariaLabel('Lucky day')
              .targetEvent(ev)
              .ok('Sim')
              .cancel('Não');
        $mdDialog.show(confirm).then(function() {
          $rootScope.logout();
        }, function() {
          console.log('You decided to continue.');
        });
      };
	
});
	
app.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', '$compileProvider', '$provide',
		  function($routeProvider, $locationProvider, $httpProvider, $compileProvider, $provide) {
			
			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: LoginController
			});
	
			$routeProvider.when('/dashboard', {
				templateUrl:'partials/dashboard.html',
				controller: 'HomeCtrl'
				
			});
			
			$routeProvider.when('/beacons', {
				templateUrl: 'partials/beacons/index.html',
				controller: 'BeaconsCtrl'
			});
			
			$routeProvider.when('/users', {
				templateUrl: 'partials/users/index.html',
				controller: 'UsersCtrl'
			});
			
			$routeProvider.otherwise({
				templateUrl:'partials/home.html',
				controller: 'HomeCtrl'
			});
			
			$locationProvider.hashPrefix('!');
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		if(config !== undefined) {
			        			var method = config.method;
			        			var url = config.url;
			        		}
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (znAngularAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    })
		    
		   
		}]).run(function($rootScope, $location, $cookieStore, $mdToast, UserService) {
			
			/* Reset error when a new view is loaded */
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});
			
			/**
			 * Messages Toast.
			 */
				var last = {
			      bottom: false,
			      top: true,
			      left: false,
			      right: true
			    };
				$rootScope.toastPosition = angular.extend({},last);
				$rootScope.getToastPosition = function() {
				  sanitizePosition();
				  return Object.keys($rootScope.toastPosition)
				    .filter(function(pos) { return $rootScope.toastPosition[pos]; })
				    .join(' ');
				};
				function sanitizePosition() {
				  var current = $rootScope.toastPosition;
				  if ( current.bottom && last.top ) current.top = false;
				  if ( current.top && last.bottom ) current.bottom = false;
				  if ( current.right && last.left ) current.left = false;
				  if ( current.left && last.right ) current.right = false;
				  last = angular.extend({},current);
				}
			/** end Messages Toast*/
			
			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $rootScope.authToken;
				$cookieStore.remove('authToken');
				$location.path("/login");
			};
			
			 /* Try getting valid user from cookie or go to login page */
			var originalPath = $location.path();
			$location.path("/login");
			var authToken = $cookieStore.get('authToken');
			if (authToken !== undefined) {
				$rootScope.authToken = authToken;
				UserService.get(function(user) {
					$rootScope.user = user;
					$location.path(originalPath);
				});
			}
			$rootScope.menuSelected = 'dashboard';
			$rootScope.initialized = true;
	});

function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = false;
	
	$scope.login = function() {
		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
				if(authenticationResult.status) {
					$rootScope.messageInvalidUser = true;
				} else {
					$rootScope.messageInvalidUser = false;
					var authToken = authenticationResult.token;
					$rootScope.authToken = authToken;
					if ($scope.rememberMe) {
						$cookieStore.put('authToken', authToken);
					}
					UserService.get(function(user) {
						$rootScope.user = user;
						$location.path("/");
					});
					$rootScope.menuSelected = 'dashboard';
				}
		});
	};
};