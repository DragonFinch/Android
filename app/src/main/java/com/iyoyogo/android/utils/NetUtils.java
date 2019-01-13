package com.iyoyogo.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 此类作用：网络判断工具类
 * <p>
 * 作者：LiuHW
 * <p>
 * 邮箱：zixuan06010@126.com
 */

public class NetUtils {
    //监测网络是否连接
    public static boolean isInternetConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }
}
