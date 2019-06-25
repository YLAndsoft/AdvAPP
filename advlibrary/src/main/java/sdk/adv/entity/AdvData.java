package sdk.adv.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @date {2019/6/19}
 */
public class AdvData implements Serializable{
    private List<AdvEntity> bannerAdvs;//banner广告集合

    private List<AdvEntity> cpAdvs;//插屏广告集合

    private List<AdvEntity> splashAdvs;//开屏广告集合

    private List<AdvEntity> videoAdvs;//视频广告集合

    private List<AdvEntity> allAdvs;//视频广告集合

    private AdvID advID;

    public AdvID getAdvID() {
        return advID;
    }

    public void setAdvID(AdvID advID) {
        this.advID = advID;
    }

    public List<AdvEntity> getAllAdvs() {
        return allAdvs;
    }

    public void setAllAdvs(List<AdvEntity> allAdvs) {
        this.allAdvs = allAdvs;
    }

    public List<AdvEntity> getBannerAdvs() {
        return bannerAdvs;
    }

    public void setBannerAdvs(List<AdvEntity> bannerAdvs) {
        this.bannerAdvs = bannerAdvs;
    }

    public List<AdvEntity> getCpAdvs() {
        return cpAdvs;
    }

    public void setCpAdvs(List<AdvEntity> cpAdvs) {
        this.cpAdvs = cpAdvs;
    }

    public List<AdvEntity> getSplashAdvs() {
        return splashAdvs;
    }

    public void setSplashAdvs(List<AdvEntity> splashAdvs) {
        this.splashAdvs = splashAdvs;
    }

    public List<AdvEntity> getVideoAdvs() {
        return videoAdvs;
    }

    public void setVideoAdvs(List<AdvEntity> videoAdvs) {
        this.videoAdvs = videoAdvs;
    }
}
