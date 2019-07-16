package sdk.adv.manager;

import com.lkvideo.sdk.impl.LKVideoListener;
import com.lkvideo.sdk.video.LKRewardVideo;

import sdk.adv.AdvConstant;
import sdk.adv.interfaces.OnSuccessListener;

/**
 * @date {2019/7/15}
 * 链咖广告ADV
 */
public class LKAdvHelper {

    private static LKRewardVideo video;
    public static void loadLKVideoAdv(String adPosId, String userId, final int gold, final OnSuccessListener listener) {
        video = new LKRewardVideo(adPosId,userId);
        // 设置视频广告回调接口
        video.setVideoListener(new LKVideoListener() {
            @Override
            public void onFail(int errorCode, String errorMsg) {
                // 视频播放失败
                if(null!=listener) listener.onFail(AdvConstant.LK_VIDEO_TYPE);
            }
            @Override
            public void onPlayStart() {
                // 开始播放视频
                Lo.e("链咖视频广告加载成功!");
            }
            @Override
            public void onPlayComplete() {
                // 视频播放完毕
//                Lo.e("链咖视频广告播放完成!");
            }
            @Override
            public void onClose() {
                // 退出视频播放
//                Lo.e("链咖视频广告观看完成关闭");
                if(null!=listener) listener.onComplete(AdvConstant.LK_VIDEO_TYPE,gold,true);
            }
        });
        // 播放视频广告
        video.show();
    }

    public static void destoryVideo(){
        // 退出时调用
        if(video!=null)video.destroy();
    }
}
