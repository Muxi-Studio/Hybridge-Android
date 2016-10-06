package com.muxistudio.library;

import android.content.Context;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ybao on 16/8/28.
 */
public class BridgeUtil {

    public static final String BRIDGE_SCHEMA = "bridge://";
    public static final String RETURN_DATA = BRIDGE_SCHEMA + "return/";
    public static final String FETCH_QUEUE = RETURN_DATA + "_fetchQueue";
    public static final String EMPTY_STR = "";
    public static final String UNDERLINE_STR = "_";
    public static final String SPLIT_MASK = "/";

    public static final String CALLBACK_FORMAT = "java_%s";
    public static final String JS_HANDLE_MESSAGE_FROM_JAVA ="javascript:WebViewJsBridge._handleMessageFromNative('%s');";
    public static final String JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge.fetchQueue();";
    public static final String JS_STR = "javascript:";

    public static String parseFunctionName(String jsUrl){
        return jsUrl.replace("javascript:WebViewJsBridge.",EMPTY_STR).replaceAll("\\(.*\\);","");
    }

    public static String getDataFromReturnUrl(String url){
        if (url.startsWith(FETCH_QUEUE)){
            return url.replace(FETCH_QUEUE,"");
        }

        String temp = url.replace(RETURN_DATA,EMPTY_STR);
        String[] functionAndData = temp.split(SPLIT_MASK);
        if (functionAndData.length >= 2){
            StringBuffer sb = new StringBuffer();
            for (int i = 1;i < functionAndData.length;i ++){
                sb.append(functionAndData[i]);
            }
            return sb.toString();
        }
        return null;
    }

    public static String getFunctionFromReturnUrl(String url){
        String temp = url.replace(RETURN_DATA,EMPTY_STR);
        String[] functionAndData = temp.split(SPLIT_MASK);
        if (functionAndData != null && functionAndData.length >= 1){
            return functionAndData[0];
        }
        return null;
    }

    public static void loadJs(WebView view,String url){
        String js = "var newscript = document.createElement(\"script\");";
        js += "newscript.src\"" + url + "\";";
        js += "document.scripts[0].parentNode.insertBefore(newscript,document.scripts[0]);";
        view.loadUrl("javascript:" + js);
    }

    public static void loadLocalJs(WebView view,String path){
        String jsContent = loadAssetJs(view.getContext(),path);
        view.loadUrl("javascript:" + jsContent);
    }

    public static String loadAssetJs(Context context,String path){
        try {
            InputStream in = context.getAssets().open(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null){
                if (line.matches("^\\s*\\/\\/.*")){
                    sb.append(line);
                }
            }
            br.close();
            in.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
