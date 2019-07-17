package com.down;

import android.util.Log;

/**
 * @date {2019/7/16}
 */
public class DownLog {
    public static final String LOG_TAG = "DownLog";
    public static void e(String msg){
        Log.e(LOG_TAG,msg);
    }
    public static void i(String msg){
        Log.i(LOG_TAG,msg);
    }

}
