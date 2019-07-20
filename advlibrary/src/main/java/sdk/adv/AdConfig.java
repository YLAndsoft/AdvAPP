package sdk.adv;

import android.content.Context;

import com.lkvideo.sdk.LKVideoSDK;
import com.qq.e.comm.util.StringUtil;

import sdk.adv.db.SPManager;
import sdk.adv.entity.AdvData;
import sdk.adv.entity.AdvID;
import sdk.adv.entity.CsjAdvID;
import sdk.adv.entity.GdtAdvID;
import sdk.adv.entity.JzAdvID;
import sdk.adv.entity.LkAdvID;
import sdk.adv.manager.CsjAdvManager;
import sdk.adv.manager.Lo;
import sdk.adv.tools.GsonUtils;

/**
 * @date {2019/6/17}
 */
public final class AdConfig {
    private CsjAdvID csjAdvID; //穿山甲ID

    private GdtAdvID gdtAdvID;//广点通ID

    private LkAdvID lkAdvID;//链咖AdvID

    private JzAdvID jzAdvID ;//精众AdvID


    public static AdConfig adConfig;

    /**
     * 初始化 ：同步
     * @param mContext
     */
    public static void initStr(Context mContext,String advStr,boolean isdebug) {
        //网络配置 AdvConstant.ADV_SDK_URL
        if(StringUtil.isEmpty(advStr)) {
            Lo.e("初始配置文件失败,参数adv:"+advStr);
            return;
        }
        Lo.setDebug(isdebug);/**日志开关*/
        toGson(mContext, advStr);//初始化SDK，获取配置文件信息
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
                        .setCsjAdvID(advID.getCsjAdvID())
                        .setGdtAdvID(advID.getGdtAdvID())
                        .setJzAdvID(advID.getJzAdvID())
                        .setLkAdvID(advID.getLkAdvID())
                        .build();
                CsjAdvManager.init(mContext, advID.getCsjAdvID().getCsj_appID()); //初始化穿山甲广告
                LKVideoSDK.init(mContext,advID.getLkAdvID().getLk_appID(),advID.getLkAdvID().getLk_appKey());//初始化链咖
            }else{
                Lo.e("配置文件解析出来的数据为空！>>"+advStr);
            }
        }catch (Exception e){
            e.printStackTrace();
            Lo.e("解析数据是发生异常:"+e.getMessage());
        }

    }

    public CsjAdvID getCsjAdvID() {
        return csjAdvID;
    }

    public GdtAdvID getGdtAdvID() {
        return gdtAdvID;
    }

    public LkAdvID getLkAdvID() {
        return lkAdvID;
    }

    public JzAdvID getJzAdvID() {
        return jzAdvID;
    }

    public void setCsjAdvID(CsjAdvID csjAdvID) {
        this.csjAdvID = csjAdvID;
    }

    public void setGdtAdvID(GdtAdvID gdtAdvID) {
        this.gdtAdvID = gdtAdvID;
    }

    public void setLkAdvID(LkAdvID lkAdvID) {
        this.lkAdvID = lkAdvID;
    }

    public void setJzAdvID(JzAdvID jzAdvID) {
        this.jzAdvID = jzAdvID;
    }

    public static class Builder {
        private CsjAdvID csjAdvID; //穿山甲ID
        private GdtAdvID gdtAdvID;//广点通ID
        private LkAdvID lkAdvID;//链咖AdvID
        private JzAdvID jzAdvID ;//精众AdvID

        public Builder() {
        }
        public AdConfig.Builder setCsjAdvID(CsjAdvID csjAdvID) {
            this.csjAdvID = csjAdvID;
            return this;
        }
        public AdConfig.Builder setGdtAdvID(GdtAdvID gdtAdvID) {
            this.gdtAdvID = gdtAdvID;
            return this;
        }
        public AdConfig.Builder setLkAdvID(LkAdvID lkAdvID) {
            this.lkAdvID = lkAdvID;
            return this;
        }
        public AdConfig.Builder setJzAdvID(JzAdvID jzAdvID) {
            this.jzAdvID = jzAdvID;
            return this;
        }


        public AdConfig build() {
            AdConfig adConfig = new AdConfig();
            adConfig.setCsjAdvID(csjAdvID);
            adConfig.setGdtAdvID(gdtAdvID);
            adConfig.setLkAdvID(lkAdvID);
            adConfig.setJzAdvID(jzAdvID);
            return adConfig;
        }

    }

}
