package com.muxistudio.jsbridge;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

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

    public void setData(String data) {
        this.data = data;
    }

    public void setData(Object data){
        this.data = new Gson().toJson(data);
    }
}
