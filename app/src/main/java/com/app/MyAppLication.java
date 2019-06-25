package com.app;

import sdk.adv.AdConfig;
import sdk.adv.AdvAppLication;
/**
 * @date {2019/6/21}
 */
public class MyAppLication extends AdvAppLication{

    /**穿山甲正式广告*/
    private static final String CSJ_APP_ID = "5018783";//appID
    private static final String CSJ_BANNER_ID = "918783581";//bannerID
    private static final String CSJ_SPLASH_ID = "818783801";//开屏ID
    private static final String CSJ_VIDEO_ID = "918783612";//视频ID
    private static final String CSJ_CP_ID = "918783672";//插屏ID
    /**广点通正式广告*/
    private static final String GDT_APP_ID = "1108967774";//appID
    private static final String GDT_BANNER_POSID = "1000866632372105";//Banner广告
    private static final String GDT_CP_POSID = "9050963632675168";//插屏广告ID
    private static final String GDT_KP_POSID = "5080262484266590";//开屏广告ID
    private static final String GDT_VIDEO_POSID = "6000625736289442";//视频广告ID


    //配置文件地址,如果没有配置,默认使用本地配置文件地址
    @Override
    protected String getUrl() {
        String ADV_SDK_URL = "https://zh-app-store.oss-cn-shenzhen.aliyuncs.com/adConfig/AdvData.json";
        return ADV_SDK_URL;
//        return ""; //返回"",会自动去选择assets目录下面(AdcData.json)测试配置文件,请保证文件存在
    }

    @Override
    protected AdConfig initAdvConfig() {
        return null;//返回空，去拿网络上的ID
        /*return new AdConfig.Builder()
                //穿山甲的ID
                .csj_appID(CSJ_APP_ID).csj_bannerID(CSJ_BANNER_ID).csj_cpID(CSJ_CP_ID)
                .csj_splashID(CSJ_SPLASH_ID).csj_videoID(CSJ_VIDEO_ID)
                //广点通的ID
                .gdt_appID(GDT_APP_ID).gdt_bannerID(GDT_BANNER_POSID).gdt_cpID(GDT_CP_POSID)
                .gdt_kpID(GDT_KP_POSID).gdt_videoID(GDT_VIDEO_POSID)
                .build();//构建*/
    }

    @Override
    protected boolean isDebug() {
        //测试阶段打开，可以通过日志排查问题，上线时去除该调用
        return true;
    }
}
