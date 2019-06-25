package sdk.adv.execute;

import android.app.Activity;
import android.widget.FrameLayout;

import sdk.adv.AdConfig;
import sdk.adv.AdvAppLication;
import sdk.adv.AdvConstant;
import sdk.adv.entity.AdvEntity;
import sdk.adv.interfaces.OnSuccessListener;
import sdk.adv.manager.CSJAdvHelper;
import sdk.adv.manager.GDTAdvHelper;
import sdk.adv.manager.Lo;


/**
 * @date {2019/6/12}
 * 广告执行类
 */
public class AdvExecute {
    private static AdvExecute advExecute;
    private static AdConfig config;
    public interface OnCompleteListener{
        void onComplete(int gold, boolean isNormal);
    }

    public static AdvExecute create() {
        if (advExecute == null) {
            config = AdvAppLication.adConfig;
            advExecute = new AdvExecute();
        }
        return advExecute;
    }

    /**
     * 广告:所有广告
     * @param activity    当前需要展示的activity
     * @param frameLayout 开屏广告的父容器
     * @param gold        奖励的金币
     * @param listener
     * @TODO 参数说明：
     * 穿山甲视频广告：	    0
     * 穿山甲开屏广告:		1
     * 穿山甲插屏广告:		2
     * 广点通视频广告:		3
     * 广点通开屏广告:		4
     * 广点通插屏广告:		5
     */
    public void execute(Activity activity, FrameLayout frameLayout, int gold, OnCompleteListener listener) {
        AdvEntity advEntity = AdvPools.getAdvPool().getAllAdv(activity);
        Lo.i("展示广告type：" + advEntity.getAdvType());
        if(null==advEntity){
            Lo.e("广告全部失效!");
            listener.onComplete(0,true);
            return;
        }
        switch (advEntity.getAdvType()) {
            case 0: //穿山甲视频
                openCSJVideo(advEntity,activity, frameLayout, gold, true, listener);
                break;
            case 1: //穿山甲开屏
                openCSJKPAdv(advEntity,activity, frameLayout, gold, true, listener);
                break;
            case 2: //穿山甲插屏
                openCSJCPAdv(advEntity,activity, frameLayout, gold, true, listener);
                break;
            case 3: //广点通视频
//                Lo.e("广点通的视频广告暂未开通!");
                openGDTVideoAdv(advEntity,activity, frameLayout, gold, true, listener);
                break;
            case 4: //广点通开屏
                openGDTSplashAD(advEntity,activity, frameLayout, gold, true, listener);
                break;
            case 5: //广点通插屏
                openGDTCPAdv(advEntity,activity, frameLayout, gold, true, listener);
                break;
            default:
                //没有广告展示，直接返回
                    if(listener!=null)listener.onComplete(gold,false);
                break;
        }

    }

    /*****************************以下方法是加载同类类型广告***********************************/
    /***
     * 执行加载所有Banner广告
     * @param activity
     * @param frameLayout
     * @param listener
     */
    public void executeBanner(final Activity activity, final FrameLayout frameLayout, final OnCompleteListener listener){
        final AdvEntity advEntity = AdvPools.getAdvPool().getBannerAdv(activity);
        if(null==advEntity){
            Lo.e("Banner广告全部失效!");
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_bannerID
            CSJAdvHelper.loadCSJBannerAdv(activity, config.getCsj_bannerID(), frameLayout, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("穿山甲广告加载成功!");
                    if(listener!=null)listener.onComplete(0,true);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setBannerFailPool(advEntity);
                    executeBanner(activity,frameLayout,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_appID, AdvConstant.gdt_bannerID
            GDTAdvHelper.loadBanner(activity, frameLayout,config.getGdt_appID(),config.getGdt_bannerID() , new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("广点通广告加载成功!");
                    if(listener!=null)listener.onComplete(0,true);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setBannerFailPool(advEntity);
                    executeBanner(activity,frameLayout,listener);
                }
            });
        }
    }

