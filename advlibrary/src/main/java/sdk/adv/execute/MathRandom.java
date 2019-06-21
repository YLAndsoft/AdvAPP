package sdk.adv.execute;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sdk.adv.entity.AdvEntity;

/**
 * @date {2019/6/11}
 * 广告池-随机函数
 */
public class MathRandom {
    private List<AdvEntity> probability;//随机数生成的概率列表

    public MathRandom() {
        this.probability = new ArrayList<>();
    }
    /*
     * 初始化概率表，判断概率之和是否为1
     */
    public boolean init(List<AdvEntity> prob) {
        int count= 0 ;
        boolean isPer = false;
        for(AdvEntity i : prob){
            if(i.getAdvRatio()>=100){
                //当广告集合里面某一项占100比率,直接按照排序顺序执行
                isPer = true;
            }
            count = count + i.getAdvRatio();
        }
        if(count <= 100) {
            for(int i = 0 ;i< prob.size();i++)
                this.probability.add(prob.get(i));
        }
        return isPer;

    }


    /*
     * 生成随机数，范围（0-high）
     */
    public int random() {
        Random random = new Random();
        int  randomNumber = random.nextInt(100)+1;
//            int  randomNumber = Math.random();
        int count = 0;
            int out = -1 ;
            for(int i = 0;i< probability.size() ; i++){
                count =count + this.probability.get(i).getAdvRatio();
                if(randomNumber <= count) {
                    out = i;
                    break;
                }
            }
            return out;
    }

    /*
     * 测试主函数
     */
    /*public static void main(String[] args) {
        MathRandom cMathRandom = new MathRandom();
        String path = "D://1-ZHKJ//ADVSDK//adv.json";
        String str = FileTools.getDataFile(path);
        List<AdvEntity> advList = GsonUtils.getGsonList(str,AdvEntity.class);
        cMathRandom.init(advList);//初始化随机数概率
        int[] count = new int[advList.size()] ;
        for(int i = 0 ; i < 1000 ; i ++) {
            int k = cMathRandom.random();
            if(k<0){
                System.out.println("错误数据："+k);
            }
            for(int j = 0;j<count.length;j++){
                if(k==j){
                    count[j]++;
                }
            }
        }
        int num = 0 ;
        for(int j = 0 ; j < count.length ; j ++) {
            System.out.println(advList.get(j).getAdvName()+"概率"+advList.get(j).getAdvRatio()+"出现的次数："+count[j]);
//            System.out.println("随机数 "+j+"  \t设定的概率："+advList.get(j).getAdvRatio()+"\t 对应的数量："+count[j] );
            num+=count[j];
        }
        System.out.println("所有随机数数量之和："+num);

    }*/

}
