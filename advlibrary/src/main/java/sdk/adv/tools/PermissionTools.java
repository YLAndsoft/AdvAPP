package sdk.adv.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import kr.co.namee.permissiongen.PermissionGen;

/**
 * @date {2019/7/15}
 * 权限请求工具
 */
public class PermissionTools {

    public static final String[] PermissionGroup = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
    };
    public static void requsetPermission(Activity mActivity,int code,String[] str){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionGen.with(mActivity).addRequestCode(code).permissions(str).request();
        }
    }
    public static void requsetPermission(Activity mActivity,int code ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionGen.with(mActivity).addRequestCode(code).permissions(PermissionGroup).request();
        }
    }

    /**
     *
     *   @TODO 调用上面requsetPermission()函数后，请把一下三个方法复制到调用的Activity里面
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    private static final int PHOTO_PERMISS = 111;
     @PermissionSuccess(requestCode = PHOTO_PERMISS)
     public void requestPhotoSuccess() {
     //成功之后的处理

     }
     @PermissionFail(requestCode = PHOTO_PERMISS)
     public void requestPhotoFail() {
     //失败之后的处理
     PermissionTools.goToSetting(this);
     }
     * */

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

}
