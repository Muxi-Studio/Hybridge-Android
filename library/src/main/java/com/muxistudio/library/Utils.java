package com.muxistudio.library;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by ybao on 16/10/11.
 */

public class Utils {

    public static final String JS_protocol = "javascript:";
    public static final String JS_HANDLE_METHOD = "handleMsgFromNative";
    public static final String JS_BRIDGE_STR = JS_protocol + JS_HANDLE_METHOD;
    public static final String JS_URL = "jsUrl";
    public static final String REGEX_METHOD = "/"

    public static String createJsUrl(Message msg) {
        String url;
        url = Message.stringifyJSON(msg);
        Pattern p = Pattern.compile()
        Log.d(JS_URL, url);
        return JS_BRIDGE_STR + "(" + url + ");";
    }

    public static String getMethodFromJson(String jsonStr){
        try {
            URL url = new URL(jsonStr);

            return url.getQuery();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
