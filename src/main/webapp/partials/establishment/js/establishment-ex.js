var establishmentCtrl = angular.module('establishmentCtrl', ['ngMaterial', 'ngMessages']);
/* BEACON CONTROLLERS */
establishmentCtrl.controller('EstablishmentCtrl', function ($scope,
											$rootScope,
											$http,
											$location,
											$mdDialog,
											$mdToast,
											EstablishmentService,
											EstablishmentFactory) {
	/* menu disable */
	$rootScope.menuSelected='establishment';
	
	$scope.establishment = EstablishmentFactory.getCurrentEstablishment();
	
	/* button toolbar actions */
		/* toast save popup show */
		$rootScope.showSucessSaveToast = function() {
		  $mdToast.show(
		    $mdToast.simple()
		      .content('Registro alterado com sucesso!')
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
	/* end toast save popup show */
	$scope.editMode = false;
	
	$scope.editEstablishment = null;
	$scope.actionEdit = function() {
		$scope.editEstablishment = JSON.parse(JSON.stringify($scope.establishment));
		$scope.editMode = true;
	}
	$scope.actionSave = function() {
		$scope.establishment.$save(function(){
			$scope.showSucessSaveToast();
			$scope.editMode = false;
		})
	}
	$scope.actionCancel = function() {
		$scope.establishment.shortName = $scope.editEstablishment.shortName;
		$scope.establishment.fullName = $scope.editEstablishment.fullName;
		$scope.establishment.phone = $scope.editEstablishment.phone;
		$scope.establishment.cnpj = $scope.editEstablishment.cnpj;
		$scope.establishment.address = $scope.editEstablishment.address;
		$scope.establishment.city = $scope.editEstablishment.city;
		$scope.establishment.state = $scope.editEstablishment.state;
		$scope.establishment.cep = $scope.editEstablishment.cep;
		$scope.establishment.complement = $scope.editEstablishment.complement;
		$scope.showCancelSaveToast();
		$scope.editMode = false;
	}
	/* --end button toolbar actions */
});