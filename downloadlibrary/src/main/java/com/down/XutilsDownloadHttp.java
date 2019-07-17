package com.down;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

/**
 * @author: FYL
 * @time: 2018/9/3
 * @email:347933430@qq.com
 * @describe: 网络请求相关
 */

public class XutilsDownloadHttp {
    //接口回调
   public interface XUtilsCallBack{
        void onDownSuccess(File result);
        void onFail(String result);
        void onLoading(long total, long current, boolean isDownloading);
    }

    /**
     *下载
     * @param url
     * @param path
     * @param xCallBack
     */
    public static void xUtilsDownloadFile(final String url, String path,final XUtilsCallBack xCallBack) {
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(path);//保存文件的路径
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                xCallBack.onDownSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onWaiting() {
            }
            @Override
            public void onStarted() {
            }
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                xCallBack.onLoading(total,current,isDownloading);
            }
        });

    }

}
