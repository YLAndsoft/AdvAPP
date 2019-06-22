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
        String url = getUrl();
        initADV(url);//网络依赖配置初始化
    }
    //配置文件地址,如果没有配置,默认使用本地配置文件地址
    protected abstract String getUrl();
    //配置联盟广告ID抽象
    protected abstract AdConfig initAdvConfig();
    //网络依赖配置初始化
    private void initADV(String url) {
        AdConfig advConfig = initAdvConfig();
        if(null==advConfig){
            LogHelper.e("请检查是否初始化AdConfig配置？");
            return ;
        }
        adConfig = advConfig;
        adConfig.init(this,url);
        CsjAdvManager.init(this, advConfig.getCsj_appID()); //初始化穿山甲广告
    }

}
