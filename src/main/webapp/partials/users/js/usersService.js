var userService = angular.module('znAngularApp.userService', ['ngResource']);


userService.factory('UsersFactory', function($rootScope, UsersService) {
	var userList = [];
	var selectedBeacon = null;
	
	var addUser = function(user) {
		userList.push(user);
	};
	
	var setUsers = function(users) {
		userList = users;
	}
	
	var getUsers = function() {
		userList = UsersService.query();

		return userList;
	};
	
	var selectUser = function(user) {
		selectedUser = user;
	}
	
	var getSelectedUser = function() {
		return selectedUser;
	}
	
	return {
		addUser: addUser,
		getUsers: getUsers,
		setUsers: setUsers,
		selectUser: selectUser,
		getSelectedUser: getSelectedUser
	};
	
});

userService.factory('ValidateUserService', function($resource) {
	
	return $resource('rest/users/:action', {},
			{
				validate: {
					method: 'POST',
					params: {'action' : 'validate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}
			}
		);
});

userService.factory('UsersService', function($resource) {
	
	return $resource('rest/users/:idUser', {idUser: '@idUser'});
});