package com.muxistudio.library;

/**
 * Created by ybao on 16/8/28.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("SetJavaScriptEnabled")
public class BridgeWebView extends WebView implements WebViewJavascriptBridge {

    public static final String LOAD_JS = "WebViewJsBridge.js";
    private Map<String, CallBackFunction> responseCallbacks;
    private Map<String, BridgeHandler> bridgeHandlers;
    private BridgeHandler defaultBridgeHandler;

    private long uniqueId = 0;

    private List<Message> startupMessage = new ArrayList<>();

    public void setStartupMessage(List<Message> startupMessage){
        this.startupMessage = startupMessage;
    }

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
        responseCallbacks = new HashMap<>();
        bridgeHandlers = new HashMap<>();
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        setWebViewClient(new BridgeWebViewClient(this));
    }

    public void handleReturnData(String url){
        String functionName = BridgeUtil.getFunctionFromReturnUrl(url);
        CallBackFunction f = responseCallbacks.get(functionName);
        String data = BridgeUtil.getDataFromReturnUrl(url);
        if (f != null){
            f.onCallBack(data);
            responseCallbacks.remove(functionName);
            return;
        }
    }

    @Override
    public void send(String data) {
        send(data,null);
    }

    @Override
    public void send(String data, CallBackFunction callbackFunction) {
        doSend(null,data,callbackFunction);
    }

    private void doSend(String handlerName,String data, CallBackFunction callbackFunction) {
        Message m = new Message();
        if (!TextUtils.isEmpty(data)){
            m.setData(data);
        }
        if (callbackFunction != null){
            String callbackStr = String.format(BridgeUtil.CALLBACK_FORMAT,++uniqueId + (BridgeUtil.UNDERLINE_STR + System.currentTimeMillis()));
            responseCallbacks.put(callbackStr,callbackFunction);
            m.setCallbackId(callbackStr);
        }
        if (!TextUtils.isEmpty(handlerName)){
            m.setHandlerName(handlerName);
        }
        queueMessage(m);
    }

    public List<Message> getStartMessage(){
        return startupMessage;
    }

    public void queueMessage(Message m){
        if (startupMessage != null){
            startupMessage.add(m);
        }else {
            dispatchMessage(m);
        }
    }

    public void dispatchMessage(Message m) {
        String messageJson = m.toJSON();
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])","\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")","\\\\\"");
        String jsCommand = String.format(BridgeUtil.JS_HANDLE_MESSAGE_FROM_JAVA,messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()){
            this.loadUrl(jsCommand);
        }
    }

    public void flushMessageQueue(){
        if (Thread.currentThread() == Looper.getMainLooper().getThread()){
            loadUrl(BridgeUtil.JS_FETCH_QUEUE_FROM_JAVA,new CallBackFunction(){
                @Override
                public void onCallBack(String data) {
                    List<Message> list = null;
                    try {
                        list = Message.toList(data);
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                    if (list == null || list.size() == 0){
                        return;
                    }
                    for (int i = 0;i < list.size();i ++){
                        Message m = list.get(i);
                        String responseId = m.getResponseId();
                        if (!TextUtils.isEmpty(responseId)){
                            CallBackFunction function = responseCallbacks.get(responseId);
                            String responseData = m.getResponseData();
                            function.onCallBack(responseData);
                            responseCallbacks.remove(responseId);
                        }else {
                            CallBackFunction responseFunction = null;
                            final String callbackId = m.getCallbackId();
                            if (!TextUtils.isEmpty(callbackId)){
                                responseFunction = new CallBackFunction() {
                                    @Override
                                    public void onCallBack(String data) {
                                        Message responseMsg = new Message();
                                        responseMsg.setResponseId(callbackId);
                                        responseMsg.setResponseData(data);
                                        queueMessage(responseMsg);
                                    }
                                };
                            }else {
                                responseFunction = new CallBackFunction() {
                                    @Override
                                    public void onCallBack(String data) {

                                    }
                                };
                            }
                            BridgeHandler handler;
                            if (!TextUtils.isEmpty(m.getHandlerName())){
                                handler = bridgeHandlers.get(m.getHandlerName());
                            }else {
                                handler = defaultBridgeHandler;
                            }
                            if (handler != null){
                                handler.handle(m.getData(),responseFunction);
                            }
                        }
                    }
                }
            });
        }
    }

    public void loadUrl(String jsUrl,CallBackFunction callBackFunction){
        this.loadUrl(jsUrl);
        responseCallbacks.put(BridgeUtil.parseFunctionName(jsUrl),callBackFunction);
    }

    public void registerHandler(String handlerName,BridgeHandler handler){
        if (handler != null){
            bridgeHandlers.put(handlerName,handler);
        }
    }

    public void callHandler(String handlerName,String data,CallBackFunction callback){
        doSend(handlerName,data,callback);
    }

}
