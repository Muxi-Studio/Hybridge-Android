package com.muxistudio.library;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by ybao on 16/10/11.
 */

public class BridgeWebViewClient extends WebViewClient{

    public BridgeWebViewClient() {
        super();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        if ()
        return super.shouldOverrideUrlLoading(webView, s);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
    }
}
