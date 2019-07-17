/*
 * Copyright (c) 指环科技
 */

package com.down;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

/**
 * 如果需要安装完成自启动,请自行注册监听广播
 */
public class AppInstallReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(final Context context, Intent intent) {
    Log.i("BroadcastReceiver", "intent.getAction() --> " +intent.getAction());
    if (intent.getData()!=null && intent.getAction()!= null) {
      if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
        String packageName = intent.getData().getSchemeSpecificPart();
        String filePath = AppManageUtils.getDefaultDownloadPath(packageName);
        Log.i("BroadcastReceiver", "应用已安装，安装包 --> " + filePath);
        File file = new File(filePath);
        if (file.exists()) {
          boolean delete = file.delete();
          Log.i("BroadcastReceiver", packageName + " 应用已安装，删除安装包 --> " + delete);
        }
        if (AppManageUtils.isInstalled(packageName,context)) {
          //安装完成，直接打开/有的手机安装完成不自动启动
          AppManageUtils.launchApp(packageName,context);
          //启动线程，打开APP
          AppManageUtils.CheckThread thread = new AppManageUtils.CheckThread(context,packageName);
          thread.start();
        }
      }
    }
  }
}