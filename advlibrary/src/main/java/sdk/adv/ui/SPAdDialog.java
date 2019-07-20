package sdk.adv.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import sdk.adv.AdConfig;
import sdk.adv.AdvConstant;
import sdk.adv.R;
import sdk.adv.interfaces.OnSuccessListener;
import sdk.adv.manager.CSJAdvHelper;
import sdk.adv.manager.GDTAdvHelper;

/**
 * @date {2019/7/19}
 */
public class SPAdDialog extends Dialog {

    private Context mContext;
    private FrameLayout spAdv;
    private OnCompleteListener listener;
    public interface OnCompleteListener {
        void onComplete(int gold, boolean isNormal);
        void onFail();
    }

    public void setOnCompleteListener(OnCompleteListener listener){
        this.listener = listener;
    }
    public SPAdDialog(@NonNull Context context) {
        super(context,R.style.sp_adv_dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        setContentView(R.layout.sp_adv_activity);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        lp.height = display.getHeight();
        window.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        spAdv = findViewById(R.id.spAdv);
    }

    public void showDialog(int advType,int gold,AdConfig config){
        SPAdDialog.this.show();//先显示，再加载广告
        if(advType== AdvConstant.CSJ_TYPE){
            CSJAdvHelper.loadCSJKPAdv((Activity)mContext, spAdv, config.getCsjAdvID().getCsj_splashID(), gold, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {
                    if(listener!=null)listener.onComplete(gold,isNormal);
                }
                @Override
                public void onFail(int type) {
                    if(listener!=null)listener.onFail();
                }
            });
        }else if(advType==AdvConstant.GDT_TYPE){
            GDTAdvHelper.loadSplashAD((Activity)mContext, spAdv, config.getGdtAdvID().getGdt_appID(), config.getGdtAdvID().getGdt_splashID(),gold, new OnSuccessListener() {
                @Override
                public void onComplete(int type, int gold, boolean isNormal) {

                    if(listener!=null)listener.onComplete(gold,isNormal);

                }
                @Override
                public void onFail(int type) {
                    if(listener!=null)listener.onFail();
                }
            });
        }

    }


}
