package com.muxistudio.jsbridge;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by ybao on 16/10/11.
 */

public class BridgeWebViewClient extends WebViewClient {

    public static final String INIT_JAVAINTERFACE = "window.javaInterface='%s'";

    private BridgeWebView mBridgeWebView;

    public BridgeWebViewClient(BridgeWebView bridgeWebView) {
        super();
        mBridgeWebView = bridgeWebView;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        sendInitData();
    }

    private void sendInitData() {
        InitData initData = new InitData();
        initData.platform = "android";
        mBridgeWebView.evaluateJavascript(String.format(INIT_JAVAINTERFACE,new Gson().toJson(initData)),null);
    }
}
