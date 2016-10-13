package com.muxistudio.library;

import android.content.Context;
import android.util.Log;

import com.tencent.smtt.sdk.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ybao on 16/10/11.
 */

public class Utils {

    public static final String JS_PROTOCOL = "javascript:";
    public static final String JS_HANDLE_METHOD = "window.JsBridge.handleMsgFromNative";
    public static final String JS_BRIDGE_STR = JS_PROTOCOL + JS_HANDLE_METHOD;
    public static final String JS_URL = "jsUrl";
    public static final String HYBRID_PROTOCOL = "hybrid://";

    //生成 jsurl 调用 js
    public static String createJsUrl(Message msg) {
        String url;
        url = Message.stringifyJSON(msg);
        url = url.replaceAll("\"","\\\\\\\"");
        url = JS_BRIDGE_STR + "(\"" + url + "\");";
        Log.d("jsbridge",url);
        return url;
    }

    /**
     * 获取 url 中的方法名
     *
     * @param url
     * @return
     */
    public static String getMethodFromUrl(String url) {
        try {
            Pattern p = Pattern.compile("/(\\w+)\\?");
            Matcher m = p.matcher(url);
            if (m.find()){
                String s = m.group(0);
                s = s.replace("/","").replace("?","");
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isHybridUrl(String url) {
        if (url.startsWith(HYBRID_PROTOCOL)) {
            return true;
        }
        return false;
    }

    /**
     * 解析url 的 json 参数
     *
     * @param url
     * @return
     */
    public static Message getMsgFromUrl(String url) {
        Pattern p = Pattern.compile("\\{(.*)\\}");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return Message.parseJSON(m.group(0));
        }
        return null;
    }

    public static void loadJs(WebView webView, String url) {
        String js = "javascript:var newscript = document.createElement(\"script\");" +
                "newscript.src = \"" + url + "\";" +
                "document.script[0].parentNode.insertBefore(newscript,document.scripts[0]);";
        Log.d("jsbridge", "jsbridge init");
        webView.loadUrl(js);
    }

    public static void loadJsBridge(WebView view, String path) {
        String jsUrl = assetFile2Str(view.getContext(), path);
//        Log.d("jsbridge","jsbridge init");
        Log.d("jsbridge content", jsUrl);
        view.loadUrl("javascript:" + jsUrl);
    }

    public static String assetFile2Str(Context c, String urlStr) {
        InputStream in = null;
        try {
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

}
