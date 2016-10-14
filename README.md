# YAJB-Android
Yet Another JavaScript Bridge, Android version.


## How to use?

- use it in Java

```
webView.callJs("functionInJs", "data", new CallbackFunc() {
	@Override
	public void callback(String data) {
		// do what yout want to do with response data
		Toast.makeText(MainActivity.this,data,Toast.LENGTH_LONG).show();
	}
});

});
webView.registerHandler("functionInJava", new BridgeHandler() {
	@Override
	public void handle(String data, CallbackFunc cb) {
		Toast.makeText(MainActivity.this,data,Toast.LENGTH_LONG).show();
		cb.callback("native has receive " + data);
	}
});
```

- use it in Js

```
//js call java
JsBridge.callJava('functionInJava',data,function(response){
	//do what you want to do with reponseData
	document.getElementById('msg_from_java').innerHTML = 'response from Native:' + response;
});
    
//js register function for java to invoke
JsBridge.registerJsHandler('functionInJs',function(data){
   document.getElementById('msg_in_js').innerHTML = 'show message from Native:' + data;
});
```