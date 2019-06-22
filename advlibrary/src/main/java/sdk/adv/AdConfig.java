package sdk.adv;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sdk.adv.db.SPManager;
import sdk.adv.entity.AdvData;
import sdk.adv.manager.Lo;
import sdk.adv.tools.GsonUtils;
import sdk.adv.tools.XutilsHttp;

/**
 * @date {2019/6/17}
 */
public final class AdConfig {
    private String csj_appID;
    private String csj_bannerID;
    private String csj_splashID;
    private String csj_videoID;
    private String csj_cpID;
    private String gdt_appID;
    private String gdt_bannerID;
    private String gdt_kpID;
    private String gdt_cpID;
    private String gdt_videoID;

    /**
     * 初始化
     *
     * @param mContext
     */
    public void init(Context mContext,String url) {
        if(null==url||url.equals("")){
            getAssetsConfig(mContext,"AdvData.json");  //用于测试
            return;
        }
        //本地配置 "AdvData.json"
//        getAssetsConfig(mContext,url);  用于测试
        //网络配置 AdvConstant.ADV_SDK_URL
        getAdvEntityConfig(mContext, url);//初始化SDK，获取配置文件信息
    }
    /**日志开关*/
    public void setLogDebug(boolean isDebug){
        Lo.setDebug(isDebug);
    }

    public String getCsj_appID() {
        return csj_appID;
    }

    public void setCsj_appID(String csj_appID) {
        this.csj_appID = csj_appID;
    }

    public String getCsj_bannerID() {
        return csj_bannerID;
    }

    public void setCsj_bannerID(String csj_bannerID) {
        this.csj_bannerID = csj_bannerID;
    }

    public String getCsj_splashID() {
        return csj_splashID;
    }

    public void setCsj_splashID(String csj_splashID) {
        this.csj_splashID = csj_splashID;
    }

    public String getCsj_videoID() {
        return csj_videoID;
    }

    public void setCsj_videoID(String csj_videoID) {
        this.csj_videoID = csj_videoID;
    }

    public String getCsj_cpID() {
        return csj_cpID;
    }

    public void setCsj_cpID(String csj_cpID) {
        this.csj_cpID = csj_cpID;
    }

    public String getGdt_appID() {
        return gdt_appID;
    }

    public void setGdt_appID(String gdt_appID) {
        this.gdt_appID = gdt_appID;
    }

    public String getGdt_bannerID() {
        return gdt_bannerID;
    }

    public void setGdt_bannerID(String gdt_bannerID) {
        this.gdt_bannerID = gdt_bannerID;
    }

    public String getGdt_kpID() {
        return gdt_kpID;
    }

    public void setGdt_kpID(String gdt_kpID) {
        this.gdt_kpID = gdt_kpID;
    }

    public String getGdt_cpID() {
        return gdt_cpID;
    }

    public void setGdt_cpID(String gdt_cpID) {
        this.gdt_cpID = gdt_cpID;
    }

    public String getGdt_videoID() {
        return gdt_videoID;
    }

    public void setGdt_videoID(String gdt_videoID) {
        this.gdt_videoID = gdt_videoID;
    }

    public static class Builder {
        private String csj_appID;
        private String csj_bannerID;
        private String csj_splashID;
        private String csj_videoID;
        private String csj_cpID;

        private String gdt_appID;
        private String gdt_bannerID;
        private String gdt_kpID;
        private String gdt_cpID;
        private String gdt_videoID;

        public Builder() {
            /**穿山甲测试广告ID*/
            /*csj_appID = AdvConstant.csj_appID;
            csj_bannerID = AdvConstant.csj_bannerID;
            csj_splashID = AdvConstant.csj_splashID;
            csj_videoID = AdvConstant.csj_videoID;
            csj_cpID = AdvConstant.csj_cpID;*/
            /**广点通测试广告ID*/
            /*gdt_appID = AdvConstant.gdt_appID;
            gdt_bannerID = AdvConstant.gdt_bannerID;
            gdt_kpID = AdvConstant.gdt_kpID;
            gdt_cpID = AdvConstant.gdt_cpID;
            gdt_videoID = AdvConstant.gdt_videoID;*/
        }

