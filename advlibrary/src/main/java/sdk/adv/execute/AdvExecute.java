package sdk.adv.execute;

import android.app.Activity;
import android.widget.FrameLayout;

import sdk.adv.AdConfig;
import sdk.adv.AdvConstant;
import sdk.adv.entity.AdvEntity;
import sdk.adv.entity.CsjAdvID;
import sdk.adv.entity.GdtAdvID;
import sdk.adv.entity.JzAdvID;
import sdk.adv.entity.LkAdvID;
import sdk.adv.interfaces.OnSuccessListener;
import sdk.adv.manager.CSJAdvHelper;
import sdk.adv.manager.GDTAdvHelper;
import sdk.adv.manager.JZAdvHelper;
import sdk.adv.manager.LKAdvHelper;
import sdk.adv.manager.Lo;
import sdk.adv.ui.SPAdDialog;


/**
 * @date {2019/6/12}
 * 广告执行类
 */
public class AdvExecute {
    private static AdvExecute advExecute;
    private static AdConfig config;

    private static CsjAdvID csjAdvID; //穿山甲ID
    /**广点通ID*/
    private static GdtAdvID gdtAdvID;//广点通ID
    /**精众*/
    private static JzAdvID jzAdvID ;//精众AdvID
    /**链咖*/
    private static LkAdvID lkAdvID;//链咖AdvID
    public interface OnCompleteListener{
        void onComplete(int gold, boolean isNormal);
    }

    public static AdvExecute create() {
        if (advExecute == null) {
            config = AdConfig.adConfig;
            if(null!=config){
                csjAdvID = config.getCsjAdvID();
                gdtAdvID = config.getGdtAdvID();
                jzAdvID = config.getJzAdvID();
                lkAdvID = config.getLkAdvID();
            }
            advExecute = new AdvExecute();
        }
        return advExecute;
    }

