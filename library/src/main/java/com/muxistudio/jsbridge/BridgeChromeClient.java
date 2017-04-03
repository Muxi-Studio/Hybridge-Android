package com.muxistudio.jsbridge;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Gson gson = new Gson();
        try {
            Message msg = gson.fromJson(message, Message.class);
            if (!TextUtils.isEmpty(msg.event)) {
                mWebView.handlers.get(msg.event).handle(msg.data.toString());
                result.confirm();
            } else {
                return false;
            }
            return true;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

}
