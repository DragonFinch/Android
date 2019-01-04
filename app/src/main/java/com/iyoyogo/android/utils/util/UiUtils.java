package com.iyoyogo.android.utils.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.iyoyogo.android.app.App;

/**
 * @author zhuhui
 * @date 2018/9/4
 * @description UI相关工具类
 */
public class UiUtils {

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * dp2px
     */
    public static int dip2px(float dpValue) {
        final float scale = App.getmContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px2dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources()
                .getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /** 获得状态栏的高度 */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz  = Class.forName("com.android.internal.R$dimen");
            Object   object = clazz.newInstance();
            int      height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取屏幕大小
     * @return {@link Screen}
     */
    public static Screen getScreenPix() {
        DisplayMetrics dm            =new DisplayMetrics();
        WindowManager  windowManager =(WindowManager)App.getmContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        Log.d("widthPixels", dm.widthPixels+"");
        Log.d("heightPixels", dm.heightPixels+"");
        return new Screen(dm.widthPixels, dm.heightPixels);

    }


    /**
     * 屏幕宽高参数
     */
    public static class Screen {
        public int widthPixels;
        public int heightPixels;

        public Screen(int widthPixels,int heightPixels) {
            this.widthPixels=widthPixels;
            this.heightPixels=heightPixels;
        }

        public String toString() {

            return "("+widthPixels+","+heightPixels+")";
        }
    }

    /**
     * 获取当前屏幕的截图
     * @param activity
     * @return 截图bitmap
     */
    public static Bitmap captureScreen(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp =activity.getWindow().getDecorView().getDrawingCache();

        return bmp;

    }

    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas   canvas     = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

}
