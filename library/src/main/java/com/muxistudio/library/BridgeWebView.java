package com.muxistudio.library;

/**
 * Created by ybao on 16/10/8.
 */

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class BridgeWebView extends WebView {

    public HashMap<String, BridgeHandler> handlers;
    public HashMap<String, CallbackFunc> callbacks;
    private int uniqueId = 1;

    private String JSBRIDGE_URL = "WebViewJsBridge.js";

    public BridgeWebView(Context context) {
        this(context, null);
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        QbSdk.allowThirdPartyAppDownload(true);
        init();
    }

    public void init() {
//        this.setWebViewClient(new BridgeWebViewClient(this));
        handlers = new HashMap<>();
        callbacks = new HashMap<>();
//        Utils.loadJsBridge(this, "WebViewJsBridge.js");
        setWebViewClient(new BridgeWebViewClient(this));
    }

    public void handleDataFromUrl(String s) {
        try {
            s = URLDecoder.decode(s,"utf-8").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (handlers == null) {
            handlers = new HashMap<>();
        }
        if (handlers.get(Utils.getMethodFromUrl(s)) != null) {
            Message msg = Utils.getMsgFromUrl(s);
            if (msg.getResponseId() != null) {
                CallbackFunc callbackFunc = callbacks.get(msg.getResponseId());
                callbackFunc.callback(msg.getResponseData());
                callbacks.remove(msg.getResponseId());
                return;
            }

            if (msg.getCallbackId() != null) {
                final String responseId = msg.getCallbackId();
                BridgeHandler handler = handlers.get(Utils.getMethodFromUrl(s));
                handler.handle(msg.getData(), new CallbackFunc() {
                    @Override
                    public void callback(String data) {
                        Message sendMsg = new Message();
                        sendMsg.setResponseData(data);
                        sendMsg.setResponseId(responseId);
                        send(sendMsg);
                    }
                });
            } else {
                BridgeHandler handler = handlers.get(Utils.getMethodFromUrl(s));
                handler.handle(msg.getData(), new DefaultCallback());
            }
        }
    }

    public void registerHandler(String method, BridgeHandler handler) {
        handlers.put(method, handler);
    }

    public void callJs(String method, String data, CallbackFunc callback) {
        Message msg = new Message();
        msg.setData(data);
        msg.setMethod(method);
        if (callback != null) {
            msg.setCallbackId(uniqueId++  + "");
            callbacks.put(msg.getCallbackId(), callback);
        }
        send(msg);
    }

    public void send(Message msg) {
        loadUrl(Utils.createJsUrl(msg));
    }
}
