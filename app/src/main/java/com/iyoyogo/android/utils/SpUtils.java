package com.iyoyogo.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private static SharedPreferences sp;

    public static SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp;
    }

    //存储boolean
    public static void putBoolean(Context context, String key, boolean value) {
        getSp(context).edit().putBoolean(key, value).apply();
    }

    //取boolean
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSp(context).getBoolean(key, defValue);
    }

    //存String
    public static void putString(Context context, String key, String value) {
        getSp(context).edit().putString(key, value).apply();
    }

    //取String
    public static String getString(Context context, String key, String defValue) {
        return getSp(context).getString(key, defValue);
    }

    //存int
    public static void putInt(Context context, String key, int value) {
        getSp(context).edit().putInt(key, value).apply();
    }

    //取int
    public static int getInt(Context context, String key, int defValue) {
        return getSp(context).getInt(key, defValue);
    }

    //存long
    public static void putLong(Context context, String key, long value) {
        getSp(context).edit().putLong(key, value).apply();
    }

    //取long
    public static long getLong(Context context, String key, long defValue) {
        return getSp(context).getLong(key, defValue);
    }

    //存float
    public static void putFloat(Context context, String key, float value) {
        getSp(context).edit().putFloat(key, value).apply();
    }

    //取float
    public static float getFloat(Context context, String key, float defValue) {
        return getSp(context).getFloat(key, defValue);
    }

    //移除
    public static void remove(Context context, String key) {
        getSp(context).edit().remove(key).apply();
    }

}
