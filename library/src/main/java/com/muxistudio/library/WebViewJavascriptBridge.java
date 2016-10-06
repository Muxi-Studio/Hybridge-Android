package com.muxistudio.library;


/**
 * Created by ybao on 16/8/28.
 */
public interface WebViewJavascriptBridge {

    void send(String data);

    void send(String data, CallBackFunction callbackFunction);
}
