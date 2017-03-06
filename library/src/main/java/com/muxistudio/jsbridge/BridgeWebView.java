package com.muxistudio.jsbridge;

/**
 * Created by ybao on 16/10/8.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;

public class BridgeWebView extends WebView {

    public HashMap<String, BridgeHandler> handlers;

    public static final String JS_SEND_DATA_FORMAT = "window.yajb.trigger('%s');";

    public BridgeWebView(Context context) {
        this(context, null);
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        handlers = new HashMap<>();
        addJavascriptInterface(new InjectedObject(),"javaInterface");
        setWebViewClient(new BridgeWebViewClient(this));
        setWebChromeClient(new BridgeChromeClient(this));
    }

    public void send(String event, int param) {
        send(event, new SimpleParams<>(param));
    }

    public void send(String event, boolean param) {
        send(event, new SimpleParams<>(param));
    }

    public void send(String event, float param) {
        send(event, new SimpleParams<>(param));
    }

    public void send(String event, String param) {
        send(event, new SimpleParams<>(param));
    }

    /**
     * send event,the object should be jsonObject
     * @param event
     * @param jsonObject
     */
    public void send(String event, Object jsonObject) {
        Message message = new Message();
        message.event = event;
        message.data = jsonObject;
        try {
            String json = new Gson().toJson(message);
            evaluateJavascript(String.format(JS_SEND_DATA_FORMAT,json),null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("the object should be jsonObject");
        }
    }

    /**
     * register Java Handler to handle js event
     * @param event
     * @param handler
     */
    public void register(String event, BridgeHandler handler) {
        handlers.put(event, handler);
    }

}
