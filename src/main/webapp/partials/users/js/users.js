var usersCtrl = angular.module('usersCtrl', ['ngMaterial', 'ngMessages','md.data.table']);
/* USERS CONTROLLERS */
usersCtrl.controller('UsersCtrl', function ($scope,
											$q,
											$timeout,
											$rootScope,
											$http,
											$location,
											$mdDialog,
											$mdToast,
											UsersFactory,
											UsersService) {
	/* menu disable */
	$rootScope.menuSelected='users';
	$scope.editMode = false;

	$scope.users = UsersFactory.getUsers();
	
	$scope.showEdit = function() {
		if(!$scope.editMode && !$scope.selected) {
			return false;
		}
		if(!$scope.editMode && $scope.selected) {
			return true;
		}
	}
	
	$scope.disableChanges = function() {
		if($scope.editMode !== undefined)
			return false
			
		return $scope.editMode;
	}
	
	$scope.actionAddNew = function() {
		$scope.user = new UsersService();
		$scope.data.secondLocked = false;
		$scope.data.selectedIndex = 1;
		$scope.isNewOperation = true;
		$scope.editMode = true;
		$scope.showPasswordFields = true;
	}
	$scope.actionEdit = function() {
		$scope.editMode = true;
		$scope.data.secondLocked = false;
		$scope.data.selectedIndex = 1;
		$scope.showPasswordFields = false;
	}
	$scope.actionSave = function() {
		$scope.editMode = false;
		if($scope.user.idUser === null || $scope.user.idUser === undefined) {
			$scope.user.$save();
			$scope.users.push($scope.user);
		} else {
			$scope.user = $scope.user.$save();
		}
		$scope.data.secondLocked = true;
		$scope.data.selectedIndex = 0;
		$scope.selected = null;
		$rootScope.showSucessSaveToast('User');
	}
	$scope.actionCancel = function() {
		$scope.editMode = false;
		$scope.data.secondLocked = true;
		$scope.data.selectedIndex = 0;
		$scope.selected = null;
	}
	
	$scope.showPasswordFields = false;
	$scope.changePassword = function() {
		$scope.showPasswordFields = true;
	}
	
	$scope.showSave = function() {
		return ($scope.user.firstName != null && 
				$scope.user.lastName != null &&
				$scope.user.username != null &&
				$scope.user.email != null &&
				(($scope.user.password != null &&
				$scope.user.password2 != null &&
				($scope.user.password === $scope.user.password2) || !$scope.showPasswordFields)) );
	};

	$scope.checkedPasswords = function() {
		if ($scope.user.password === $scope.user.password2 && ($scope.user.password != null && $scope.user.password2 != null)) {
			return true;
		}
		if($scope.user.password !== $scope.user.password2 && ($scope.user.password != null && $scope.user.password2 != null)) {
			return false;
		}
		
		return false;
	}
	
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
    
    /** PASSWORD **/
    $scope.showPasswordChange = function(ev) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.confirm()
              .title('Atenção')
              .content('Realmente deseja alterar a senha?')
              .ariaLabel('Senha')
              .targetEvent(ev)
              .ok('Sim')
              .cancel('Não');
        $mdDialog.show(confirm).then(function() {
          $scope.showPasswordFields = true;
          $scope.user.password = '';
        }, function() {
          console.log('You decided to continue.');
        });
      };
    /** END PASSWORD **/
    
    /** DATA TABLE OPERATIONS */
	  $scope.selected = null;
	  
	  $scope.selectUser = function(user) {
		  if($scope.selected == user) {
			  $scope.selected = null;
		  }
		  else {
			  $scope.selected = user;
			  $scope.user = $scope.selected;
		  }
	  }
	  
	  $scope.query = {
	    order: 'username',
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