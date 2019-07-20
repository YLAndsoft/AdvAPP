package sdk.adv.entity;

import java.io.Serializable;

/**
 * @date {2019/7/20}
 * 穿山甲ID
 */
public class CsjAdvID implements Serializable {


    private String csj_appID; //应用ID
    private String csj_bannerID; //banner ID
    private String csj_splashID; //开屏ID
    private String csj_videoID; //视频ID
    private String csj_cpID;//插屏ID
    private String tips;//说明

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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
}
