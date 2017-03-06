var app=angular.module('myApp',['ngRoute','ngCookies']);
app.config(function($routeProvider){
	$routeProvider
	.when('/',{
		templateUrl:'c_home/home.html'
	})
	.when('/login', {
		templateUrl : 'c_user/login.html',
		controller : 'UserController'
	})
	.when('/search', {
		templateUrl : 'c_friend/listfriend.html',
		controller : 'UserController'
	})
	
	.when('/register', {
		templateUrl : 'c_user/register.html',
		controller : 'UserController'
	})
	.when('/homme',{
		templateUrl:'c_home/homme.html'
	})
	
	.otherwise({
		redirectTo : '/'
	});

});
app.run(function($rootScope,$location,$cookieStore,$http){

	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 
		 console.log("$locationChangeStart")
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
	     
		 var userPages = ['/myProfile','/create_blog','/add_friend','/search_friend','/homme','/view_friend', '/viewFriendRequest','/chat','/search']
		 var adminPages = ["/post_job","/manage_users"]
		 
		 var currentPage = $location.path()
		 
		 var isUserPage = $.inArray(currentPage, userPages) ===0;
		 var isAdminPage = $.inArray(currentPage, adminPages) ===0;
		 
		 var isLoggedIn = $rootScope.currentUser.userid;
	        
	     console.log("isLoggedIn:" +isLoggedIn)
	     console.log("isUserPage:" +isUserPage)
	     console.log("isAdminPage:" +isAdminPage)
	        
	        if(!isLoggedIn)
	        	{
	        	
	        	 if (isUserPage || isAdminPage) {
		        	  console.log("Navigating to login page:")
		        	  alert("You need to loggin to do this operation")

						            $location.path('/');
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 
				 if(isAdminPage && role!='admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/homme');
					 
					 }
				     
	        	
	        	}
	        
	 }
	       );
	 
	 
	 // keep user logged in after page refresh
    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
    if ($rootScope.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser; 
    }

});

