package com.muxistudio.jsbridge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muxistudio.library.BridgeHandler;
import com.muxistudio.library.BridgeWebView;
import com.muxistudio.library.CallbackFunc;
import com.tencent.smtt.sdk.WebSettings;

public class MainActivity extends Activity {

    BridgeWebView webView;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (BridgeWebView) findViewById(R.id.web_view);
        button = (Button) findViewById(R.id.btn);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        webView.loadUrl("file:///android_asset/demo.html");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.callJs("showUser", null, new CallbackFunc() {
                    @Override
                    public void callback(String data) {
                        Toast.makeText(MainActivity.this,data,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        webView.registerHandler("toast", new BridgeHandler() {
            @Override
            public void handle(String data, CallbackFunc cb) {
                Toast.makeText(MainActivity.this,data,Toast.LENGTH_LONG).show();
                cb.callback("native has receive " + data);
            }
        });
    }

}