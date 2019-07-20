package sdk.adv.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.widget.FrameLayout;

/**
 * @date {2019/7/20}
 */
public class ViewTools {
    /**
     * 获取屏幕是横屏还是竖屏
     */
    public static final int PORTRAIT_ORIENTATION = 1;
    public static final int LANDSCAPE_ORIENTATION = 2;
    public static int getOrientation(Context mContext){
        Configuration mConfiguration = mContext.getResources().getConfiguration(); //获取设置的配置信息
//        int ori = mConfiguration.orientation; //获取屏幕方向
        return mConfiguration.orientation;
        /*if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        }*/
    }

    /**
     * banner2.0规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
     * @return
     */
    public static FrameLayout.LayoutParams getUnifiedBannerLayoutParams(Activity mActivity) {
        Point screenSize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        int x = screenSize.x;//1360
        int y = screenSize.y;//720
        int new_x = Math.round(x / 6.4f); //113
//        1360/90
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(x, new_x);
        return layoutParams;
    }



}
