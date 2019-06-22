package sdk.adv;

/**
 * @date {2019/6/11}
 * 配置文件
 */
public class AdvConstant {

    public static final boolean ADV_DEBUG = true;//全局日志开关,上线之前请关闭

    private static final boolean isDEBUG_ID = false;//广告ID测试开关

    /**广告SDK的配置文件*/
//    public static final String ADV_SDK_URL = "https://zh-app-store.oss-cn-shenzhen.aliyuncs.com/adConfig/adv.json";
//    public static final String ADV_SDK_URL = "https://zh-app-store.oss-cn-shenzhen.aliyuncs.com/adConfig/AdvData.json";

    /**穿山甲正式广告*/
    private static final String CSJ_APP_ID = "";//appID
    private static final String CSJ_BANNER_ID = "";//bannerID
    private static final String CSJ_SPLASH_ID = "";//开屏ID
    private static final String CSJ_VIDEO_ID = "";//视频ID
    private static final String CSJ_CP_ID = "";//插屏ID
    /**广点通正式广告*/
    private static final String GDT_APP_ID = "";//appID
    private static final String GDT_BANNER_POSID = "";//Banner广告
    private static final String GDT_CP_POSID = "";//插屏广告ID
    private static final String GDT_KP_POSID = "";//开屏广告ID
    private static final String GDT_VIDEO_POSID = "";//视频广告ID

    /**以下代码位不用修改**/
    public static final int CSJ_VIDEO_TYPE = 0;//穿山甲视频广告
    public static final int CSJ_KP_TYPE = 1;//穿山甲开屏广告
    public static final int CSJ_CP_TYPE = 2;//穿山甲插屏广告
    public static final int GDT_VIDEO_TYPE = 3;//广点通视频广告
    public static final int GDT_KP_TYPE = 4;//广点通开屏广告
    public static final int GDT_CP_TYPE = 5;//广点通插屏广告

    /**广告联盟ID*/
    public static final int CSJ_TYPE = 11; //穿山甲
    public static final int GDT_TYPE = 22;//广点通
    public static final int ADV_TYPE = 33;//以后增加，往下累加

    /**穿山甲测试广告ID*/
    private static final String CSJ_TEST_APP_ID = "5019722";//appID
    private static final String CSJ_TEST_BANNER_ID = "919722591";//bannerID
    private static final String CSJ_TEST_SPLASH_ID = "819722380";//开屏ID
    private static final String CSJ_TEST_VIDEO_ID = "919722997";//视频ID
    private static final String CSJ_TEST_CP_ID = "919722786";//插屏ID
    /**广点通测试广告ID*/
    private static final String GDT_TEST_APP_ID = "1109171015";//appID
    private static final String GDT_TEST_BANNER_POSID = "1000866632372105";//Banner广告
    private static final String GDT_TEST_CP_POSID = "9050963632675168";//插屏广告ID
    private static final String GDT_TEST_KP_POSID = "2030861632674187";//开屏广告ID
    private static final String GDT_TEST_VIDEO_POSID = "6000625736289442";//视频广告ID

    public static final String csj_appID  = isDEBUG_ID?CSJ_APP_ID:CSJ_TEST_APP_ID;
    public static final String csj_bannerID  = isDEBUG_ID?CSJ_BANNER_ID:CSJ_TEST_BANNER_ID;
    public static final String csj_splashID  = isDEBUG_ID?CSJ_SPLASH_ID:CSJ_TEST_SPLASH_ID;
    public static final String csj_videoID  = isDEBUG_ID?CSJ_VIDEO_ID:CSJ_TEST_VIDEO_ID;
    public static final String csj_cpID  = isDEBUG_ID?CSJ_CP_ID:CSJ_TEST_CP_ID;

    public static final String gdt_appID  = isDEBUG_ID?GDT_APP_ID:GDT_TEST_APP_ID;
    public static final String gdt_bannerID  = isDEBUG_ID?GDT_BANNER_POSID:GDT_TEST_BANNER_POSID;
    public static final String gdt_kpID  = isDEBUG_ID?GDT_KP_POSID:GDT_TEST_KP_POSID;
    public static final String gdt_cpID  = isDEBUG_ID?GDT_CP_POSID:GDT_TEST_CP_POSID;
    public static final String gdt_videoID  = isDEBUG_ID?GDT_VIDEO_POSID:GDT_TEST_VIDEO_POSID;


}
