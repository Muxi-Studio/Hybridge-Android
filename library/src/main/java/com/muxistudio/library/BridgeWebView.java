package com.muxistudio.library;

/**
 * Created by ybao on 16/10/8.
 */
import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;


public class BridgeWebView extends WebView {

    public BridgeWebView(Context context) {
        this(context,null);
        init();
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        QbSdk.allowThirdPartyAppDownload(true);
        init();
    }

    public void init(){
//        this.setWebViewClient(new BridgeWebViewClient(this));
    }

}
