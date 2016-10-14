(function(){

	if(window.JsBridge){
		console.log('jsBridge has existed');
		return;
	}
	
	console.log('jsbridge init');

	var id = 1;
	var handlerName = {};
	var callbacks = {};

	function registerJsHandler(method,handler){
		handlerName[method] = handler;
	}

	function callJava(method,data,callback){
		var callbackId;
		var callJavaUrl;
		var params = {};
		params.data = data;
		if(callback){
			callbackId = '' + id++;
			callbacks[callbackId] = callback;
			params.callbackId = callbackId;
		}
		send(method,params);
	}

	function send(method,params){
		var callJavaUrl = 'hybrid://jsbridge/' + method + '?' + JSON.stringify(params);
		location.assign(callJavaUrl);
	}

	function handleMsgFromNative(msgJSON){
		console.log(msgJSON);
		console.log('handle msg from native');
		var msg = JSON.parse(msgJSON);
		console.log(msg);
		console.log(handlerName);
		if(msg.responseId){
			var method = callbacks[msg.responseId];
			if(method){
				method(msg.responseData);
			}
			delete callbacks[msg.responseId];
			return;
		}
		var data = msg.data;
		if(msg.callbackId){
			var callbackFunction = function(responseData){
				send('callback',{
					responseId: msg.callbackId,
					responseData: responseData
				});
			};
			handlerName[msg.method](data,callbackFunction);
		}else{
			try{
				handlerName[msg.method](data);
			}catch(exception){
				console.log('js can not handle method from java' + exception);
			}
		}
	}


	/*对外暴露的方法*/
	var JsBridge = window.JsBridge = {
		callJava: callJava,
		handleMsgFromNative: handleMsgFromNative,
		registerJsHandler: registerJsHandler,
	};

	/*创建 bridge 初始化事件及分发*/
	var bridgeInitEvent = new Event('bridgeInit');
	document.dispatchEvent(bridgeInitEvent);

}());