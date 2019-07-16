package sdk.adv.manager;

import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;

import sdk.adv.AdvConstant;
import sdk.adv.R;

/**
 * 穿山甲广告辅助类
 */
public class CsjAdvManager {

    /**
     * 初始化
     * @param context
     */
    public static void init(Context context,String appID) {

        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        try{
            TTAdSdk.init(context,buildConfig(context,appID));
            //拿取权限
            TTAdSdk.getAdManager().requestPermissionIfNecessary(context);
        }catch (Exception e){
            Lo.e("CsjAdvManager>init()："+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 获取广告管理器
     * @return
     */
    private static TTAdManager getTTAdManager() {
        try{
            return TTAdSdk.getAdManager();
        }catch (Exception e){
            Lo.e("CsjAdvManager>getTTAdManager()："+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static TTAdConfig buildConfig(Context context,String appID) {
        return new TTAdConfig.Builder()
                .appId(appID)
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName(context.getString(R.string.app_name))
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(Lo.ADV_DEBUG) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)
                .build();
    }
    /**
     * 构建TTAdNative对象
     */
    private static TTAdNative mTTAdNative;
    public static TTAdNative createTTAdNative(Context baseContext){
        if(mTTAdNative==null){
            mTTAdNative = getTTAdManager().createAdNative(baseContext);//baseContext建议为activity
        }
        return mTTAdNative;
    }
    /**
     * 构建AdSlot对象
     * @param advID
     * @param w
     * @param h
     * @return
     */
    public static AdSlot getAdSlot(String advID,int w,int h){
        if(null==advID||"".equals(advID)){
            Lo.e("广告ID是空的！！");
            return null;
        }
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(advID) //广告位id AdvConstant.BANNER_ID
                .setSupportDeepLink(true)
                .setImageAcceptedSize(w, h)
                .build();
        return adSlot;
    }


}
