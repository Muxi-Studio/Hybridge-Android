package com.muxistudio.library;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by ybao on 16/8/28.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.RETURN_DATA)) { // 如果是返回数据
            webView.handleReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.BRIDGE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (BridgeWebView.LOAD_JS!= null) {
            BridgeUtil.loadLocalJs(view, BridgeWebView.LOAD_JS);
        }

        if (webView.getStartMessage() != null) {
            for (Message m : webView.getStartMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}
