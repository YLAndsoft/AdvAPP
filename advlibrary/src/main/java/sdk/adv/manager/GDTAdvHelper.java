package sdk.adv.manager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.ssp.sdk.platform.show.PRewardVideo;

import sdk.adv.AdvConstant;
import sdk.adv.interfaces.OnSuccessListener;
import sdk.adv.tools.ViewTools;


/**
 * @date {2019/6/11}
 */
public class GDTAdvHelper {

    /***
     * 加载Banner广告
     * @param activity
     * @param viewGroup
     * @param appID
     * @param posID
     */
    public static void loadBanner(Activity activity, ViewGroup viewGroup, String appID, String posID, final OnSuccessListener listener){
        if(null==appID||"".equals(appID)){
            Lo.e("Banner:广点通appID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_TYPE);
            return;
        }
        if(null==posID||"".equals(posID)){
            Lo.e("Banner:广点通posID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_TYPE);
            return;
        }
        viewGroup.removeAllViews();
//        BannerView banner = new BannerView(activity, ADSize.BANNER, appID, posID);
        UnifiedBannerView banner = new UnifiedBannerView(activity, appID, posID,new UnifiedBannerADListener(){
            @Override
            public void onNoAD(AdError adError) {
                Lo.e("广点通Banner广告加载失败,错误码:"+adError.getErrorCode()+"错误信息："+adError.getErrorMsg());
                if(listener!=null)listener.onFail(AdvConstant.GDT_TYPE);
            }
            @Override
            public void onADReceive() {
                //加载成功
                Lo.e("广点通Banner广告加载成功");
            }
            @Override
            public void onADExposure() {
            }
            @Override
            public void onADClosed() {
            }
            @Override
            public void onADClicked() {
            }
            @Override
            public void onADLeftApplication() {
            }
            @Override
            public void onADOpenOverlay() {
            }
            @Override
            public void onADCloseOverlay() {
            }
        });

        //设置广告轮播时间，为0或30~120之间的数字，单位为s,0标识不自动轮播
        banner.setRefresh(20);
        banner.setVisibility(View.VISIBLE);
        viewGroup.addView(banner);
//        viewGroup.addView(banner, ViewTools.getUnifiedBannerLayoutParams(activity));
//        banner.setShowClose(false;);//设置是否展示关闭按钮,默认不展示
        banner.loadAD();
    }

    /**
     * 加载插屏广告
     * @param appID
     * @param posID
     */
    private static UnifiedInterstitialAD iad;
    public static void loadCPAdv(Activity activity, String appID, String posID,final int gold, final OnSuccessListener listener){
        if(null==appID||"".equals(appID)){
            Lo.e("广点通插屏:appID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_CP_TYPE);//失败回调
            return;
        }
        if(null==posID||"".equals(posID)){
            Lo.e("广点通插屏:posID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_CP_TYPE);//失败回调
            return;
        }
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }
        if (iad == null) {
            iad = new UnifiedInterstitialAD(activity, appID, posID, new UnifiedInterstitialADListener() {
                //插屏广告加载完毕
                @Override
                public void onADReceive() {
                    Lo.e("广点通插屏广告加载完毕");
//                    iad.showAsPopupWindow();//展示插屏广告，无遮罩\
                iad.show();//展示插屏广告，有遮罩
                }
                @Override
                public void onNoAD(AdError adError) {
                    Lo.e("插屏广告加载失败,错误码:"+adError.getErrorCode()+"错误信息："+adError.getErrorMsg());
                    if(listener!=null)listener.onFail(AdvConstant.GDT_CP_TYPE);//失败回调
                }
                @Override
                public void onADOpened() {
                }
                @Override
                public void onADExposure() {
                }
                @Override
                public void onADClicked() {
                }
                @Override
                public void onADLeftApplication() {
                }
                @Override
                public void onADClosed() {
                    if(listener!=null)listener.onComplete(AdvConstant.GDT_CP_TYPE,gold,true);
                }
            });
        }
        iad.loadAD();
    }

    /**
     *  广点通开屏
     * @param activity
     * @param adContainer
     * @param appID
     * @param posID
     */
    public static void loadSplashAD(Activity activity, ViewGroup adContainer, String appID, String posID,final int gold, final OnSuccessListener listener){
        if(null==appID||"".equals(appID)){
            Lo.e("广点通开屏:appID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_KP_TYPE);//失败回调
            return;
        }
        if(null==posID||"".equals(posID)){
            Lo.e("广点通开屏:posID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_KP_TYPE);//失败回调
            return;
        }
        adContainer.removeAllViews();
        adContainer.setVisibility(View.VISIBLE);
        SplashAD splashAD = new SplashAD(activity, adContainer, appID, posID, new SplashADListener() {
            //广告关闭时调用
            @Override
            public void onADDismissed() {
                Lo.i("广告关闭");
                if(listener!=null)listener.onComplete(AdvConstant.GDT_KP_TYPE,gold,true);
            }
            //广告加载失败，error 对象包含了错误码和错误信息
            @Override
            public void onNoAD(AdError adError) {
                Lo.e("广点通开屏广告加载失败,错误码:"+adError.getErrorCode()+"错误信息："+adError.getErrorMsg());
                if(listener!=null)listener.onFail(AdvConstant.GDT_KP_TYPE);//失败回调
            }
            //广告成功展示时调用
            @Override
            public void onADPresent() {
                Lo.i("广告成功展示");
            }
            //广告被点击时调用
            @Override
            public void onADClicked() {
                Lo.i("广告被点击");
            }
            //倒计时回调
            @Override
            public void onADTick(long millisUntilFinished) {
                Lo.e("倒计时："+String.format("点击跳过 %d",Math.round(millisUntilFinished / 1000f)));
            }
            //广告曝光时调用
            @Override
            public void onADExposure() {
            }
        });
    }

    /**
     * 加载视频广告
     * @param activity
     * @param appID
     * @param posID
     */
    private static RewardVideoAD rewardVideoAD;
    public static void loadVideoAdv(Activity activity, String appID, String posID,final int gold, final OnSuccessListener listener){
        if(null==appID||"".equals(appID)){
            Lo.e("广点通视频:appID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_VIDEO_TYPE);//失败回调
            return;
        }
        if(null==posID||"".equals(posID)){
            Lo.e("广点通视频:posID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.GDT_VIDEO_TYPE);//失败回调
            return;
        }
//        PRewardVideo rewardVideo = new PRewardVideo();
        rewardVideoAD = new RewardVideoAD(activity, appID, posID, new RewardVideoADListener() {
            //广告加载成功，可在此回调后进行广告展示
            @Override
            public void onADLoad() {
                rewardVideoAD.showAD();//展示视频广告
            }
            //视频素材缓存成功，可在此回调后进行广告展示
            @Override
            public void onVideoCached() {
            }
            //激励视频广告页面展示
            @Override
            public void onADShow() {
            }
            //激励视频广告曝光
            @Override
            public void onADExpose() {
            }
            //激励视频触发激励
            @Override
            public void onReward() {
            }
            //激励视频广告被点击
            @Override
            public void onADClick() {
            }
            //激励视频播放完毕
            @Override
            public void onVideoComplete() {
                if(listener!=null)listener.onComplete(AdvConstant.GDT_VIDEO_TYPE,gold,true);
            }
            //激励视频广告被关闭
            @Override
            public void onADClose() {
//                if(listener!=null)listener.onComplete(AdvConstant.GDT_VIDEO_TYPE);
            }
            //广告流程出错
            @Override
            public void onError(AdError adError) {
                Lo.e("广点通视频广告加载失败,错误码:"+adError.getErrorCode()+"错误信息："+adError.getErrorMsg());
                if(listener!=null)listener.onFail(AdvConstant.GDT_VIDEO_TYPE);//失败回调
            }
        });
        rewardVideoAD.loadAD();
    }

}
