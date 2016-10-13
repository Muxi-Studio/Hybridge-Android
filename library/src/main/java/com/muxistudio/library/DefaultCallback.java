package com.muxistudio.library;

import android.util.Log;

/**
 * Created by ybao on 16/10/12.
 */

public class DefaultCallback implements CallbackFunc{

    @Override
    public void callback(String data) {
        Log.d("callback data in java",data);
    }
}
