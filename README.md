# YAJB-Android

[![](https://jitpack.io/v/Muxi-Studio/YAJB-Android.svg)](https://jitpack.io/#Muxi-Studio/YAJB-Android)

Yet Another JavaScript Bridge, Android version.
  
Use with [YAJB-JavaScript](https://github.com/Muxi-Studio/YAJB-JavaScript)

## Install

### Gradle

Step 1. Add the JitPack repository to your build file

```gradle

//Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```gradle
	dependencies {
	        compile 'com.github.Muxi-Studio:YAJB-Android:1.0'
	}
```

## Usage

- in xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.muxistudio.jsbridge.example.MainActivity">

    <com.muxistudio.jsbridge.BridgeWebView
        android:id="@+id/web_view"
        android:layout_width="match_parent"
        android:layout_above="@+id/btn"
        android:layout_height="match_parent"/>

    <Button
        android:id="@+id/btn"
        android:layout_width="match_parent"
        android:layout_alignParentBottom="true"
        android:layout_height="48dp"
        android:text="send event to web"/>

</RelativeLayout>

```

- in Java

```java
    private BridgeWebView webView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (BridgeWebView) findViewById(R.id.web_view);
        button = (Button) findViewById(R.id.btn);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);

        //inject initialized data,just normal string or java object, not json!
        webView.setInitData("init data from native");

        //register handler to handle event from web
        webView.register("click", new BridgeHandler() {
            @Override
            public void handle(String data,CallbackFunc cb) {
                Log.d("jsbridge", data);

                //return result to web should call cb.onCallback()
                String result = "msg from native";
                cb.onCallback(result);
            }
        });

        webView.loadUrl("file:///android_asset/demo.html");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send event to web, invoke yajb.trigger('{"event":"onNativeButtonClick","data":{...}');
                webView.send("emit", "native msg", new BridgeHandler() {
                      @Override
                      public void handle(String data, CallbackFunc cb) {

                          //do what want to do with return data from web.
                          Log.d("jsbridge", data);
                      }
                });

//                or just send simple params.
//                webView.send("emit",2);
//                webView.send("emit",true);
//                webView.send("emit",4.5);
//                webView.send("emit","native message");


            }
        });
    }
```

