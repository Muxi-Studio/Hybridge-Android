package com.muxistudio.jsbridge;

/**
 * Created by ybao on 16/10/12.
 */

public interface BridgeHandler {

    void handle(String data,CallbackFunc cb);
}
