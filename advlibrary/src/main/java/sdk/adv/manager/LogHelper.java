package sdk.adv.manager;

import android.util.Log;

import sdk.adv.AdvConstant;

/**
 * @date {2019/6/11}
 */
public class LogHelper {

    public static String TAG = "LogHelper";
    public static void e(String errorMsg){
        if(AdvConstant.ADV_DEBUG){
            Log.e(TAG,errorMsg);
        }
    }
    public static void i(String msg){
        if(AdvConstant.ADV_DEBUG){
            Log.e(TAG,msg);
        }
    }
}
