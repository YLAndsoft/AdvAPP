package sdk.adv.entity;

import java.io.Serializable;

/**
 * @date {2019/6/25}
 */
public class AdvID implements Serializable{
    /**穿山甲ID*/
    private String csj_appID;
    private String csj_bannerID;
    private String csj_cpID;
    private String csj_splashID;
    private String csj_videoID;
    /**广点通ID*/
    private String gdt_appID;
    private String gdt_bannerID;
    private String gdt_cpID;
    private String gdt_splashID;
    private String gdt_videoID;
    /**精众*/
    private String jz_appID;
    private String jz_posID;
    /**链咖*/
    private String lk_appID;
    private String lk_appKey;
    private String adPosId;//广告位ID

    public String getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(String adPosId) {
        this.adPosId = adPosId;
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

    public String getCsj_cpID() {
        return csj_cpID;
    }

    public void setCsj_cpID(String csj_cpID) {
        this.csj_cpID = csj_cpID;
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

    public String getGdt_cpID() {
        return gdt_cpID;
    }

    public void setGdt_cpID(String gdt_cpID) {
        this.gdt_cpID = gdt_cpID;
    }

    public String getGdt_splashID() {
        return gdt_splashID;
    }

    public void setGdt_splashID(String gdt_splashID) {
        this.gdt_splashID = gdt_splashID;
    }

    public String getGdt_videoID() {
        return gdt_videoID;
    }

    public void setGdt_videoID(String gdt_videoID) {
        this.gdt_videoID = gdt_videoID;
    }
}
