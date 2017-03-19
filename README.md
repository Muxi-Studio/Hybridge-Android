# YAJB-Android
Yet Another JavaScript Bridge, Android version.
  
Use with [YAJB-JavaScript](https://github.com/Muxi-Studio/YAJB-JavaScript)

## Install

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
        //register handler to handle event from web
        webView.register("onBtnClick", new BridgeHandler() {
            @Override
            public void handle(String data) {
                Gson gson = new Gson();
                IntData intData = gson.fromJson(data,IntData.class);
                Toast.makeText(MainActivity.this,"id:" + intData.id,Toast.LENGTH_LONG).show();
            }
        });

        webView.loadUrl("file:///android_asset/demo.html");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send event to web, invoke yajb.trigger('{"event":"onNativeButtonClick","data":{...}');
                webView.send("onNativeButtonClick","message from java");

            }
        });
    }
```

