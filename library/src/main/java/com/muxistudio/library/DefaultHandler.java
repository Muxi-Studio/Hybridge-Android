package com.muxistudio.library;

import com.muxistudio.library.BridgeHandler;

/**
 * Created by ybao on 16/8/27.
 */
public class DefaultHandler implements BridgeHandler {

    @Override
    public void handle(String data, CallBackFunction callbackFunction) {
        if (callbackFunction == null){
            callbackFunction.onCallBack("response from default handler");
        }
    }
}
