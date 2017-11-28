package com.dopool.proutil.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class SPUtil {

    /**
     * 保存内容到sp
     */
    public static void saveDataToSP(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    /**
     * 得到内容从sp
     */
    public static String readDataFromSP(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }


    /**
     * 保存内容到sp
     */
    public static void saveDataToSP(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 得到内容从sp
     */
    public static boolean readDataFromSP(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }
}
