package com.iyoyogo.android.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;

public class GlideUtils {
    private static GlideUtils mInstance;
    private RequestOptions options;

    public static DecodeFormat getDecodeFormat(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        DecodeFormat decodeFormat = DecodeFormat.DEFAULT;
        if (null != am) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(memoryInfo);
            //decodeFormat = memoryInfo.lowMemory?DecodeFormat.PREFER_RGB_565:DecodeFormat.PREFER_ARGB_8888;
            decodeFormat = DecodeFormat.PREFER_ARGB_8888;
        }
        return decodeFormat;
    }
    private GlideUtils() {
        options = new RequestOptions();
        options.error(R.mipmap.default_ic);
        options.placeholder(R.mipmap.default_ic);
    }


    /****
     *
     * glide  单例模式
     *
     * @return
     */
    public static GlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtils();
                }
            }
        }
        return mInstance;
    }

    /***
     * 加载网络的图片
     *
     * @param context con
     * @param url  网络的地址
     * @param view ImageView
     */
    public void displayImageFromUrl(Context context, String url, ImageView view) {
        try {
            Glide.with(context).load(url).apply(options).into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 加载本地的图片
     *
     * @param context con
     * @param drawableId  本地的图片资源
     * @param view ImageView
     */
    public void displayImageFromRes(Context context, int drawableId, ImageView view) {
        Glide.with(context).load(drawableId).apply(options).into(view);
    }
}

