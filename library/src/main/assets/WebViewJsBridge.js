//hybrid://callJava:111/method?{}

(function(){

	if(window.JsBridge){
		return;
	}
	
	var id = 0;
	var handlerName = {};
	var callbacks = {};

	function registerJsHandler(methodName,handler){
		jsFunctions[method] = handler;
	}

//data 为函数的传入参数
function callJava(method,data,callback){
	var callbackId;
	var callJavaUrl;
	var params;
	params.data = data;
	if(callback){
		callbackId = id++;
		callbacks[callbackId] = callback;
		params.callbackId = callbackId;
	}
}

function send(method,params){
	var callJavaUrl = 'hybrid://callJava/' + method + '?' + JSON.stringify(params);
	location.assign(callJavaUrl);
}

function handleMsgFromNative(msgJSON){
	var msg = JSON.parse(msgJSON);
	if(msg.responseId){
		var method = callbacks[responseId];
		if(method){
			method(msgJSON.responseData);
		}
		delete callbacks[responseId];
		return;
	}
	if(msg.callbackId){
		var method = msg.method;
		var data = msg.data;
		var callbackFunction = function(responseData){
			send('callback',{
				responseId: msg.callbackId,
				responseData: responseData
			})
		}
		try{
			handlerName[method](data,callbackFunction);					
		}catch(exception){
			console.log(js can not handle method from java + 'exception');
		}
	}else{
		try{
			handlerName[msg.method](data);
		}catch(exception){
			console.log(js can not handle method from java + 'exception');
		}
	}
}

var JsBridge = window.JsBridge = {
	callJava: callJava,
	handleMsgFromNative: handleMsgFromNative
};

}());