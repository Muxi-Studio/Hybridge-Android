package com.muxistudio.jsbridge.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.muxistudio.jsbridge.BridgeHandler;
import com.muxistudio.jsbridge.BridgeWebView;
import com.tencent.smtt.sdk.WebSettings;


public class MainActivity extends Activity {

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
        webView.register("onBtnClick", new BridgeHandler() {
            @Override
            public void handle(String data) {
                Gson gson = new Gson();
                WebData webData = gson.fromJson(data,WebData.class);
                Toast.makeText(MainActivity.this,"id:" + webData.id,Toast.LENGTH_LONG).show();
            }
        });
        webView.loadUrl("file:///android_asset/demo.html");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.send("onNativeButtonClick","message from java");
            }
        });

    }

}