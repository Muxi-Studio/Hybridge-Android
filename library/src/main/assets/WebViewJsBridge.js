
(function(){
	if (window.WebViewJsBridge) {
		return;
	}

	var messagingIframe;
	var sendMessageQueue = [];
	var receiveMessageQueue = [];
	var messageHandlers = {};

	var PROTOCOL_SCHEME = 'bridge:';
	var QUEUE_MESSAGE = '__QUEUE_MESSAGE__/';

	var responseCallbacks = {};
	var uniqueId = 1;

	function _createQueueReadyIframe(doc){
		messagingIframe = doc.createElement('iframe');
		messagingIframe.style.display = 'none';
		doc.documentElement.appendChild(messagingIframe);
	}

	function init(messageHandler){
		if (WebViewJsBridge) {
			throw new Error('WebViewJsBridge.init called twice');
		}
		WebViewJsBridge._messageHandler = messageHandler;
		var receivedMessages = receiveMessageQueue;
		receiveMessageQueue = null;
		for (var i = 0; i < receivedMessages.length; i++) {
			_dispatchMessageFromNative(receivedMessages[i]);
		}
	}

	function send(data,responseCallback){
		_doSend({
			data:data
		},responseCallbacks);
	}

	function _doSend(message,responseCallbacks){
		if (responseCallbacks) {
			var callbackId = 'cbla_' + (uniqueId ++) + '_la' + new Date().getTime();
			responseCallbacks[callbackId] = responseCallbacks;
			message.callbackId = callbackId;
		}

		sendMessageQueue.push(message);
		messageIframe.src = PROTOCOL_SCHEME + "://" + QUEUE_MESSAGE;

	}

	function registerHandler(handlerName,handler){
		messageHandlers[handlerName] = handler;
	}

	function callHandler(handlerName,data,reponseCallback){
		_doSend({
			handlerName: handlerName,
			data:data
		},responseCallbacks);
	}

	function _fetchQueue(){
		var messageQueueString = JSON.stringify(sendMessageQueue);
		sendMessageQueue = [];
		messageIframe.src = PROTOCOL_SCHEME + '://return/_fetchQueue/' + encodeURIComponent(messageQueueString);
	}

	function _dispatchMessageFromNative(messageJSON){
		setTimeout(function(){
			var message = JSON.parse(messageJSON);
			var responseCallback;

			if (message.responseId) {
				responseCallback = responseCallbacks[message.responseId];
				if (!responseCallback) {
					return;
				}
				responseCallback(message.responseData);
			}else{
				if (message.callbackId) {
					var callbackResponseId = message.callbackId;
					responseCallback = function(responseData){
						_doSend({
							responseId:callbackResponseId,
							responseData:responseData
						});
					};
				}

				var handler = WebViewJsBridge._messageHandler;
				if (message.handlerName) {
					handler = messageHandlers[message.handlerName];
				}
				try{
					handler(message.data,responseCallback);

				}catch(exception){
					if (typeof console != 'undefined') {
						console.log("WebViewJsBridge: warning: js handler threw",message,exception);
					}
				}
			}
		});
	}

	function _handleMessageFromNative(messageJSON){
		console.log(messageJSON);
		if (receiveMessageQueue) {
			receiveMessageQueue.push(messageJSON);
		}else{
			_dispatchMessageFromNative(messageJSON);
		}
	}

	var WebViewJsBridge = window.WebViewJsBridge = {
		init: init,
		send: send,
		registerHandler: registerHandler,
		callHandler: callHandler,
		_fetchQueue: _fetchQueue,
		_handleMessageFromNative: _handleMessageFromNative
	};

	var doc = document;
	_createQueueReadyIframe(doc);
	var readyEvent = doc.createEvent('Events');
	readyEvent.initEvent('WebViewJsBridgeReady');
	readyEvent.bridge = WebViewJsBridge;
	doc.dispatchEvent(readyEvent);
})();