package sdk.adv;

import android.app.Application;

import org.xutils.x;

import sdk.adv.manager.CsjAdvManager;
import sdk.adv.manager.LogHelper;


public abstract class AdvAppLication extends Application {
    public static AdConfig adConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
//        initModuleADV();//依赖module方式初始化
        initADV();//网络依赖配置初始化
    }

    //使用Module工程方式请这样初始化,在AdConstant里面手动配置广告ID
    private void initModuleADV() {
        adConfig = new AdConfig.Builder().build();
        adConfig.init(this);
        CsjAdvManager.init(this, adConfig.getCsj_appID()); //初始化穿山甲广告

    }

    protected abstract AdConfig initAdvConfig();

    //网络依赖配置初始化
    private void initADV() {
        AdConfig advConfig = initAdvConfig();
        if(null==advConfig){
            LogHelper.e("请检查是否初始化AdConfig配置？");
            return ;
        }
        adConfig = advConfig;
        adConfig.init(this);
        /*AdConfig adConfig = new AdConfig.Builder()
                //穿山甲的ID
                .csj_appID("").csj_bannerID("").csj_cpID("")
                .csj_splashID("").csj_videoID("")
                //广点通的ID
                .gdt_appID("").gdt_bannerID("").gdt_cpID("")
                .gdt_kpID("").gdt_videoID("")
                .build();//构建*/
        CsjAdvManager.init(this, advConfig.getCsj_appID()); //初始化穿山甲广告
    }

}