        public AdConfig.Builder csj_appID(String csj_appID) {
            this.csj_appID = csj_appID;
            return this;
        }

        public AdConfig.Builder csj_bannerID(String csj_bannerID) {
            this.csj_bannerID = csj_bannerID;
            return this;
        }

        public AdConfig.Builder csj_splashID(String csj_splashID) {
            this.csj_splashID = csj_splashID;
            return this;
        }

        public AdConfig.Builder csj_cpID(String csj_cpID) {
            this.csj_cpID = csj_cpID;
            return this;
        }

        public AdConfig.Builder csj_videoID(String csj_videoID) {
            this.csj_videoID = csj_videoID;
            return this;
        }

        public AdConfig.Builder gdt_bannerID(String gdt_bannerID) {
            this.gdt_bannerID = gdt_bannerID;
            return this;
        }

        public AdConfig.Builder gdt_appID(String gdt_appID) {
            this.gdt_appID = gdt_appID;
            return this;
        }

        public AdConfig.Builder gdt_kpID(String gdt_kpID) {
            this.gdt_kpID = gdt_kpID;
            return this;
        }

        public AdConfig.Builder gdt_cpID(String gdt_cpID) {
            this.gdt_cpID = gdt_cpID;
            return this;
        }

        public AdConfig.Builder gdt_videoID(String gdt_videoID) {
            this.gdt_videoID = gdt_videoID;
            return this;
        }

        public AdConfig build() {
            AdConfig adConfig = new AdConfig();
            adConfig.setCsj_appID(csj_appID);
            adConfig.setCsj_bannerID(csj_bannerID);
            adConfig.setCsj_cpID(csj_cpID);
            adConfig.setCsj_splashID(csj_splashID);
            adConfig.setCsj_videoID(csj_videoID);
            adConfig.setGdt_appID(gdt_appID);
            adConfig.setGdt_bannerID(gdt_bannerID);
            adConfig.setGdt_cpID(gdt_cpID);
            adConfig.setGdt_kpID(gdt_kpID);
            adConfig.setGdt_videoID(gdt_videoID);

            return adConfig;
        }

    }


    /**
     * 获取网络上的广告配置文件
     */
    private static void getAdvEntityConfig(final Context mContext, String url) {
        if (null == url || "".equals(url)) {
            Lo.e("URL cannot be empty");
            return;
        }
        XutilsHttp.xUtilsGet(url, null, new XutilsHttp.XUtilsCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    AdvData advData = GsonUtils.josnToModule(result, AdvData.class);
                    if (null != advData) {
                        SPManager.getInstance(mContext).setConfig("AdvData", advData);
                    }
                } catch (Exception e) {
                    Lo.e(e.getMessage() + "");
                }
            }

            @Override
            public void onDownSuccess(File result) {
            }

            @Override
            public void onFail(String result) {
                Lo.e(result + "");
            }
        });
    }

    /**
     * 获取本地广告配置文件
     * 用于测试
     */
    public static void getAssetsConfig(Context mContext, String jsonName){
        try{
            StringBuilder str = new StringBuilder();
            InputStream inputStream = null;
            try {
                inputStream = mContext.getResources().getAssets().open(jsonName);
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String jsonLine;
                while ((jsonLine = reader.readLine()) != null) {
                    str.append(jsonLine);
                }
                reader.close();
                isr.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            List<T> advList = GsonUtils.getGsonList(str.toString(),clazz);
            AdvData advData = GsonUtils.josnToModule(str.toString(), AdvData.class);
            if (null != advData) {
                SPManager.getInstance(mContext).setConfig("AdvData", advData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
