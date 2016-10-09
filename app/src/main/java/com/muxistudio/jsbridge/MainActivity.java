package com.muxistudio.jsbridge;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.muxistudio.library.BridgeWebView;
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

    }

}