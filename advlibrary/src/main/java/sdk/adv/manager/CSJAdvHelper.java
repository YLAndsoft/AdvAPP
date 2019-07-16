package sdk.adv.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import sdk.adv.AdvConstant;
import sdk.adv.interfaces.OnSuccessListener;

/**
 * @date {2019/6/11}
 */
public class CSJAdvHelper {

    private static TTAdNative mTTAdNative;
    private static void initAdNative(Context mContext){
        if(mTTAdNative==null){
            mTTAdNative = CsjAdvManager.createTTAdNative(mContext);
        }
    }
    /***
     * 加载穿山甲视频广告
     */
    public static void loadCSJVideo(final Activity activity, String videoID,final int gold, final OnSuccessListener listener){
        AdSlot adSlot = CsjAdvManager.getAdSlot(videoID,1080,1920);
        if(adSlot==null){
            Lo.i("请检查videoID是否有效！！！");
            if(listener!=null)listener.onFail(AdvConstant.CSJ_VIDEO_TYPE);//失败回调
            return;
        }
        if(mTTAdNative==null){
            initAdNative(activity);
        }

        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                Lo.e("加载穿山甲视频广告错误码："+i+"，错误信息："+s);
                if(listener!=null)listener.onFail(AdvConstant.CSJ_VIDEO_TYPE);//失败回调
            }
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                if(null==activity||activity.isFinishing()||activity.isDestroyed()){
                    return;
                }
                ttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                    }
                    @Override
                    public void onAdVideoBarClick() {
                    }
                    @Override
                    public void onAdClose() {
                    }
                    @Override
                    public void onVideoComplete() {
                    }
                    @Override
                    public void onVideoError() {
                        if(listener!=null)listener.onFail(AdvConstant.CSJ_VIDEO_TYPE);//失败回调
                    }
                    //视频广告播完验证奖励有效性回调，
                    @Override
                    public void onRewardVerify(boolean b, int i, String s) {
                            if(listener!=null)listener.onComplete(AdvConstant.CSJ_VIDEO_TYPE,gold,true);
                    }
                });
                ttRewardVideoAd.showRewardVideoAd(activity);
//                Lo.i("穿山甲视频广告加载成功！");
            }
            @Override
            public void onRewardVideoCached() {

            }
        });
    }
    /***
     * 加载穿山甲开屏广告
     */
    public static void loadCSJKPAdv(Activity activity, final FrameLayout frameLayout, String kpID,final int gold, final OnSuccessListener listener){
        AdSlot adSlot = CsjAdvManager.getAdSlot(kpID,1080,1920);
        if(adSlot==null){
            Lo.i("请检查kpID是否有效！！！");
            if(listener!=null)listener.onFail(AdvConstant.CSJ_KP_TYPE);//失败回调
            return;
        }
        if(mTTAdNative==null){
            initAdNative(activity);
        }
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int i, String s) {
                Lo.e("加载穿山甲开屏广告错误码："+i+"，错误信息："+s);
                if(listener!=null)listener.onFail(AdvConstant.CSJ_KP_TYPE);//失败回调
            }
            @Override
            public void onTimeout() {
                Lo.e("加载穿山甲开屏广告超时！");
                if(listener!=null)listener.onFail(AdvConstant.CSJ_KP_TYPE);//失败回调
            }
            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                if(ttSplashAd==null){
                    return;
                }
                View view = ttSplashAd.getSplashView();
                ttSplashAd.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int i) {//点击
                    }
                    @Override
                    public void onAdShow(View view, int i) { //展示
                    }
                    @Override
                    public void onAdSkip() {//跳过
                        if(listener!=null){
                            Lo.e("跳过！");
                            listener.onComplete(AdvConstant.CSJ_KP_TYPE,gold,false);
                        }
                    }
                    @Override
                    public void onAdTimeOver() {//倒计时结束
                        if(listener!=null){
                            Lo.e(" 倒计时结束！");
                            listener.onComplete(AdvConstant.CSJ_KP_TYPE,gold,true);
                        }
                    }
                });
                frameLayout.removeAllViews();
                frameLayout.addView(view);
                frameLayout.setVisibility(View.VISIBLE);
            }

        });
    }

    /***
     * 加载穿山甲插屏广告
     */
    public static void loadCSJCPAdv(final Activity activity, String cpID,final int gold, final OnSuccessListener listener){
        AdSlot adSlot = CsjAdvManager.getAdSlot(cpID,600,600);
        if(adSlot==null){
            Lo.i("请检查kpID是否有效！！！");
            if(listener!=null)listener.onFail(AdvConstant.CSJ_CP_TYPE);//失败回调
            return;
        }
        if(mTTAdNative==null){
            initAdNative(activity);
        }
        mTTAdNative.loadInteractionAd(adSlot, new TTAdNative.InteractionAdListener() {
            @Override
            public void onError(int i, String s) {
                Lo.e("加载穿山甲插屏广告错误码："+i+"，错误信息："+s);
                if(listener!=null)listener.onFail(AdvConstant.CSJ_CP_TYPE);//失败回调
            }

            @Override
            public void onInteractionAdLoad(TTInteractionAd ttInteractionAd) {
                if(null==activity||activity.isFinishing()||activity.isDestroyed()){
                    return;
                }
                ttInteractionAd.setAdInteractionListener(new TTInteractionAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked() { //点击
                    }
                    @Override
                    public void onAdShow() { //显示
                    }
                    @Override
                    public void onAdDismiss() {
                        if(listener!=null)listener.onComplete(AdvConstant.CSJ_CP_TYPE,gold,true);
                    }
                });
                ttInteractionAd.showInteractionAd(activity);
            }
        });
    }

    /***
     * 加载显示穿山甲Banner广告
     */
    public static void loadCSJBannerAdv(Context mContext, final String bannerID, final FrameLayout mBannerContainer, final OnSuccessListener listener){
        AdSlot adSlot = CsjAdvManager.getAdSlot(bannerID,600,90);
        if(adSlot==null){
            Lo.e("请检查kpID是否有效！！！");
            if(listener!=null){listener.onFail(AdvConstant.CSJ_TYPE);}
            return;
        }
        if(mTTAdNative==null){
            initAdNative(mContext);
        }
        mTTAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {
            @Override
            public void onError(int i, String s) {
                if(listener!=null){listener.onFail(AdvConstant.CSJ_TYPE);}
                Lo.e("加载穿山甲Banner广告错误码："+i+"，错误信息："+s);
            }
            @Override
            public void onBannerAdLoad(TTBannerAd ttBannerAd) {
                if(ttBannerAd==null){return;}
                View bannerView = ttBannerAd.getBannerView();
                if (bannerView == null) {
                    return;
                }
                mBannerContainer.removeAllViews();
                mBannerContainer.addView(bannerView);
                if(listener!=null){listener.onComplete(AdvConstant.CSJ_TYPE,0,true);}
            }
        });
    }

}
