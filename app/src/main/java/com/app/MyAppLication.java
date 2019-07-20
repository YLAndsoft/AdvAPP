package com.app;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import sdk.adv.AdConfig;

/**
 * @date {2019/6/21}
 */
public class MyAppLication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        String advStr = getAssetsConfig(this,"AdvData.json");
        AdConfig.initStr(this,advStr,true);//String方式初始化
    }

    /**
     * 获取本地广告配置文件
     * 用于测试

     */
    public static String getAssetsConfig(Context mContext, String jsonName){
        try{
            StringBuilder str = new StringBuilder();
            InputStream inputStream = null;
            try {
                inputStream = mContext.getResources().getAssets().open(jsonName);
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String jsonLine;
                while ((jsonLine = reader.readLine()) != null) {
                    str.append(jsonLine);
                }
                reader.close();
                isr.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
