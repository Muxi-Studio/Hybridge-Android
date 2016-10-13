package com.muxistudio.library;

import android.util.Log;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by ybao on 16/10/11.
 */

public class BridgeWebViewClient extends WebViewClient{

    private BridgeWebView mBridgeWebView;

    public BridgeWebViewClient(BridgeWebView bridgeWebView) {
        super();
        mBridgeWebView = bridgeWebView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        Log.d("js load url",s);
        if (Utils.isHybridUrl(s)){
            mBridgeWebView.handleDataFromUrl(s);
            return true;
        }
        return super.shouldOverrideUrlLoading(webView, s);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        Utils.loadJsBridge(webView,"WebViewJsBridge.js");
    }
}
