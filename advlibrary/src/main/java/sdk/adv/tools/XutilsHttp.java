package sdk.adv.tools;

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

public class XutilsHttp {
    //接口回调
   public interface XUtilsCallBack{
        void onResponse(String result);
        void onDownSuccess(File result);
        void onFail(String result);
    }
    /**
     * 普通的get请求(无缓存)
     *@param  url  请求地址
     * @param map 参数集合
     * @param  xCallBack 请求结果回调
     */
    public static void xUtilsGet(String url, Map<String,String> map, final XUtilsCallBack xCallBack){
        RequestParams params = new RequestParams(url);
        if (null != map && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()){
                params.addBodyParameter(entry.getKey(),entry.getValue());
            }
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            /**
             * // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，
             * 因此可以用以下方法区分是网络错误还是其他错误
             // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
             * @param ex
             * @param isOnCallback
             */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) {
                    // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    xCallBack.onFail(responseMsg);
                } else {
                    // 错误
                    xCallBack.onFail(ex.toString());
                }
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                // 不管成功或者失败最后都会回调该接口
            }
        });
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


            }
        });

    }

}
