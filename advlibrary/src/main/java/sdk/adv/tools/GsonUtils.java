package sdk.adv.tools;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author: FYL
 * @time: 2019/3/29
 * @email:347933430@qq.com
 * @describe: config
 */
public class GsonUtils {
    private static Gson GsonUtilsGson = new Gson();

    /**
     * @fun 根据不同类型进行json到实体间的转化
     * @param jsonString json字符串
     * @param cls 需要转化的类型
     * @param <T> 需要转化的类型
     * @return 返回实体对象
     */
    public static  <T> T josnToModule(String jsonString, Class<T> cls) {
        T list ;
        list=GsonUtilsGson.fromJson(jsonString,cls);
        return list;
    }

    /**
     * @fun 根据不同类型进行实体到json间的转化
     * @param cls 需要转化的类型
     * @param <T> 需要转化的类型
     * @return 返回Json字符串
     */
    public static  <T> String  moduleTojosn(T cls) {
        return  GsonUtilsGson.toJson(cls);
    }

    /**
     * String转gson
     * @param data
     * @param klass
     * @param <T>
     * @return List<T>
     *
     */
    public static <T> ArrayList<T> getGsonList(String data, Class<T> klass) {
        try{
            Gson gson = new Gson();//.newBuilder().setDateFormat("").create();
            return gson.fromJson(data, new ListOfSomething<T>(klass));
        }catch (Exception ex){
            Log.e("GsonList-ERROR", ex.getMessage());
            return null;
        }
    }

    public static class ListOfSomething<X> implements ParameterizedType {
        private Class<?> wrapped;
        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }
        public Type[] getActualTypeArguments() {
            return new Type[] {wrapped};
        }
        public Type getRawType() {
            return ArrayList.class;
        }
        public Type getOwnerType() {
            return null;
        }
    }

}
