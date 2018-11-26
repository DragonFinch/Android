package com.iyoyogo.android.app;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.iyoyogo.android.utils.SpUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by wgheng on 2018/7/10.
 * Description :
 */
public class AppInfo {

    private static final String KEY_UUID = "uuid";

    /**
     * 获取当前应用版本名称
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            //获取packageInfo
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            //获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前应用版本号
     */
    public static int getVersionCode(Context context) {

        int versionCode = 0;
        try {
            //获取packageInfo
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            //获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取发版时间
     */
    public static String getReleaseTime(Context context) {

        String releaseTime = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            releaseTime = appInfo.metaData.getString("RELEASE_TIME");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return releaseTime;
    }

    /**
     * 获取渠道名
     */
    public static String getChannelId() {
        String channelId = null;
        //TODO
        channelId = "1234";
        return channelId;
    }

    /**
     * 获取进程名
     */
    public static String getCurProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps != null) {
                for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
                    if (proInfo.pid == android.os.Process.myPid()) {
                        if (proInfo.processName != null) {
                            return proInfo.processName;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 唯一标识，暂以UUID代替
     */
    public static String getDeviceID() {
        //获取uuid（第一次获取时随机生成保存到本地，后续从sp获取）
        String uuid = SpUtils.getString(App.context, KEY_UUID, null);
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SpUtils.putString(App.context, KEY_UUID, uuid);
        }
        return uuid;
    }
}
