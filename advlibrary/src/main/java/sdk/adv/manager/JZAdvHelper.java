package sdk.adv.manager;

import android.app.Activity;

import com.ssp.sdk.adInterface.AdListener;
import com.ssp.sdk.adInterface.RewardVideoListener;
import com.ssp.sdk.platform.show.PRewardVideo;

import sdk.adv.AdvConstant;
import sdk.adv.interfaces.OnSuccessListener;

/**
 * @date {2019/7/15}
 * 精众广告SDK 帮助类
 * @TODO 因精众也集成了广点通,所以这里只集成精众的视频广告
 */
public class JZAdvHelper {

    /**
     * 加载视频广告
     */
    private static PRewardVideo rewardVideo;
    public static void loadVideoAdv(Activity activity, String appID, String posID, final int gold, final OnSuccessListener listener){
        if(null==appID||"".equals(appID)){
            Lo.e("精众视频:appID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.JZ_VIDEO_TYPE);//失败回调
            return;
        }
        if(null==posID||"".equals(posID)){
            Lo.e("精众视频:posID为空！！");
            if(listener!=null)listener.onFail(AdvConstant.JZ_VIDEO_TYPE);//失败回调
            return;
        }
        if(null!=rewardVideo)rewardVideo = null;
        rewardVideo = new PRewardVideo(activity, appID, posID, new AdListener() {
            @Override
            public void onLoadFail(int i, String s) {
                //广告加载失败
                Lo.e("精众视频广告加载失败：code:"+i+"错误信息："+s);
                if(null!=listener)listener.onFail(AdvConstant.JZ_VIDEO_TYPE);
            }
            @Override
            public void onLoadSuccess() {
                //广告加载成功
                Lo.e("精众视频广告加载成功!");
                rewardVideo.showAd(new RewardVideoListener() {
                    @Override
                    public void onVideoShow() {
                    }
                    @Override
                    public void onVideoClick() {
                    }
                    @Override
                    public void onVideoBarClick() {
                    }
                    @Override
                    public void onVideoClose() {
                    }
                    @Override
                    public void onVideoComplete() {
                    }
                    @Override
                    public void onVideoError(int i, String s) {
                    }
                    @Override
                    public void onVideoVerify(boolean b, int i, String s) {
                    }
                });
            }
            @Override
            public void onAdOpen() {
                //广告被展示
            }
            @Override
            public void onAdClick() {
                //广告被点击
            }
            @Override
            public void onAdClose() {
                //广告被关闭
                if(null!=listener)listener.onComplete(AdvConstant.JZ_VIDEO_TYPE,gold,true);
            }
        });
        rewardVideo.loadAd();
    }


}
