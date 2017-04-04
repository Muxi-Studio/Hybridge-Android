package com.muxistudio.jsbridge.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.muxistudio.jsbridge.BridgeHandler;
import com.muxistudio.jsbridge.BridgeWebView;
import com.muxistudio.jsbridge.CallbackFunc;
import com.tencent.smtt.sdk.WebSettings;


public class MainActivity extends Activity {

    private BridgeWebView webView;
    private Button button;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (BridgeWebView) findViewById(R.id.web_view);
        button = (Button) findViewById(R.id.btn);
        btn3 = (Button) findViewById(R.id.btn3);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/demo.html");

        //js call java
        webView.register("click", new BridgeHandler() {
            @Override
            public void handle(String data, CallbackFunc cb) {
                Log.d("jsbridge", data);

                cb.onCallback("msg from native");
            }
        });

        //java call js
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.send("emit", "native msg", new BridgeHandler() {
                    @Override
                    public void handle(String data, CallbackFunc cb) {
                        Log.d("jsbridge", data);
                    }
                });

                //or you can send with simple params. serialize to string like {"event":"emit","data":2}
//                webView.send("emit",2);
//                webView.send("emit",true);
//                webView.send("emit",4.5);
//                webView.send("emit","native message");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });
    }

}