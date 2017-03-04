package com.muxistudio.library;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by ybao on 17/2/9.
 */

public class BridgeChromeClient extends WebChromeClient {

    private BridgeWebView mWebView;

    public BridgeChromeClient(BridgeWebView webView) {
        mWebView = webView;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//        mWebView.handleDataFromUrl(message);
        return false;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        mWebView.handleDataFromUrl(message);
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
            JsPromptResult result) {
        mWebView.handleDataFromUrl(message);
        return true;
    }
}
