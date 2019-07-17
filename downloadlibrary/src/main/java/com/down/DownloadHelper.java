package com.down;

import android.app.Activity;

import java.io.File;

/**
 * @date {2019/7/16}
 * 下载辅助类 适用于单线程下载
 */
public class DownloadHelper {

    public interface OnDownloadListener{
        void onComplete();//下载完成
        void onDowning(int pro);
        void onFail(String s);//下载失败
    }

    private static boolean isDowning = false;

    /**
     * 下载前判断
     * @param activity
     * @param apkPackage
     * @param apkSize
     * @param apkurl
     * @param listener
     * @param
     */
    public static void down(Activity activity,String apkPackage, long apkSize, String apkurl,OnDownloadListener listener){
        if(isDowning){return;}
        //1.检查apk是否安装
        if(AppManageUtils.isInstalled(apkPackage,activity)){ //已经安装,直接打开
            AppManageUtils.launchApp(apkPackage,activity);
            isDowning = false;
        }else{ //2.判断apk是否已经下载
            if(AppManageUtils.validateSdcardHasAPK(apkPackage,apkSize)){ //已经下载完成，直接拉起安装界面
                AppManageUtils.installApp(activity,apkPackage);//去安装
                isDowning = false;
            }else{ //去下载
                isDowning = true;
                download(activity,apkurl,apkPackage,apkSize,listener);
            }
        }
    }

    /**
     * 去下载
     * @param activity
     * @param apkurl
     * @param apkPackage
     * @param apkSize
     * @param listener
     */
    private static void download(final Activity activity, String apkurl, final String apkPackage, final long apkSize, final OnDownloadListener listener) {
        String path = AppManageUtils.getDefaultDownloadPath(apkPackage);
        XutilsDownloadHttp.xUtilsDownloadFile(apkurl, path, new XutilsDownloadHttp.XUtilsCallBack() {
            @Override
            public void onDownSuccess(File result) {
                //下载完成,判断文件是否完整
                isDowning = false;
                if(AppManageUtils.validateSdcardHasAPK(apkPackage,apkSize)){
                    AppManageUtils.installApp(activity,apkPackage);//去安装
                }
            }
            @Override
            public void onFail(String result) {
                //下载失败
                isDowning = false;
                if(null!=listener)listener.onFail(result);
            }
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //下载中
                int progress = (int) (current *100L / total);  //下载进度
                if(null!=listener)listener.onDowning(progress);
            }
        });
    }


}
