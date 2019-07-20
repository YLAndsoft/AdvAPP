package com.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import sdk.adv.execute.AdvExecute;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button all_adv,banner_adv,cp_adv,splash_adv,video_adv,btn1,btn2;
    private FrameLayout banner_frame;
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
                AdvExecute.create().execute(MainActivity.this, 100, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        if(isNormal){
                            showToast("广告观看完毕,奖励金币："+gold);
                        }
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
                AdvExecute.create().executeSplashAdv(this, new AdvExecute.OnCompleteListener() {
                    @Override
                    public void onComplete(int gold, boolean isNormal) {
                        showToast("开屏广告加载成功！！");
                    }
                });
                break;
            case R.id.btn1:

                break;
            case R.id.btn2:

                break;


        }
    }


    public void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }


}
