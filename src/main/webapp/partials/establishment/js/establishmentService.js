var establishmentService = angular.module('znAngularApp.establishmentService', ['ngResource']);


establishmentService.factory('EstablishmentFactory', function(	$rootScope,
																EstablishmentService) {
	
	var getCurrentEstablishment = function() {
		if($rootScope.establishment == null) {
			$rootScope.establishment = EstablishmentService.get({userName: $rootScope.user.username});
		}
		return $rootScope.establishment;
	};
	
	return {
		getCurrentEstablishment: getCurrentEstablishment
	};
	
});

establishmentService.factory('EstablishmentService', function($resource) {
	
	return $resource('rest/establishments/:userName', {userName: '@userName'});
});