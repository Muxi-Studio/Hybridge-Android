package com.muxistudio.jsbridge.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.muxistudio.jsbridge.BridgeHandler;
import com.muxistudio.jsbridge.BridgeWebView;
import com.muxistudio.jsbridge.CallbackFunc;
import com.tencent.smtt.sdk.WebSettings;

import java.util.ArrayList;
import java.util.logging.Logger;


public class MainActivity extends Activity {

    private BridgeWebView webView;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (BridgeWebView) findViewById(R.id.web_view);
        button = (Button) findViewById(R.id.btn);
        button2 = (Button) findViewById(R.id.btn2);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        WebData webData = new WebData();
        webData.lists = new ArrayList<>();
        WebData.TransData data = new WebData.TransData();
        data.time = "2017-03-31 19:42:44";
        data.transMoney = "2.9";
        webData.lists.add(data);

        WebData.TransData data1 = new WebData.TransData();
        data1.time = "2017-03-30 18:33:44";
        data1.transMoney = "18";
        webData.lists.add(data1);
        webView.setInitData(webData);
//        webView.loadUrl("1.1.167:3000");
        webView.loadUrl("file:///android_asset/demo.html");

        webView.register("click1", new BridgeHandler() {
            @Override
            public void handle(String data, CallbackFunc cb) {
                Log.d("jsbridge",data);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://10.1.1.167:3000");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.send("invokeweb","msg from native");
            }
        });

    }

}