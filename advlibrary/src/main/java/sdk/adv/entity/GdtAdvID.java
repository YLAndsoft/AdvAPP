package sdk.adv.entity;

import java.io.Serializable;

/**
 * @date {2019/7/20}
 * 广点通ID
 */
public class GdtAdvID implements Serializable {
    private String gdt_appID; //应用ID
    private String gdt_bannerID; //bannerID
    private String gdt_splashID;//开屏广告ID
    private String gdt_cpID; //插屏ID
    private String gdt_videoID; //视频ID
    private String tips;//说明

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    public String getGdt_splashID() {
        return gdt_splashID;
    }

    public void setGdt_splashID(String gdt_splashID) {
        this.gdt_splashID = gdt_splashID;
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
}
