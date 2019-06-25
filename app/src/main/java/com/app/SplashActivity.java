package com.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * @author: FYL
 * @time: 2019/5/27
 * @email:347933430@qq.com
 * @describe: l.j.a.com.ajl
 */
public class SplashActivity extends AppCompatActivity {
    private int recLen = 4;
    private TextView sp_time;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
//        Constant.init(getApplication());
        initView();
    }

    private static final int PHOTO_PERMISS = 111;
    public static final String[] PermissionGroup = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };

    public void initView() {
        sp_time = findViewById(R.id.sp_time);
        initPermission();
    }

    private void toNext(){
        sp_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel();
                toActivity(MainActivity.class);
                finish();
            }
        });
        timer.schedule(task,1000,1500);

    }
    /**
     * 权限判断
     * PermissionsUtils.PermissionGroup
     */
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionGen.with(SplashActivity.this).addRequestCode(PHOTO_PERMISS).permissions(PermissionGroup).request();
        } else {
            toNext();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PHOTO_PERMISS)
    public void requestPhotoSuccess() {
        //成功之后的处理
        toNext();
    }
    @PermissionFail(requestCode = PHOTO_PERMISS)
    public void requestPhotoFail() {
            goToSetting(this);
    }

    /***
     * 去设置界面
     */
    public static void goToSetting(Context context) {
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    if(recLen<0){
                        task.cancel();
                        toActivity(MainActivity.class);
                        finish();
                    }else{
                        sp_time.setText(recLen+"s");
                    }
                }
            });
        }
    };

    protected  void toActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
