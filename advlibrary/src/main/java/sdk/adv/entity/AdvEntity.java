package sdk.adv.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sdk.adv.tools.FileTools;
import sdk.adv.tools.GsonUtils;

/**
 * @date {2019/6/11}
 * 广告实体类
 */
public class AdvEntity implements Serializable{
    private int advType;//广告类型

    private int advRatio;//广告比率

    private String advName;//广告名称

    public AdvEntity(int advType,int advRatio,String advName){
        this.advType = advType;
        this.advRatio = advRatio;
        this.advName = advName;
    }

    public int getAdvType() {
        return advType;
    }

    public void setAdvType(int advType) {
        this.advType = advType;
    }

    public int getAdvRatio() {
        return advRatio;
    }

    public void setAdvRatio(int advRatio) {
        this.advRatio = advRatio;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public static AdvData create(){
        AdvData advData = new AdvData();
        List<AdvEntity> allLists = new ArrayList<>();
        AdvEntity advEntity1 = new AdvEntity(1,40,"穿山甲视频广告");
        AdvEntity advEntity2 = new AdvEntity(2,20,"穿山甲开屏广告");
        AdvEntity advEntity3 = new AdvEntity(3,10,"穿山甲插屏广告");
        AdvEntity advEntity4 = new AdvEntity(4,20,"广点通视频广告");
        AdvEntity advEntity5 = new AdvEntity(5,5,"广点通开屏广告");
        AdvEntity advEntity6 = new AdvEntity(6,5,"广点通插屏广告");
        allLists.add(advEntity1);
        allLists.add(advEntity2);
        allLists.add(advEntity3);
        allLists.add(advEntity4);
        allLists.add(advEntity5);
        allLists.add(advEntity6);

        List<AdvEntity> bannerList = new ArrayList<>();
        AdvEntity banner1 = new AdvEntity(1,100,"穿山甲Banner广告");
        AdvEntity banner2 = new AdvEntity(2,0,"广点通Banner广告");
        bannerList.add(banner1);
        bannerList.add(banner2);

        List<AdvEntity> videoList = new ArrayList<>();
        AdvEntity video1 = new AdvEntity(1,100,"穿山甲视频广告");
        AdvEntity video2 = new AdvEntity(2,0,"广点通视频广告");
        videoList.add(video1);
        videoList.add(video2);

        List<AdvEntity> splashList = new ArrayList<>();
        AdvEntity splash1 = new AdvEntity(1,100,"穿山甲开屏广告");
        AdvEntity splash2 = new AdvEntity(2,0,"广点通开屏广告");
        splashList.add(splash1);
        splashList.add(splash2);

        List<AdvEntity> cpList = new ArrayList<>();
        AdvEntity cp1 = new AdvEntity(1,100,"穿山甲插屏广告");
        AdvEntity cp2 = new AdvEntity(2,0,"广点通插屏广告");
        cpList.add(cp1);
        cpList.add(cp2);

        advData.setBannerAdvs(bannerList);
        advData.setCpAdvs(cpList);
        advData.setSplashAdvs(splashList);
        advData.setVideoAdvs(videoList);
        advData.setAllAdvs(allLists);
        return advData;
    }
    public static void main(String [] args){
        String path = "D://1-ZHKJ//ADVSDK//AdvData.json";
//        AdvData advData = create();
//        String gson = GsonUtils.moduleTojosn(advData);
//        FileTools.saveToFile(gson,new File(path));
        String str = FileTools.getDataFile(path);
        AdvData advData = GsonUtils.josnToModule(str,AdvData.class);
//        List<AdvEntity> all = advData.getAllAdvs();
        List<AdvEntity> all = advData.getBannerAdvs();
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0;i<all.size();i++) {
            map.put(all.get(i).getAdvType(),0);
        }

        /*List<AdvEntity> advList = advData.getBannerAdvs();
//        AdvPools.setBannerFailPool(all.get(0));
        for(int i = 0;i<1000;i++){
            AdvEntity ad  = AdvPools.getAdvPool().getBannerAdv(advList);
            if(ad!=null){
                AdvPools.setBannerFailPool(ad);
//                System.out.println("结果:"+ad.getAdvName()+"概率:"+ad.getAdvRatio());
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if(ad.getAdvType()==entry.getKey()){
                        map.put(entry.getKey(),entry.getValue()+1);
                    }
                }
            }else {
                break;
            }
        }*/

        int count = 0;
        for (int i = 0;i<all.size();i++) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if(all.get(i).getAdvType()==entry.getKey()){
                    System.out.println(all.get(i).getAdvName()+"概率"+all.get(i).getAdvRatio()+"出现的次数："+entry.getValue());
                    count = count+entry.getValue();
                }
            }
        }
        System.out.println("所有随机数数量之和："+count);


    }

}
