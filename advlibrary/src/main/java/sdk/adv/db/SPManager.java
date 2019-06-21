package sdk.adv.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 * Created by DN on 2017/12/19.
 */

public class SPManager {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date"; //可自行修改
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sp;
    private static SPManager spu;
    public SPManager(Context context){
        sp = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

    }
    //单例模式
    public static SPManager getInstance(Context context) {
        if (spu ==null){
            spu =new SPManager(context);
        }
        return  spu;
    }

    /**
     * 获取是否登录
     * @param context
     * @return true:已登录, false:未登录
     */
    public static boolean isLogin(Context context){
        return  (boolean)getInstance(context).getValue("isLogin",false);
    }
    /**
     * 保存用户id
     * @param context
     * @param values
     */
    public static void saveIsLogin(Context context,boolean values){
        getInstance(context).setValue("isLogin",values);
    }



    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param object
     */
    public void setValue(@NonNull String key, @NonNull  Object object){
        try{
            String type = object.getClass().getSimpleName();
            if("String".equals(type)){
                editor.putString(key, (String)object);
            }
            else if("Integer".equals(type)){
                editor.putInt(key, (Integer)object);
            }
            else if("Boolean".equals(type)){
                editor.putBoolean(key, (Boolean)object);
            }
            else if("Float".equals(type)){
                editor.putFloat(key, (Float)object);
            }
            else if("Long".equals(type)){
                editor.putLong(key, (Long)object);
            }
            editor.apply();
        }catch (Exception ex){
            throw new IllegalThreadStateException();
        }

    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getValue(@NonNull String key, @NonNull Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }


    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public  <K, V> boolean putHashMapData(@NonNull String key, @NonNull Map<K, V> map) {
        boolean result;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于获取保存的集合
     *
     * @param key key
     * @return HashMap
     */
    public  <V> Map<String, V> getHashMapData(@NonNull String key, @NonNull Class<V> clsV) {
        try{
            String json = sp.getString(key, "");
            if(json==null){return null;}
            Map<String, V> map = new HashMap<>();
            Gson gson = new Gson();
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject value = (JsonObject) entry.getValue();
                map.put(entryKey, gson.fromJson(value, clsV));
            }
            //Log.e("SharedPreferencesUtil", obj.toString());
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public  <T> boolean setList(@NonNull String tag, @NonNull List<T> datalist) {
        try{
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(datalist);
            if (null == datalist || datalist.size() <= 0) return false;
            editor.putString(tag, strJson);
            editor.apply();
            return true;
        }catch (Exception ex ){
            Log.i("setList异常信息>>>",ex.toString()+"");
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * 获取List
     * @param tag
     * @return
     */
    public  <T>List<T> getList(@NonNull String tag,@NonNull Class<T> cls) {
        try{
            List<T> mList=new ArrayList<>();
            Gson mGson = new Gson();
            String strJson = sp.getString(tag, null);
            if(null==strJson||"".equals(strJson)){
                return null;
            }
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for(final JsonElement elem : array){
                mList.add(mGson.fromJson(elem, cls));
            }
            return mList;
        }catch (Exception ex){
            Log.i("getList异常信息>>>",ex.toString()+"");
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * 序列化对象
     * 保存对象
     * @param obj 实体类
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
    public boolean setConfig(@NonNull String key,@NonNull Object obj){
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            String serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
            objectOutputStream.close();
            byteArrayOutputStream.close();
            editor.putString(key, serStr);
            editor.apply();
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 反序列化对象
     * 获取保存对象
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unused")
    public  Object getConfig(@NonNull String key) {
        try{
            String string = sp.getString(key,null);
            String redStr = java.net.URLDecoder.decode(string, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            Object config = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return config;
        }catch(IOException ex){
            ex.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据key移除指定数据
     * @param key
     * @return
     */
    public boolean remove(@NonNull String key){
        if(key==null||"".equals(key)){return false;}
        editor.remove(key);
        editor.commit();
        return true;
    }

    /**
     * @TODO 请谨慎使用
     * 清除所有SP保存的数据
     * @param key
     * @return
     */
    public boolean clean(@NonNull String key){
        if(key==null||"".equals(key)){return false;}
        editor.clear();
        editor.commit();
        return true;
    }



}
