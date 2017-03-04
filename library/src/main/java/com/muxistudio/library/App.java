package com.muxistudio.library;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by ybao on 17/2/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("qbs", "腾讯X5内核 下载结束");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("qbs", "腾讯X5内核 安装完成");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("qbs", "腾讯X5内核 下载进度:%" + i);
            }
        });

        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d("qbs", "core init finish");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d("qbs", "view init " + b);
            }
        });

    }
}
