package sdk.adv.execute;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import sdk.adv.entity.AdvData;
import sdk.adv.entity.AdvEntity;
import sdk.adv.tools.FileTools;
import sdk.adv.tools.GsonUtils;

/**
 * @date {2019/6/11}
 * 广告池-随机函数
 */
public class AdvRandom {
    private static Random random = new Random();
    /**
     * 检查百分比，返回百分比的和
     * @param prob
     * @return
     */
    private static int checkRatio(List<AdvEntity> prob) {
        int count= 0 ;
        if(null==prob||prob.size()<=0)return count;
        for(AdvEntity i : prob){
            count = count + i.getAdvRatio();
        }
        return count;
    }

    /**
     * 生成随机数，范围 ratioCount
     * @param probability 广告列表
     * @return 返回得到的广告
     * TODO 如果加载失败的广告,记得添加至失败池里面
     */
    public static AdvEntity randomAdv(List<AdvEntity> probability,List<AdvEntity> failList) {
        if(null==probability||probability.size()<=0){return null;}
        int ratioCount = checkRatio(probability);//未加载失败的广告百分比总和
        int yratioCount = checkRatio(failList);//已加载失败的广告百分比总和
        if(ratioCount<=0){
            //如果所有广告的比率都为0，那么直接返回列表第一个，
            // tips:没做排重,当返回的广告加载失败,记得添加到广告池
            if(yratioCount>0){
                //失败广告池配置广告的比率大于0的话，
                // 说明有配置比率的广告已经加载失败,那么就不去加载配置为0的广告
                return null;
            }
            return probability.get(0);
        }
        AdvEntity advEntity = null;
        int  randomNumber = random.nextInt(ratioCount)+1;
        int count = 0;
            for(int i = 0;i< probability.size() ; i++){
                count =count + probability.get(i).getAdvRatio();
                if(randomNumber <= count) { //拿到百分比区间，返回对应的广告
                    advEntity = probability.get(i);
                    break;
                }
            }
            return advEntity;
    }

    /*
     * 测试主函数
     */
    public static void main(String[] args) {
        AdvRandom cMathRandom = new AdvRandom();
        String path = "D://1-ZHKJ//ADVSDK//AdvData.json";
        String str = FileTools.getDataFile(path);
        AdvData advData = GsonUtils.josnToModule(str,AdvData.class);
//        cMathRandom.init(advData.getAllAdvs());//初始化随机数概率
        Map<Integer,Integer> map = new HashMap<>();
        List<AdvEntity> countts = advData.getAllAdvs();
        for (int i = 0;i<countts.size();i++) {
            map.put(countts.get(i).getAdvType(),0);
        }
        for(int i = 0 ; i < 1000 ; i ++) {
            AdvEntity advEntity = cMathRandom.randomAdv(countts,null);
            if(advEntity==null){
                System.out.println("错误数据：");
                return;
            }
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if(advEntity.getAdvType()==entry.getKey()){
                    map.put(entry.getKey(),entry.getValue()+1);
                }
            }
        }
        int count = 0;
        for (int i = 0;i<countts.size();i++) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if(countts.get(i).getAdvType()==entry.getKey()){
                    System.out.println(countts.get(i).getAdvName()+"概率"+countts.get(i).getAdvRatio()+"出现的次数："+entry.getValue());
                    count = count+entry.getValue();
                }
            }
        }
        System.out.println("所有随机数数量之和："+count);

    }

}
