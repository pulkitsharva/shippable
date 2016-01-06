var personControllers = angular.module('personControllers', []);

personControllers.controller('Controller1', function ($scope,$http) {
	console.log("In controller");
	var urlBase="http://localhost:8080/SpringAngularMaven";
	//$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
	
	
	$scope.getPerson = function getPerson() {
		console.log("data:"+$scope.searchName);
		$http.get(urlBase+'/api/getSelected/'+$scope.searchName).
		success(function(data) {
	        $scope.tasks = data;
	        console.log(data);
	        $scope.users = data;
	        
		});
	};
	
	$scope.getAllPerson = function getAllPerson() {
		console.log("sending request");
		$http.get(urlBase+'/api/getAll').
		success(function(data) {
	        $scope.tasks = data;
	        console.log(data);
	        $scope.users = data;
	        
		}).error(function(data, status, headers, config) {
	        console.log('error: data = ' , data);
	      });
	};
	
	$scope.createPerson = function createPerson() {
		$http.post(urlBase+'/api/create',{
			"name": $scope.name
		}).
		success(function(data) {
			console.log(data);
	        $scope.newUser = data.name;
	        $http.get(urlBase+'/api/getAll').
			success(function(data) {
		        $scope.tasks = data;
		        console.log(data);
		        $scope.users = data;
		        
			}).error(function(data, status, headers, config) {
		        console.log('error: data = ' , data);
		      });
	    })
	    .error(function(data, status, headers, config) {
	        console.log('error: data = ' , data);
	      });
	};
	
});	


