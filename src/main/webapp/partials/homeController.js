var homeCtrl = angular.module('homeCtrl',['ngMaterial','chart.js']);
/* BEACON CONTROLLERS */
homeCtrl.controller('HomeCtrl', function (  $scope,
											$http,
											$rootScope,
											$location) {
	console.log("pelo menos chamar, chamou");
	$scope.labels = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho"];
	  $scope.series = ['Beacon', 'Beacon'];
	  $scope.data = [
	    [65, 59, 80, 81, 56, 55, 40],
	    [40, 55, 56, 81, 80, 59, 65]
	  ];
	  $scope.colours = ["blue"];
	  $scope.onClick = function (points, evt) {
	    console.log(points, evt);
	  };
	  
	$scope.labels0 = ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabádo"];
	  $scope.series0 = ['Acesso dos últimos 7 dias'];
	  $scope.data0 = [
	    [70, 34, 29, 35, 49, 55, 92]
	  ];
	  $scope.colours1 = ["red"];
	  $scope.onClick = function (points, evt) {
	    console.log(points, evt);
	  };
	  
	$scope.labels1 = ["08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"];
	  $scope.series1 = ['Horário de acessos'];
	  $scope.data1 = [
	    [01, 03, 10, 32, 49, 55, 63, 57, 68, 79, 82, 55, 46, 34, 21]
	  ];
	  $scope.colours1 = ["red"];
	  $scope.onClick = function (points, evt) {
	    console.log(points, evt);
	  };
	  
	  
	  $scope.labels2 = ["Download Sales", "In-Store Sales", "Mail-Order Sales", "Tele Sales", "Corporate Sales"];
	    $scope.data2 = [300, 500, 100, 40, 120];
	    $scope.type = 'PolarArea';

	    $scope.toggle2 = function () {
	      $scope.type = $scope.type === 'PolarArea' ?
	        'Pie' : 'PolarArea';
	    };
	
	    $scope.labels3 = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
	    $scope.data3 = [300, 500, 100];
	    
	    $scope.labels4 =["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"];

	    $scope.data4 = [
	      [65, 59, 90, 81, 56, 55, 40],
	      [28, 48, 40, 19, 96, 27, 100]
	    ];
	    
	    $scope.labels5 = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
	    $scope.data5 = [300, 500, 100];
});