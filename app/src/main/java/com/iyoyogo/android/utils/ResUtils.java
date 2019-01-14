package com.iyoyogo.android.utils;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 此类作用：资源文件获取工具类
 * <p>
 * 作者：LiuHW
 * <p>
 * 邮箱：zixuan06010@126.com
 */

public class ResUtils {
    private static Context sContext;

    private ResUtils() {
        throw new IllegalAccessError("You can't get instance of this class");
    }

    /**
     * This method must be called on application create
     *
     * @param context application context
     */
    public static void updateContext(Context context) {
        sContext = context.getApplicationContext();
    }

    public static String getString(int stringId) {
        return sContext.getResources().getString(stringId);
    }

    public static String getString(int stringId, int param) {
        return sContext.getResources().getString(stringId, param);
    }

    public static int getInt(int intId) {
        return sContext.getResources().getInteger(intId);
    }

    public static String[] getStringArray(int stringArrayId) {
        return sContext.getResources().getStringArray(stringArrayId);
    }

    public static int[] getIntArray(int intArrayId) {
        return sContext.getResources().getIntArray(intArrayId);
    }

    public static int getColor(int colorId) {
        return sContext.getResources().getColor(colorId);
    }

    public static int getDimension(int dimensionId) {
        return (int) sContext.getResources().getDimension(dimensionId);
    }


    public static boolean isPortOrientation() {
        return sContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /*
        0.75 - ldpi
        1.0 - mdpi
        1.5 - hdpi
        2.0 - xhdpi
        3.0 - xxhdpi
        4.0 - xxxhdpi
     */
    public static float getDensityKoef() {
        return sContext.getResources().getDisplayMetrics().density;
    }

    public static int getDensity() {
        float density = getDensityKoef();
        if (density >= 4.0) {
            return 640; //DisplayMetrics.DENSITY_XXXHIGH;
        }
        if (density >= 3.0) {
            return 480; //DisplayMetrics.DENSITY_XXHIGH;
        }
        if (density >= 2.0) {
            return DisplayMetrics.DENSITY_XHIGH;
        }
        if (density >= 1.5) {
            return DisplayMetrics.DENSITY_HIGH;
        }
        if (density >= 1.0) {
            return DisplayMetrics.DENSITY_MEDIUM;
        }
        if (density >= 0.75) {
            return DisplayMetrics.DENSITY_LOW;
        }
        return DisplayMetrics.DENSITY_MEDIUM;
    }


    public static float dpFromPx(float px) {
        return px / sContext.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(float dp) {
        return dp * sContext.getResources().getDisplayMetrics().density;
    }

    public static Drawable getDrawable(int drawableID) {
        Drawable drawable = sContext.getResources().getDrawable(drawableID);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        return drawable;
    }

    public static int[] getScreenWH() {
        int[] wh = new int[2];
        WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        wh[0] = wm.getDefaultDisplay().getWidth();
        wh[1] = wm.getDefaultDisplay().getHeight();
        return wh;
    }


}
