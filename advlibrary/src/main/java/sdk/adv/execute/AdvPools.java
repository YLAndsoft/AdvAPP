package sdk.adv.execute;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sdk.adv.db.SPManager;
import sdk.adv.entity.AdvData;
import sdk.adv.entity.AdvEntity;

/**
 * @date {2019/6/12}
 * 广告池
 */
public class AdvPools {
    private static List<AdvEntity> advAllFials = new ArrayList<>(); //广告百分比配置文件(所有的广告)

    private static List<AdvEntity> bannerFailPool = new ArrayList<>();//banner广告失败池
    private static List<AdvEntity> cpFailPool = new ArrayList<>();//cp广告失败池
    private static List<AdvEntity> splashFailPool = new ArrayList<>();//cp广告失败池
    private static List<AdvEntity> videoFailPool = new ArrayList<>();//cp广告失败池
    private static AdvPools advPool;


    public static AdvPools getAdvPool(){
        if(advPool==null){
            advPool = new AdvPools();
        }
        return advPool;
    }
    /***
     * 获取Banner广告实体
     * @return
     */
    public AdvEntity getAllAdv(Context mContext){
        List<AdvEntity> entities = getAdvEntitys(mContext,0);
        List<AdvEntity> newList = checkAdvEntity(0,entities);
        if(null==newList||newList.size()<=0){
            return null;
        }
        AdvEntity advEntity = AdvRandom.randomAdv(newList);
        return advEntity;
    }


    /***
     * 获取Banner广告实体
     * @return
     */
    public AdvEntity getBannerAdv(Context mContext){
        List<AdvEntity> entities = getAdvEntitys(mContext,1);
        List<AdvEntity> newList = checkAdvEntity(1,entities);
        if(null==newList||newList.size()<=0){
            return null;
        }
        AdvEntity advEntity = AdvRandom.randomAdv(newList);
        return advEntity;
    }
    /***
     * 获取插屏广告实体
     * @return
     */
    public AdvEntity getCPAdv(Context mContext){
        List<AdvEntity> entities = getAdvEntitys(mContext,2);
        List<AdvEntity> newList = checkAdvEntity(2,entities);
        AdvEntity advEntity = AdvRandom.randomAdv(newList);
        return advEntity;
    }
    /***
     * 获取开屏广告
     * @param mContext
     * @return
     */
    public AdvEntity getSpashAdv(Context mContext){
        List<AdvEntity> entities = getAdvEntitys(mContext,3);
        List<AdvEntity> newList = checkAdvEntity(3,entities);
        AdvEntity advEntity = AdvRandom.randomAdv(newList);
        return advEntity;
    }

    /**
     * 获取视频广告集合
     * @param mContext
     * @return
     */
    public AdvEntity getVideoAdv(Context mContext){
        List<AdvEntity> entities = getAdvEntitys(mContext,4);
        List<AdvEntity> newList = checkAdvEntity(4,entities);
        AdvEntity advEntity = AdvRandom.randomAdv(newList);
        return advEntity;
    }



//    public AdvEntity getBannerAdv(List<AdvEntity> entities){
////        List<AdvEntity> entities = getAdvEntitys(mContext,1);
//        List<AdvEntity> newList = checkAdvEntity(entities);
//        AdvEntity advEntity = AdvRandom.randomAdv(newList);
//        return advEntity;
//    }

    /**
     * 检查过滤掉已经加载失败的广告
     */
    private List<AdvEntity> checkAdvEntity(int type,List<AdvEntity> entities){
        if(null==entities||entities.size()<=0) return null;
        List<AdvEntity> newList = new ArrayList<>();
        List<AdvEntity> failAdv = getFailAdv(type);
//        List<AdvEntity> failAdv = getBannerFailPool();
        if(null==failAdv||failAdv.size()<=0){
            newList.addAll(entities);
            return newList;
        }
        for(int i=0;i<entities.size();i++){
            boolean isContains = false;
            for(int j=0;j<failAdv.size();j++){
                if(failAdv.get(j).getAdvType()==entities.get(i).getAdvType()){
                    isContains = true;
                    break;
                }
            }
            if(!isContains){
                newList.add(entities.get(i));
            }

        }
        return newList;
    }

    /**
     * 根据类型返回失败池集合
     * @param type
     * @return
     */
    private List<AdvEntity> getFailAdv(int type){
        List<AdvEntity> fails = null;
        switch (type){
            case 0:
                fails = getAdvAllFials();
                break;
            case 1:
                fails = getBannerFailPool();
                break;
            case 2:
                fails = getCpFailPool();
                break;
            case 3:
                fails = getSplashFailPool();
                break;
            case 4:
                fails = getVideoFailPool();
                break;
        }
        return fails;
    }

