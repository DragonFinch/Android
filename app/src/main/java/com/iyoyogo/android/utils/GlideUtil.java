package com.iyoyogo.android.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.bumptech.glide.load.DecodeFormat;

public class GlideUtil {
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
}
