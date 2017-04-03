package com.muxistudio.jsbridge;

/**
 * Created by ybao on 16/10/8.
 */

import android.content.Context;
import android.util.AttributeSet;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;

public class BridgeWebView extends WebView {

    public HashMap<String, BridgeHandler> handlers;
    public HashMap<String, BridgeHandler> responseHandlers;
    private InjectedObject mInjectedObject;

    private int uniqueId = 0;

    public static final String INJECTED_OBJECT_NAME = "javaInterface";

    public static final String JS_SEND_DATA_FORMAT = "window.YAJB_INSTANCE._emit('%s');";

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
        responseHandlers = new HashMap<>();
        mInjectedObject = new InjectedObject();
        addJavascriptInterface(mInjectedObject, "javaInterface");
        setWebViewClient(new BridgeWebClient(this));
    }

    public void setInitData(String json) {
        mInjectedObject.setData(json);
    }

    public void setInitData(Object data) {
        mInjectedObject.setData(data);
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
     */
    public void send(String event, Object jsonObject) {
        send(event,jsonObject,null);
    }

    public void send(String event,Object jsonObject,BridgeHandler callbackHandler){
        Message message = new Message();
        message.event = event;
        message.data = jsonObject;
        message.id = uniqueId++;
        try {
            String json = new Gson().toJson(message);
            if (callbackHandler != null){
                responseHandlers.put(event + "Resolved" + (message.id),callbackHandler);
            }
            evaluateJavascript(String.format(JS_SEND_DATA_FORMAT, json), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("the object should be jsonObject");
        }
    }

    /**
     * register Java Handler to handle js event
     */
    public void register(String event, BridgeHandler handler) {
        handlers.put(event, handler);
    }

}