    /***
     * 执行加载所有插屏广告
     * @param activity
     * @param listener
     */
    public void executeCpAdv(final Activity activity,final OnCompleteListener listener){
        final AdvEntity advEntity = AdvPools.getAdvPool().getCPAdv(activity);
        if(null==advEntity){
            Lo.e("插屏广告全部失效!");
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_cpID
            CSJAdvHelper.loadCSJCPAdv(activity, config.getCsj_cpID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("穿山甲插屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setCpFailPool(advEntity);
                    executeCpAdv(activity,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_appID, AdvConstant.gdt_cpID
            GDTAdvHelper.loadCPAdv(activity, config.getGdt_appID(),config.getGdt_cpID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("广点通插屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setCpFailPool(advEntity);
                    executeCpAdv(activity,listener);
                }
            });
        }
    }

    /***
     * 执行加载所有开屏广告
     * @param activity
     * @param frameLayout
     * @param listener
     */
    public void executeSplashAdv(final Activity activity, final FrameLayout frameLayout,final OnCompleteListener listener){
        final AdvEntity advEntity = AdvPools.getAdvPool().getSpashAdv(activity);
        if(null==advEntity){
            Lo.e("开屏广告全部失效!");
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_splashID
            CSJAdvHelper.loadCSJKPAdv(activity, frameLayout, config.getCsj_splashID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("穿山甲开屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setSplashFailPool(advEntity);
                    executeSplashAdv(activity,frameLayout,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_kpID, AdvConstant.gdt_appID
            GDTAdvHelper.loadSplashAD(activity, frameLayout, config.getGdt_appID(),config.getGdt_kpID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("广点通开屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setSplashFailPool(advEntity);
                    executeSplashAdv(activity,frameLayout,listener);
                }
            });
        }
    }

    /***
     * 执行加载所有视频广告
     * @param activity
     * @param listener
     */
    public void executeVideoAdv(final Activity activity,final OnCompleteListener listener){
        final AdvEntity advEntity = AdvPools.getAdvPool().getVideoAdv(activity);
        if(null==advEntity){
            Lo.e("视频广告全部失效!");
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_videoID
            CSJAdvHelper.loadCSJVideo(activity, config.getCsj_videoID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("穿山甲视频广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setVideoFailPool(advEntity);
                    executeVideoAdv(activity,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_appID ,AdvConstant.gdt_videoID
            GDTAdvHelper.loadVideoAdv(activity, config.getGdt_appID(), config.getGdt_videoID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool().clearFailPool();//清空失败池
                    Lo.e("广点通视频广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool().setVideoFailPool(advEntity);
                    executeVideoAdv(activity,listener);
                }
            });
        }
    }




    /****************************以下方法是加载单个广告***************************/
    /**
     * 打开穿山甲视频广告
     * @param activity
     * @param frameLayout
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openCSJVideo(final AdvEntity advEntity, final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        //AdvConstant.csj_videoID
        CSJAdvHelper.loadCSJVideo(activity, config.getCsj_videoID(), gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool().clearFailPool();//情况失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool().setVideoFailPool(advEntity);
                    execute(activity, frameLayout, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /**
     * 打开穿山甲开屏广告
     * @param activity
     * @param frameLayout
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openCSJKPAdv(final AdvEntity advEntity,final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        //AdvConstant.csj_splashID
        CSJAdvHelper.loadCSJKPAdv(activity, frameLayout, config.getCsj_splashID(), gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool().clearFailPool();//情况失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool().setSplashFailPool(advEntity);
                    execute(activity, frameLayout, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开穿山甲插屏广告
     * @param activity
     * @param frameLayout
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openCSJCPAdv(final AdvEntity advEntity,final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        //AdvConstant.csj_cpID
        CSJAdvHelper.loadCSJCPAdv(activity,config.getCsj_cpID() , gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool().clearFailPool();//情况失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool().setCpFailPool(advEntity);
                    execute(activity, frameLayout, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开广点通视频广告
     * @param activity
     * @param frameLayout
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTVideoAdv(final AdvEntity advEntity,final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        //AdvConstant.gdt_appID,AdvConstant.gdt_videoID
        GDTAdvHelper.loadVideoAdv(activity, config.getGdt_appID(),config.getGdt_videoID() , gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool().clearFailPool();//情况失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool().setVideoFailPool(advEntity);
                    execute(activity, frameLayout, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开广点通开屏广告
     * @param activity
     * @param frameLayout
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTSplashAD(final AdvEntity advEntity,final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        //AdvConstant.gdt_kpID, AdvConstant.gdt_appID
        GDTAdvHelper.loadSplashAD(activity, frameLayout, config.getGdt_appID(), config.getGdt_kpID(),gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool().clearFailPool();//情况失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool().setSplashFailPool(advEntity);
                    execute(activity, frameLayout, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开广点通插屏广告
     * @param activity
     * @param frameLayout
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTCPAdv(final AdvEntity advEntity,final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        //AdvConstant.gdt_appID, AdvConstant.gdt_cpID
        GDTAdvHelper.loadCPAdv(activity, config.getGdt_appID(),config.getGdt_cpID(), gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool().clearFailPool();//情况失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool().setCpFailPool(advEntity);
                    execute(activity, frameLayout, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }


}
