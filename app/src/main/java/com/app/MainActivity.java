package com.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ssp.sdk.adInterface.AdListener;
import com.ssp.sdk.adInterface.RewardVideoListener;
import com.ssp.sdk.platform.show.PRewardVideo;

import sdk.adv.execute.AdvExecute;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button all_adv,banner_adv,cp_adv,splash_adv,video_adv,btn1,btn2;
    private FrameLayout frame,banner_frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        all_adv = findViewById(R.id.all_adv);
        video_adv = findViewById(R.id.video_adv);
        banner_adv = findViewById(R.id.banner_adv);
        cp_adv = findViewById(R.id.cp_adv);
        splash_adv = findViewById(R.id.splash_adv);
        frame = findViewById(R.id.frame);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        banner_frame = findViewById(R.id.banner_frame);

        all_adv.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        video_adv.setOnClickListener(this);
        banner_adv.setOnClickListener(this);
        cp_adv.setOnClickListener(this);
        splash_adv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_adv:
                AdvExecute.create().execute(MainActivity.this, frame, 100, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        if(isNormal){
                            showToast("广告观看完毕,奖励金币："+gold);
                        }
                        frame.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.video_adv:
                AdvExecute.create().executeVideoAdv(this, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        if(isNormal){
                            showToast("视频广告观看完毕！！！");
                        }
                    }
                });
                break;
            case R.id.banner_adv:
                AdvExecute.create().executeBanner(this, banner_frame, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        if(isNormal){
                            showToast("Banner广告加载成功！！");
                        }
                    }
                });
                break;
            case R.id.cp_adv:
                AdvExecute.create().executeCpAdv(this, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        showToast("插屏广告加载成功！！");
                    }
                });
                break;
            case R.id.splash_adv:
                AdvExecute.create().executeSplashAdv(this, frame, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        showToast("开屏广告加载成功！！");
                        frame.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.btn1:
                loadRewardVideo();
                break;
            case R.id.btn2:
                showRewardVideo();
                break;


        }
    }

    // 测试
    public final static String APP_ID = "1540623508";
    public final static String REWARDVIDEO_POSID = "1561692800";
    private PRewardVideo pRewardVideo;
    private void loadRewardVideo() {
        String appID = APP_ID;
        String posID = REWARDVIDEO_POSID;
        pRewardVideo = new PRewardVideo(this, appID, posID, new AdListener() {
            @Override
            public void onLoadFail(int code, String message) {
                Toast.makeText(MainActivity.this,"onLoadFail code=" + code + ";message=" + message,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLoadSuccess() {
                Toast.makeText(MainActivity.this,"视频加载成功",Toast.LENGTH_SHORT).show();
//                ActivityRewardVideo.this.showAdBtn.setEnabled(true);
            }
            @Override
            public void onAdOpen() {
            }
            @Override
            public void onAdClick() {
            }
            @Override
            public void onAdClose() {

            }
        });
        pRewardVideo.loadAd();
    }

    private void showRewardVideo() {
        pRewardVideo.showAd(new RewardVideoListener() {
            @Override
            public void onVideoShow() {
                Toast.makeText(MainActivity.this,"onVideoShow",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVideoClick() {
//                        Toast.makeText(ActivityTest.this,"onVideoClick",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVideoBarClick() {
                Toast.makeText(MainActivity.this,"onVideoBarClick",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVideoClose() {
                Toast.makeText(MainActivity.this,"onVideoClose",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVideoComplete() {
                Toast.makeText(MainActivity.this,"onVideoComplete",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVideoError(int code, String message) {
                Toast.makeText(MainActivity.this,"onVideoError",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVideoVerify(boolean verify, int amount, String name) {
                Toast.makeText(MainActivity.this,"onVideoVerify",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }


}
