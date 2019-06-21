package sdk.adv.interfaces;

/**
 * @date {2019/6/11}
 */
public interface OnSuccessListener {
    void onComplete(int type,int gold,boolean isNormal);//完成回调
    void onFail(int type);//失败回调
}
