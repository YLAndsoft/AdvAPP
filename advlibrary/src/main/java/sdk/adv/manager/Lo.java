package sdk.adv.manager;

import android.util.Log;

import sdk.adv.AdvConstant;

/**
 * @date {2019/6/11}
 */
public class Lo {

    public static String TAG = "Lo";
    public static boolean ADV_DEBUG = true;//全局日志开关,上线之前请关闭

    public static void setDebug(boolean isDebug){
        ADV_DEBUG = isDebug;
    }
    public static void e(String errorMsg){
        if(ADV_DEBUG){
            Log.e(TAG,errorMsg);
        }
    }
    public static void i(String msg){
        if(ADV_DEBUG){
            Log.e(TAG,msg);
        }
    }
}