    private boolean isNull(Object obj){
        if(null==obj)return true;
        return false;
    }
    /**
     * 广告:所有广告
     * @param activity    当前需要展示的activity
     * @param gold        奖励的金币
     * @param listener
     * @TODO 参数说明：
     * 穿山甲视频广告：	1
     * 穿山甲开屏广告:		2
     * 穿山甲插屏广告:		3
     * 广点通视频广告:		4
     * 广点通开屏广告:		5
     * 广点通插屏广告:		6
     * 精众视频广告:		7
     * 链咖视频广告:		8
     */
    public void execute(Activity activity, int gold, OnCompleteListener listener) {
//        if(isNull(config)){
//            Lo.e("配置文件为空,不加载广告！");
////            if(null!=listener)listener.onComplete(0,false);
//            return;
//        }
        AdvEntity advEntity = AdvPools.getAdvPool(activity).getAllAdv();
        if(null==advEntity){
            Lo.e("广告全部失效!");
            AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
            listener.onComplete(0,true);
            return;
        }
        Lo.i("展示广告type：" + advEntity.getAdvType());
        switch (advEntity.getAdvType()) {
            case 1: //穿山甲视频
//                openCSJVideo(advEntity,activity, frameLayout, gold, true, listener);
                openCSJVideo(advEntity,activity, gold, true, listener);
                break;
            case 2: //穿山甲开屏
//                openCSJKPAdv(advEntity,activity, frameLayout, gold, true, listener);
                openCSJKPAdv(advEntity,activity, gold, true, listener);
                break;
            case 3: //穿山甲插屏
//                openCSJCPAdv(advEntity,activity, frameLayout, gold, true, listener);
                openCSJCPAdv(advEntity,activity, gold, true, listener);
                break;
            case 4: //广点通视频
//                Lo.e("广点通的视频广告暂未开通!");
//                openGDTVideoAdv(advEntity,activity, frameLayout, gold, true, listener);
                openGDTVideoAdv(advEntity,activity, gold, true, listener);
                break;
            case 5: //广点通开屏
//                openGDTSplashAD(advEntity,activity, frameLayout, gold, true, listener);
                openGDTSplashAD(advEntity,activity, gold, true, listener);
                break;
            case 6: //广点通插屏
//                openGDTCPAdv(advEntity,activity, frameLayout, gold, true, listener);
                openGDTCPAdv(advEntity,activity, gold, true, listener);
                break;
            case 7: //精众视频
                openJZVideoAdv(advEntity,activity, gold, true, listener);
//                openJZVideoAdv(advEntity,activity, frameLayout, gold, true, listener);
                break;
            case 8: //链咖视频
                openLKVideoAdv(advEntity,activity, gold, true, listener);
//                openLKVideoAdv(advEntity,activity, frameLayout, gold, true, listener);
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
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(null!=listener)listener.onComplete(0,false);
            return;
        }
        final AdvEntity advEntity = AdvPools.getAdvPool(activity).getBannerAdv();
        if(null==advEntity){
            Lo.e("Banner广告全部失效!");
            AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_bannerID
            CSJAdvHelper.loadCSJBannerAdv(activity, csjAdvID.getCsj_bannerID(), frameLayout, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("穿山甲广告加载成功!");
                    if(listener!=null)listener.onComplete(0,true);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setBannerFailPool(advEntity);
                    executeBanner(activity,frameLayout,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_appID, AdvConstant.gdt_bannerID
            GDTAdvHelper.loadBanner(activity, frameLayout,gdtAdvID.getGdt_appID(),gdtAdvID.getGdt_bannerID() , new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("广点通广告加载成功!");
                    if(listener!=null)listener.onComplete(0,true);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setBannerFailPool(advEntity);
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
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(null!=listener)listener.onComplete(0,false);
            return;
        }
        final AdvEntity advEntity = AdvPools.getAdvPool(activity).getCPAdv();
        if(null==advEntity){
            Lo.e("插屏广告全部失效!");
            AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_cpID
            CSJAdvHelper.loadCSJCPAdv(activity, csjAdvID.getCsj_cpID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("穿山甲插屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setCpFailPool(advEntity);
                    executeCpAdv(activity,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_appID, AdvConstant.gdt_cpID
            GDTAdvHelper.loadCPAdv(activity, gdtAdvID.getGdt_appID(),gdtAdvID.getGdt_cpID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("广点通插屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setCpFailPool(advEntity);
                    executeCpAdv(activity,listener);
                }
            });
        }
    }

    private SPAdDialog spAdDialog;
    public void executeSplashAdv(final Activity activity, final OnCompleteListener listener){
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
            if(null!=listener)listener.onComplete(0,false);
            return;
        }
        final AdvEntity advEntity = AdvPools.getAdvPool(activity).getSpashAdv();
        if(null==advEntity){
            Lo.e("开屏广告全部失效!");
            if(null!=spAdDialog&&spAdDialog.isShowing())spAdDialog.dismiss();
            AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
            listener.onComplete(0,true);
            return;
        }
        if(null==spAdDialog)spAdDialog = new SPAdDialog(activity);
        spAdDialog.setOnCompleteListener(new SPAdDialog.OnCompleteListener() {
            @Override
            public void onComplete(int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(null!=spAdDialog&&spAdDialog.isShowing()){
                    spAdDialog.dismiss();
                }
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail() {
                AdvPools.getAdvPool(activity).setSplashFailPool(advEntity);
                if(null!=spAdDialog&&spAdDialog.isShowing()){
                    spAdDialog.dismiss();
                }
                executeSplashAdv(activity,listener);
            }
        });
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            spAdDialog.showDialog(AdvConstant.CSJ_TYPE,0,config);
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            spAdDialog.showDialog(AdvConstant.GDT_TYPE,0,config);
        }
    }
    /***
     * 执行加载所有开屏广告
     * @param activity
     * @param frameLayout
     * @param listener
     */
    public void executeSplashAdv(final Activity activity, final FrameLayout frameLayout,final OnCompleteListener listener){
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
            if(null!=listener)listener.onComplete(0,false);
            return;
        }
        final AdvEntity advEntity = AdvPools.getAdvPool(activity).getSpashAdv();
        if(null==advEntity){
            Lo.e("开屏广告全部失效!");
            AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
            listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_splashID
            CSJAdvHelper.loadCSJKPAdv(activity, frameLayout, csjAdvID.getCsj_splashID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("穿山甲开屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setSplashFailPool(advEntity);
                    executeSplashAdv(activity,frameLayout,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_kpID, AdvConstant.gdt_appID
            GDTAdvHelper.loadSplashAD(activity, frameLayout, gdtAdvID.getGdt_appID(),gdtAdvID.getGdt_splashID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("广点通开屏广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setSplashFailPool(advEntity);
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
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(null!=listener)listener.onComplete(0,false);
            return;
        }
        final AdvEntity advEntity = AdvPools.getAdvPool(activity).getVideoAdv();
        if(null==advEntity){
            Lo.e("视频广告全部失效!");
            AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
            if(null!=listener)listener.onComplete(0,true);
            return;
        }
        if(advEntity.getAdvType()==AdvConstant.CSJ_TYPE){
            //AdvConstant.csj_videoID
            CSJAdvHelper.loadCSJVideo(activity, csjAdvID.getCsj_videoID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("穿山甲视频广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    executeVideoAdv(activity,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.GDT_TYPE){
            //AdvConstant.gdt_appID ,AdvConstant.gdt_videoID
            GDTAdvHelper.loadVideoAdv(activity, gdtAdvID.getGdt_appID(), gdtAdvID.getGdt_videoID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("广点通视频广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    executeVideoAdv(activity,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.JZ_TYPE){
            //精众视频
            JZAdvHelper.loadVideoAdv(activity, jzAdvID.getJz_appID(), jzAdvID.getJz_posID(), 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    Lo.e("精众视频广告加载成功!");
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    executeVideoAdv(activity,listener);
                }
            });
        }else if(advEntity.getAdvType()==AdvConstant.LK_TYPE){
            //链咖视频
            LKAdvHelper.loadLKVideoAdv(lkAdvID.getLk_posID(), "", 0, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    //视频播放完成
                    AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                    LKAdvHelper.destoryVideo();//销毁一下
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    //视频加载失败
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    executeVideoAdv(activity,listener);
                }
            });
        }
    }

    /****************************以下方法是加载单个广告***************************/
    /**
     * 打开穿山甲视频广告
     * @param activity
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openCSJVideo(final AdvEntity advEntity, final Activity activity, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(null!=listener)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.csj_videoID
        CSJAdvHelper.loadCSJVideo(activity, csjAdvID.getCsj_videoID(), gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    execute(activity, gold, listener);
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
    public void openCSJKPAdv(final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.csj_splashID
        CSJAdvHelper.loadCSJKPAdv(activity, frameLayout, csjAdvID.getCsj_splashID(), gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,false);
            }
        });
    }
    /**
     * 打开穿山甲开屏广告
     * @param activity
     * @param isLoop
     * @param listener
     */
    public void openCSJKPAdv(final AdvEntity advEntity,final Activity activity, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        spAdDialog = new SPAdDialog(activity);
        spAdDialog.setOnCompleteListener(new SPAdDialog.OnCompleteListener() {
            @Override
            public void onComplete(int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
                if(null!=spAdDialog&&spAdDialog.isShowing())spAdDialog.dismiss();
            }
            @Override
            public void onFail() {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setSplashFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
        spAdDialog.showDialog(AdvConstant.CSJ_TYPE,0,config);

    }

    /***
     * 打开穿山甲插屏广告
     * @param activity
     * @param
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openCSJCPAdv(final AdvEntity advEntity,final Activity activity  , final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.csj_cpID
        CSJAdvHelper.loadCSJCPAdv(activity,csjAdvID.getCsj_cpID() , gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setCpFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开广点通视频广告
     * @param activity
     * @param
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTVideoAdv(final AdvEntity advEntity,final Activity activity,  final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.gdt_appID,AdvConstant.gdt_videoID
        GDTAdvHelper.loadVideoAdv(activity, gdtAdvID.getGdt_appID(),gdtAdvID.getGdt_videoID() , gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开广点通开屏广告
     * @param activity
     * @param
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTSplashAD(final Activity activity, final FrameLayout frameLayout, final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.gdt_kpID, AdvConstant.gdt_appID
        GDTAdvHelper.loadSplashAD(activity, frameLayout, gdtAdvID.getGdt_appID(), gdtAdvID.getGdt_splashID(),gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,false);
            }
        });
    }
    /***
     * 打开广点通开屏广告
     * @param activity
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTSplashAD(final AdvEntity advEntity,final Activity activity,  final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        spAdDialog = new SPAdDialog(activity);
        spAdDialog.setOnCompleteListener(new SPAdDialog.OnCompleteListener() {
            @Override
            public void onComplete(int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
                if(null!=spAdDialog&&spAdDialog.isShowing())spAdDialog.dismiss();
            }
            @Override
            public void onFail() {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setSplashFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
        spAdDialog.showDialog(AdvConstant.GDT_TYPE,0,config);

    }

    /***
     * 打开广点通插屏广告
     * @param activity
     * @param
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openGDTCPAdv(final AdvEntity advEntity,final Activity activity  , final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.gdt_appID, AdvConstant.gdt_cpID
        GDTAdvHelper.loadCPAdv(activity, gdtAdvID.getGdt_appID(),gdtAdvID.getGdt_cpID(), gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setCpFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /***
     * 打开精众视频广告
     * @param activity
     * @param
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openJZVideoAdv(final AdvEntity advEntity,final Activity activity,  final int gold, final boolean isLoop, final OnCompleteListener listener) {
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        //AdvConstant.gdt_appID,AdvConstant.gdt_videoID
        JZAdvHelper.loadVideoAdv(activity, jzAdvID.getJz_appID(),jzAdvID.getJz_posID() , gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });
    }

    /**
     * 打开链咖视频ADV
     * @param advEntity
     * @param activity
     * @param
     * @param gold
     * @param isLoop
     * @param listener
     */
    public void openLKVideoAdv(final AdvEntity advEntity,final Activity activity,final int gold, final boolean isLoop, final OnCompleteListener listener){
        if(isNull(config)){
            Lo.e("配置文件为空,不加载广告！");
//            if(listener!=null)listener.onComplete(gold,false);
            return;
        }
        LKAdvHelper.loadLKVideoAdv(lkAdvID.getLk_posID(), "", gold, new OnSuccessListener() {
            @Override
            public void onComplete(int type, int gold, boolean isNormal) {
                AdvPools.getAdvPool(activity).clearFailPool();//清空失败池
                if(listener!=null)listener.onComplete(gold,isNormal);
            }
            @Override
            public void onFail(int type) {
                if (isLoop) {
                    AdvPools.getAdvPool(activity).setVideoFailPool(advEntity);
                    execute(activity, gold, listener);
                }else{
                    if(listener!=null)listener.onComplete(gold,false);
                }
            }
        });

    }


}
