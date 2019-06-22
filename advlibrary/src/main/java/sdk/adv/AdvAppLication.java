package sdk.adv;

import android.app.Application;

import org.xutils.x;

import sdk.adv.manager.CsjAdvManager;
import sdk.adv.manager.Lo;


public abstract class AdvAppLication extends Application {
    public static AdConfig adConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        initADV();//网络依赖配置初始化
    }
    //配置文件地址,如果没有配置,默认使用本地配置文件地址
    protected abstract String getUrl();
    //配置联盟广告ID抽象
    protected abstract AdConfig initAdvConfig();
    //是否开启debug
    protected abstract boolean isDebug();
    //网络依赖配置初始化
    private void initADV() {
        String url = getUrl();
        boolean debug = isDebug();
        x.Ext.init(this);
        x.Ext.setDebug(debug);
        AdConfig advConfig = initAdvConfig();
        if(null==advConfig){
            Lo.e("请检查是否初始化AdConfig配置？");
            return ;
        }
        adConfig = advConfig;
        adConfig.init(this,url);
        adConfig.setLogDebug(debug);
        CsjAdvManager.init(this, advConfig.getCsj_appID()); //初始化穿山甲广告
    }

}
