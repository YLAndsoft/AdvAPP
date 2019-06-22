package com.app;

import sdk.adv.AdConfig;
import sdk.adv.AdvAppLication;
/**
 * @date {2019/6/21}
 */
public class MyAppLication extends AdvAppLication{

    /**穿山甲广告ID*/
    private final String CSJ_TEST_APP_ID = "5019722";//appID
    private  final String CSJ_TEST_BANNER_ID = "919722591";//bannerID
    private  final String CSJ_TEST_SPLASH_ID = "819722380";//开屏ID
    private  final String CSJ_TEST_VIDEO_ID = "919722997";//视频ID
    private  final String CSJ_TEST_CP_ID = "919722786";//插屏ID
    /**广点通广告ID*/
    private  final String GDT_TEST_APP_ID = "";//appID 1109171015
    private  final String GDT_TEST_BANNER_POSID = "";//Banner广告 1000866632372105
    private  final String GDT_TEST_CP_POSID = "";//插屏广告ID 9050963632675168
    private  final String GDT_TEST_KP_POSID = "";//开屏广告ID 2030861632674187
    private  final String GDT_TEST_VIDEO_POSID = "";//视频广告ID

    //配置文件地址,如果没有配置,默认使用本地配置文件地址
    @Override
    protected String getUrl() {
        String ADV_SDK_URL = "https://zh-app-store.oss-cn-shenzhen.aliyuncs.com/adConfig/AdvData.json";
        return ADV_SDK_URL;
    }

    @Override
    protected AdConfig initAdvConfig() {
        return new AdConfig.Builder()
                //穿山甲的ID
                .csj_appID(CSJ_TEST_APP_ID).csj_bannerID(CSJ_TEST_BANNER_ID).csj_cpID(CSJ_TEST_CP_ID)
                .csj_splashID(CSJ_TEST_SPLASH_ID).csj_videoID(CSJ_TEST_VIDEO_ID)
                //广点通的ID
                .gdt_appID(GDT_TEST_APP_ID).gdt_bannerID(GDT_TEST_BANNER_POSID).gdt_cpID(GDT_TEST_CP_POSID)
                .gdt_kpID(GDT_TEST_KP_POSID).gdt_videoID(GDT_TEST_VIDEO_POSID)
                .build();//构建
    }

    @Override
    protected boolean isDebug() {
        return true;
    }
}
