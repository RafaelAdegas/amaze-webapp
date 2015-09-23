var beaconService = angular.module('znAngularApp.beaconService', ['ngResource']);


beaconService.factory('BeaconFactory', function() {
	var beaconList = [];
	var selectedBeacon = null;
	
	var addBeacon = function(beacon) {
		beaconList.push(beacon);
	};
	
	var setBeacons = function(beacons) {
		beaconList = beacons;
	}
	
	var getBeacons = function() {
		return beaconList;
	};
	
	var selectBeacon = function(beacon) {
		selectedBeacon = beacon;
	}
	
	var getSelectedBeacon = function() {
		return selectedBeacon;
	}
	
	return {
		addBeacon: addBeacon,
		getBeacons: getBeacons,
		setBeacons: setBeacons,
		selectBeacon: selectBeacon,
		getSelectedBeacon: getSelectedBeacon
	};
	
});


beaconService.factory('BeaconsService', function($resource) {
	
	return $resource('rest/beacons/');
});