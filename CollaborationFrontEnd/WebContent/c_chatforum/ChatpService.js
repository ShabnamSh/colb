app.service("ChatpService", function($q, $timeout,$rootScope) {
	console.log('ChatpService')
	var service = {}
	var listener = $q.defer()
	var socket = {
		client : null,
		stomp : null
	}
	
	
	var messageIds = [];

	service.RECONNECT_TIMEOUT = 30000;
	service.SOCKET_URL = "/ColaborativeController/chatp";
	service.CHAT_TOPIC = "/queue/message"+$rootScope.currentUser.userid;
	service.CHAT_BROKER = "/app/chatp";

	service.receive = function() { 
		console.log("receive")
		console.log("listener.promise:" + listener.promise)
		return listener.promise;
	};

	service.send = function(message) { 
		console.log("send")
		var id = Math.floor(Math.random() * 1000);

		socket.stomp.send(service.CHAT_BROKER, { 
			priority : 9
		}, JSON.stringify({
			message : message,
			userid : $rootScope.currentUser.userid,
			useridd : $rootScope.useridd,
	        id : id
			
		}));
		console.log("message: " + message.message)
		console.log("id : " + id)
		messageIds.push(id);
	};

	var reconnect = function() {
		console.log("reconnect")
		$timeout(function() { 
			initialize();
		}, this.RECONNECT_TIMEOUT); 
	};

	var getMessage = function(data) {
		console.log("getMessage")
		console.log("data:" + data)
		var message = JSON.parse(data)
		var out = {};
		
		out.message = message.message;
		out.time = new Date(message.time);
		
		  //message.userid=$rootScope.currentUser.userid;
		out.useridd =message.useridd;
	    out.friendid=message.friendid;
		console.log("data:" + data)
		console.log("message:" + message.message)
		console.log("time :" + message.time)
		console.log("userID :" + message.useridd)

		return out;
	};
	
	var startListener = function() {
		console.log("startListener")
		socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
			listener.notify(getMessage(data.body));
		});
	};

	var initialize = function() {
		console.log("initialize")
		socket.client = new SockJS(service.SOCKET_URL);
		socket.stomp = Stomp.over(socket.client);
		socket.stomp.connect({}, startListener); 
		socket.stomp.onclose = reconnect; 
	};

	initialize(); 
	return service;
});

