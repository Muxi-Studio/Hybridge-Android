package com.muxistudio.jsbridge;

import android.webkit.JavascriptInterface;

import com.google.gson.Gson;

/**
 * Created by ybao (ybaovv@gmail.com)
 * Date: 17/3/6
 * injected object ,jsbridge-js init with injectedObject
 */

public class InjectedObject {

    public String platform = "android";
    public String data;

    @JavascriptInterface
    public String toString(){
        return new Gson().toJson(this,InjectedObject.class);
    }
}
