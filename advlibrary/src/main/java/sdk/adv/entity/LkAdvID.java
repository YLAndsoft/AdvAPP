package sdk.adv.entity;

import java.io.Serializable;

/**
 * @date {2019/7/20}
 */
public class LkAdvID implements Serializable {
    private String lk_appID; //应用ID
    private String lk_appKey;//appKey
    private String lk_posID;//视频ID
    private String tips;//说明

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    public String getLk_posID() {
        return lk_posID;
    }

    public void setLk_posID(String lk_posID) {
        this.lk_posID = lk_posID;
    }
}
