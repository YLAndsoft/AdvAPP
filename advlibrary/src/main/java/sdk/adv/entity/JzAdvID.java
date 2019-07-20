package sdk.adv.entity;

import java.io.Serializable;

/**
 * @date {2019/7/20}
 * 精众ID
 */
public class JzAdvID implements Serializable {

    private String jz_appID; //应用ID
    private String jz_posID;//视频ID
    private String tips;//说明

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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
}