    /**
     * 获取广告sdk配置的信息集合（all）
     *  0:返回所有广告集合 1：返回Banner广告集合 2：返回插屏广告集合 3:返回开屏广告集合 4：返回视频广告集合
     * @return
     */
    private static List<AdvEntity> getAdvEntitys(Context mContext,int type){
        AdvData advData = (AdvData) SPManager.getInstance(mContext).getConfig("AdvData");
        List<AdvEntity> advEntityList = null;
        switch (type){
            case 0:
                advEntityList = advData.getAllAdvs();
                break;
            case 1:
                advEntityList = advData.getBannerAdvs();
                break;
            case 2:
                advEntityList = advData.getCpAdvs();
                break;
            case 3:
                advEntityList = advData.getSplashAdvs();
                break;
            case 4:
                advEntityList = advData.getVideoAdvs();
                break;
        }
        return advEntityList;
    }
    /**
     * 判断id是否在失败池里面
     * @param failpool 失败池
     * @param id 广告ID
     * @return
     */
    private boolean isAdvPool(int id,List<AdvEntity> failpool){
        boolean isLocalFail = false;
        for(AdvEntity entity:failpool){
            if(entity.getAdvType()==id){
                isLocalFail = true;//和失败池匹配上，结束循环
                break;
            }
        }
        return isLocalFail;
    }

    public static void setAdvFailAlls(AdvEntity allEntity) {
        boolean isContains = false;
        if(advAllFials.size()>0){
            for(int i = 0;i<advAllFials.size();i++){
                if(advAllFials.get(i).getAdvType()==allEntity.getAdvType()){
                    isContains = true;
                    break;
                }
            }
        }
        if(!isContains)advAllFials.add(allEntity);//不存在,则添加
    }
    /***
     * 往Banner失败池添加失败的广告
     * @param bannerAdvEntity
     */
    public static void setBannerFailPool(AdvEntity bannerAdvEntity) {
        boolean isContains = false;
        if(bannerFailPool.size()>0){
            for(int i = 0;i<bannerFailPool.size();i++){
                if(bannerFailPool.get(i).getAdvType()==bannerAdvEntity.getAdvType()){
                    isContains = true;
                    break;
                }
            }
        }
        if(!isContains)bannerFailPool.add(bannerAdvEntity);//不存在,则添加
    }
    /***
     * 往插屏失败池添加失败的广告
     * @param cpAdvEntity
     */
    public static void setCpFailPool(AdvEntity cpAdvEntity) {
        boolean isContains = false;
        if(cpFailPool.size()>0){
            for(int i = 0;i<cpFailPool.size();i++){
                if(cpFailPool.get(i).getAdvType()==cpAdvEntity.getAdvType()){
                    isContains = true;
                    break;
                }
            }
        }
        if(!isContains)cpFailPool.add(cpAdvEntity);//不存在,则添加
    }
    /***
     * 往开屏失败池添加失败的广告
     * @param spashAdvEntity
     */
    public static void setSplashFailPool(AdvEntity spashAdvEntity) {
        boolean isContains = false;
        if(splashFailPool.size()>0){
            for(int i = 0;i<splashFailPool.size();i++){
                if(splashFailPool.get(i).getAdvType()==spashAdvEntity.getAdvType()){
                    isContains = true;
                    break;
                }
            }
        }
        if(!isContains)splashFailPool.add(spashAdvEntity);//不存在,则添加
    }

    /***
     * 往视频失败池添加失败的广告
     * @param videoAdvEntity
     */
    public static void setVideoFailPool(AdvEntity videoAdvEntity) {
        boolean isContains = false;
        if(videoFailPool.size()>0){
            for(int i = 0;i<videoFailPool.size();i++){
                if(videoFailPool.get(i).getAdvType()==videoAdvEntity.getAdvType()){
                    isContains = true;
                    break;
                }
            }
        }
        if(!isContains)videoFailPool.add(videoAdvEntity);//不存在,则添加
    }

    public static List<AdvEntity> getAdvAllFials() {
        return advAllFials;
    }

    public static List<AdvEntity> getBannerFailPool() {
        return bannerFailPool;
    }

    public static List<AdvEntity> getCpFailPool() {
        return cpFailPool;
    }

    public static List<AdvEntity> getSplashFailPool() {
        return splashFailPool;
    }

    public static List<AdvEntity> getVideoFailPool() {
        return videoFailPool;
    }

    /**
     * 清空失败池
     * 当广告显示出来之后再执行此函数
     */
    public void clearFailPool(){
        if(null!=advAllFials&&advAllFials.size()>0) advAllFials.clear();
        if(null!=bannerFailPool&&bannerFailPool.size()>0) bannerFailPool.clear();
        if(null!=cpFailPool&&cpFailPool.size()>0) cpFailPool.clear();
        if(null!=splashFailPool&&splashFailPool.size()>0) splashFailPool.clear();
        if(null!=videoFailPool&&videoFailPool.size()>0) videoFailPool.clear();
    }


}

