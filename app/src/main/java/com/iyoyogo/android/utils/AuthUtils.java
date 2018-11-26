package com.iyoyogo.android.utils;


import com.iyoyogo.android.app.AppInfo;

/**
 * Created by wgheng on 2018/7/20.
 * Description :
 */
public class AuthUtils {

    private static final String SECURITY_KEY = "yYER!@UO-L#F13^Ey!@";

    public synchronized static String getAuth() {
        String sid = AppInfo.getDeviceID();
        long time = System.currentTimeMillis();
        String auth = getAuth(sid, time);
        return sid + "_" + time + "_" + auth;

    }

    public static String getAuth(String sid, long time) {
        String encodeFirst = MD5Utils.encode(sid + '_' + time + SECURITY_KEY);
        String encodeSecond = MD5Utils.encode(encodeFirst.substring(0, 12));
        return encodeSecond.substring(0, 16);
    }

}
