package com.down;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by DN on 2018/3/10.
 */

public class AppManageUtils {
    /*下载apk默认保存的文件夹*/
    public static final String APKDefaultDirectory = "JOKE";
    public static final int INSTALL_CODE = 1315;
    private AppManageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getDefaultDownloadPath(@NonNull String apkPath,@NonNull String url){
        return getDefaultDirectory(apkPath)+getFileName(url);
    }

    public static String getDefaultDownloadPath(@NonNull String packageName){
        return getDefaultDirectory(null)+getFileName(packageName);
    }


    /****
     * 判断本地缓存的APK文件是否存在,并且是否完整.
     * 允许存在一些丢包现象,即服务端配置的APK大小为20000字节,可以允许本地SDCARD下载完后只有19900
     *
     * @param apkUrl
     * @param appSize
     * @return
     */
    public static final int APK_FILE_DEVIATION = 100; //APK包大小允许的误差
    public static boolean validateSdcardHasAPK(@NonNull String apkPackageName,long appSize) {
        if(null==apkPackageName){return false;}
        if(apkPackageName.equals("")){return false;}
        String defaultDownloadPath = getDefaultDownloadPath(apkPackageName);
        //下载的文件大小是否小于指定文件大小的100
        if (AppManageUtils.exists(defaultDownloadPath)&& AppManageUtils.fileIsFull(defaultDownloadPath, appSize - APK_FILE_DEVIATION)) {
            return true;
        } else {
            return false;
        }
    }

    /****
     * 检查APP是否安装成功
     * @param packageName
     * @param context
     * @return
     */
    public  static boolean isInstalled(@NonNull String packageName, Context context) {
        if("".equals(packageName)||packageName.length()<=0){return false;}
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是否有权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean selfPermissionGranted(Context context, String... permission) {
        boolean ret = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
                for(String s:permission){
                    ret = context.checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED;
                    if(!ret){
                        break;
                    }
                }
            } else {
                for(String s:permission){
                    ret = PermissionChecker.checkSelfPermission(context, s) == PermissionChecker.PERMISSION_GRANTED;
                    if(!ret){
                        break;
                    }
                }
            }
        }
        return ret;
    }


    /*****
     * 启动APP
     *
     * @param packageName
     * @param context
     */
    public static void launchApp(@NonNull String packageName, Context context) {
        try{
            Intent mainIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
            context.startActivity(mainIntent);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /***
     * 安装APP
     * @param apkPackage
     * @param act
     */
    public static void installApp( Activity act,@NonNull String apkPackage) {
        //判断是否有安装权限，针对8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try{
                install(apkPackage,act);
            }catch (Exception e){
                e.printStackTrace();
                DownLog.e("异常信息："+e.getMessage());
                startInstallPermissionSettingActivity(act);
            }
//            startInstallPermissionSettingActivity(act);
        }else{
            install(apkPackage,act);
        }
    }


    /**
     * 适配华为，oppo,vivo 主流市场安装
     * @param apkPackage
     * @param act
     * @return
     */
    private static void install(@NonNull String apkPackage, Activity act){
        String downloadPath = getDefaultDownloadPath(apkPackage);
        Intent intent = new Intent();
        if (exists(downloadPath)) {
            // 开始安装
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){   //判断版本是否大于7.0
                Uri apkUri = FileProvider.getUriForFile(act,act.getApplicationContext().getPackageName()+".DownLoadFileProvider",new File(downloadPath));
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }else{
                //7.0以下的安装方式
                intent.setDataAndType(Uri.fromFile(new File(downloadPath)),"application/vnd.android.package-archive");
            }
            /*if(isOppo()){
                intent.putExtra("refererHost","m.store.oppomobile.com"); //适配OPPO手机，仿照应用宝适配
            }*/
            if(isVivo()){
                intent.putExtra("installDir",true);//单独适配vivo手机，仿照应用宝适配
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            act.startActivity(intent);
        }
    }


    public static boolean isOppo() {
        try {
            String obj = Build.MANUFACTURER;
            if (!TextUtils.isEmpty(obj) && obj.toLowerCase().contains("oppo")) {
                return true;
            }
            obj = Build.FINGERPRINT;
            if (!TextUtils.isEmpty(obj) && obj.toLowerCase().contains("oppo")) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            return false;
        }
    }

    public static boolean isVivo() {
        if (replaceBlank((Build.MANUFACTURER + "-" + Build.MODEL).toLowerCase()).contains("vivo")) {
            return true;
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String str2 = "";
        if (str != null) {
            return Pattern.compile("\\s*|\t|\r|\n").matcher(str).replaceAll("");
        }
        return str2;
    }
    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    public static void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        //获取当前apk包URI，并设置到intent中（这一步设置，可让“未知应用权限设置界面”只显示当前应用的设置项）
        Uri packageURI = Uri.parse("package:"+context.getPackageName());
        intent.setData(packageURI);
        //设置不同版本跳转未知应用的动作
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        }else {
            intent.setAction(Settings.ACTION_SECURITY_SETTINGS);
        }
        ((Activity) context).startActivityForResult(intent, INSTALL_CODE);
        DownLog.e("请开启未知应用安装权限");
    }

    //3. 判断SDCard的文件大小不小于指定的
    private static boolean fileIsFull(@NonNull String path, double size) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            int available = inputStream.available();
            if (available >= size) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 默认下载目录
     * @return
     */
    public static String getDefaultDirectory(@NonNull String apkPath) {
        String  DEFAULT_FILE_DIR ;
        if (TextUtils.isEmpty(apkPath)) {
            DEFAULT_FILE_DIR  = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + APKDefaultDirectory + File.separator;
        }else{
            return apkPath;
        }
        return DEFAULT_FILE_DIR;

    }

    /**
     * 默认下载文件的名称
     * @return
     */
    public static String getFileName(@NonNull String url) {
        if(url.isEmpty()){return "";}
        return url.substring(url.lastIndexOf("/") + 1)+".apk";
    }


    public static boolean isEmpty(Object obj){
        return null==obj;
    }

    public static boolean exists(String fileName) {
        if (fileName == null) {
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 检查是否安装应用
     */
    //检查APK是否被安装循环次数,每隔2秒钟检查一次,2分钟后不再检查.
    private static final int APK_INSTALL_CHECK_TIMES = 120;
    public static class CheckThread extends Thread{
        private Context mContext;
        private String apkPackage;
        public CheckThread(Context mContext,String apkPackage){
            super();
            this.mContext = mContext;
            this.apkPackage = apkPackage;
        }
        @Override
        public void run(){
            int checkTimes = APK_INSTALL_CHECK_TIMES;
            while (checkTimes-- > 0) {
                try{
                    Thread.sleep(2000);//暂停两秒
                    if (isInstalled(apkPackage,mContext)) {
                        checkTimes=0;//设置为0，结束循环
                        //启动APK
                        launchApp(apkPackage,mContext);
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
