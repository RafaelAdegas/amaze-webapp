var beaconsCtrl = angular.module('beaconsCtrl', ['ngMaterial', 'ngMessages','md.data.table']);
/* BEACON CONTROLLERS */
beaconsCtrl.controller('BeaconsCtrl', function ($scope,
											$q,
											$timeout,
											$rootScope,
											$http,
											$location,
											$mdDialog,
											$mdToast,
											BeaconsService,
											BeaconFactory,
											EstablishmentFactory) {
	/* menu disable */
	$rootScope.menuSelected='beacons';
	
	if(BeaconFactory.getBeacons().length <= 0) {
		BeaconFactory.setBeacons(BeaconsService.query());
		console.log(BeaconFactory.getBeacons());
	}
	
	/* button toolbar actions */
		/* toast save popup show */
	    $scope.showConfirmReturn = function(ev) {
	        // Appending dialog to document.body to cover sidenav in docs app
	        var confirm = $mdDialog.confirm()
	              .title('Deseja retornar para a tela de pesquisa?')
	              .content('Todos os seus dados serão perdidos.')
	              .ariaLabel('Lucky day')
	              .targetEvent(ev)
	              .ok('Sim')
	              .cancel('Não');
	        $mdDialog.show(confirm).then(function() {
	          $scope.operationScreen='formBeacon';
	        }, function() {
	          console.log('You decided to keep your debt.');
	        });
	      };
	    
	/* end toast save popup show */
	$scope.editMode = false;
	$scope.isNewOperation = false;
	
	$scope.showEdit = function() {
		if(!$scope.editMode && !$scope.selected) {
			return false;
		}
		if(!$scope.editMode && $scope.selected) {
			return true;
		}
	}
	
	$scope.actionAddNew = function() {
		$scope.beacon = new BeaconsService();
		$scope.data.secondLocked = false;
		$scope.data.selectedIndex = 1;
		$scope.isNewOperation = true;
		$scope.editMode = true;
	}
	$scope.actionEdit = function() {
		$scope.editMode = true;
		$scope.data.secondLocked = false;
		$scope.data.selectedIndex = 1;
	}
	$scope.actionSave = function() {
		$scope.editMode = false;
		$scope.beacon = $scope.beacon.$save(function(){
			$scope.data.secondLocked = true;
			$scope.data.selectedIndex = 0;
			$scope.selected = null;
			$rootScope.showSucessSaveToast('Beacon');
			$scope.isNewOperation = false;
		});
	}
	$scope.actionCancel = function() {
		$scope.editMode = false;
		$scope.data.secondLocked = true;
		$scope.data.selectedIndex = 0;
		$scope.selected = null;
	}
	/* --end button toolbar actions */
	
	/** TABS */
	$scope.data = {
      selectedIndex: 0,
      secondLocked:  true,
      thirdLocked:   true,
      forthLocked:   true,
      bottom:        false
    };
    $scope.next = function() {
      $scope.data.selectedIndex = Math.min($scope.data.selectedIndex + 1, 2) ;
    };

    $scope.previous = function() {
      $scope.data.selectedIndex = Math.max($scope.data.selectedIndex - 1, 0);
    };
 /** ./END TABS */
    
/** OPERATIONS */
    $scope.beacons = BeaconFactory.getBeacons();

/** ./END OPERATIONS */

/** DATA TABLE OPERATIONS */
	  $scope.selected = null;
	  
	  $scope.selectBeacon = function(beacon) {
		  if($scope.selected == beacon) {
			  $scope.selected = null;
		  }
		  else {
			  $scope.selected = beacon;
			  $scope.beacon = $scope.selected;
		  }
	  }
	  
	  $scope.query = {
	    order: 'minor',
	    limit: 5,
	    page: 1
	  };
	  
	  $scope.onpagechange = function(page, limit) {
	    var deferred = $q.defer();
	    
	    $timeout(function () {
	      deferred.resolve();
	    }, 2000);
	    
	    return deferred.promise;
	  };
	  
	  $scope.onorderchange = function(order) {
	    var deferred = $q.defer();
	    
	    $timeout(function () {
	      deferred.resolve();
	    }, 2000);
	    
	    return deferred.promise;
	  };
/** ./END DATA TABLE CONTROLLERS **/
	
});