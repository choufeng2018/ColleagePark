package com.nacity.college.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nacity.college.MainApp;


/**
 * Created by xzz on 2016/1/11.
 **/
public class SpUtil {
    static Context context= MainApp.mContext;
    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences("park", Context.MODE_PRIVATE);
        return sp;
    }

    public static int getInt( String key) {
        SharedPreferences sp = getSharedPreferences(context);
        int result = sp.getInt(key, 0);
        return result;
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        int result = sp.getInt(key, defValue);
        return result;
    }

    /**
     * 从首选项中获取String值,默认值为null
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        SharedPreferences sp = getSharedPreferences(context);
        String result = sp.getString(key, null);
        return result;
    }

    /**
     * 从首选项中获取boolean值,默认值为false
     *
     * @param key
     * @return
     */
    public static boolean getBoolean( String key) {
        SharedPreferences sp = getSharedPreferences(context);
        boolean result = sp.getBoolean(key, false);
        return result;
    }

    /**
     * 向首选项中存储数据(仅限于String,int,boolean三种数据类型)
     *
     * @param key
     * @param value
     */
    public static void put( String key, Object value) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }


}