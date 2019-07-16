package sdk.adv;

import android.content.Context;

import com.lkvideo.sdk.LKVideoSDK;
import com.qq.e.comm.util.StringUtil;

import sdk.adv.db.SPManager;
import sdk.adv.entity.AdvData;
import sdk.adv.entity.AdvID;
import sdk.adv.manager.CsjAdvManager;
import sdk.adv.manager.Lo;
import sdk.adv.tools.GsonUtils;

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
    private String lk_appID;
    private String lk_appKey;
    private String adPosId;//广告位ID

    private String jz_appID;
    private String jz_posID;

    public static AdConfig adConfig;

    /**
     * 初始化
     "csj_appID":"5019722",
     "csj_bannerID":"919722591",
     "csj_cpID":"919722786",
     "csj_splashID":"819722380",
     "csj_videoID":"919722997",

     "csj_appID":"5021440",
     "csj_bannerID":"921440226",
     "csj_cpID":"919722786",
     "csj_splashID":"821440890",
     "csj_videoID":"921440322",
     * @param mContext
     */
    public static void init(Context mContext,String advStr,boolean isdebug) {
        //网络配置 AdvConstant.ADV_SDK_URL
        if(StringUtil.isEmpty(advStr)) {
            Lo.e("初始配置文件失败,参数adv:"+advStr);
            return;
        }
        Lo.setDebug(isdebug);/**日志开关*/
        toGson(mContext, advStr);//初始化SDK，获取配置文件信息
    }

    public String getLk_appID() {
        return lk_appID;
    }
    public void setLk_appID(String lk_appID) {
        this.lk_appID = lk_appID;
    }
    public String getLk_appKey() {
        return lk_appKey;
    }
    public void setLk_appKey(String lk_appKey) {
        this.lk_appKey = lk_appKey;
    }

    public String getJz_appID() {
        return jz_appID;
    }

    public void setJz_appID(String jz_appID) {
        this.jz_appID = jz_appID;
    }

    public String getJz_posID() {
        return jz_posID;
    }

    public void setJz_posID(String jz_posID) {
        this.jz_posID = jz_posID;
    }

    public String getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(String adPosId) {
        this.adPosId = adPosId;
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

        private String jz_appID;
        private String jz_posID;

        /**链咖*/
        private String lk_appID;
        private String lk_appKey;
        private String adPosId;//广告位ID

        public Builder() {
        }

        public AdConfig.Builder setLk_appID(String lk_appID) {
            this.lk_appID = lk_appID;
            return this;
        }

        public AdConfig.Builder setJz_appID(String jz_appID) {
            this.jz_appID = jz_appID;
            return this;
        }

        public AdConfig.Builder setJz_posID(String jz_posID) {
            this.jz_posID = jz_posID;
            return this;
        }

        public AdConfig.Builder setLk_appKey(String lk_appKey) {
            this.lk_appKey = lk_appKey;
            return this;
        }
        public AdConfig.Builder setAdPosId(String adPosId) {
            this.adPosId = adPosId;
            return this;
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
            adConfig.setJz_appID(jz_appID);
            adConfig.setJz_posID(jz_posID);
            adConfig.setLk_appID(lk_appID);
            adConfig.setLk_appKey(lk_appKey);
            adConfig.setAdPosId(adPosId);
            return adConfig;
        }

    }

    private static void toGson(final Context mContext, String advStr) {
        if (null == advStr || "".equals(advStr)) {
            Lo.e("advStr 广告配置数据为空!");
            return;
        }
        try{
            AdvData advData = GsonUtils.josnToModule(advStr, AdvData.class);
            if (null != advData) {
                SPManager.getInstance(mContext).setConfig("AdvData", advData);
                AdvID advID = advData.getAdvID();
                adConfig = new AdConfig.Builder()
                        //穿山甲的ID
                        .csj_appID(advID.getCsj_appID())
                        .csj_bannerID(advID.getCsj_bannerID())
                        .csj_cpID(advID.getCsj_cpID())
                        .csj_splashID(advID.getCsj_splashID())
                        .csj_videoID(advID.getCsj_videoID())
                        //广点通的ID
                        .gdt_appID(advID.getGdt_appID())
                        .gdt_bannerID(advID.getGdt_bannerID())
                        .gdt_cpID(advID.getGdt_cpID())
                        .gdt_kpID(advID.getGdt_splashID())
                        .gdt_videoID(advID.getGdt_videoID())
                        //精众
                        .setJz_appID(advID.getJz_appID())
                        .setJz_posID(advID.getJz_posID())
                        //链咖ID
                        .setLk_appID(advID.getLk_appID())
                        .setLk_appKey(advID.getLk_appKey())
                        .setAdPosId(advID.getAdPosId())
                        .build();
                CsjAdvManager.init(mContext, advID.getCsj_appID()); //初始化穿山甲广告
                LKVideoSDK.init(mContext,advID.getLk_appID(),advID.getLk_appKey());//初始化链咖
            }else{
                Lo.e("配置文件解析出来的数据为空！>>"+advStr);
            }
        }catch (Exception e){
            e.printStackTrace();
            Lo.e("解析数据是发生异常:"+e.getMessage());
        }

    }

}
