package com.muxistudio.jsbridge;

import android.net.Uri;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by ybao (ybaovv@gmail.com)
 * Date: 17/4/3
 */

public class BridgeWebClient extends WebViewClient{

    public static final String PROTOCOL_HYBRID = "hybrid";

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
//        Uri uri = Uri.parse(s);
//        if (uri.getScheme().equals(PROTOCOL_HYBRID)){
//            String
//        }
        return super.shouldOverrideUrlLoading(webView, s);
    }
}
