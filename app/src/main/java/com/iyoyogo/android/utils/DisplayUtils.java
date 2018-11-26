package com.iyoyogo.android.utils;


import android.util.DisplayMetrics;

import com.iyoyogo.android.app.App;


/**
 * Created by wgheng on 2018/7/18.
 * Description :
 */
public class DisplayUtils {

    public static int getScreenWidth() {
        DisplayMetrics display = App.context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics display =  App.context.getResources().getDisplayMetrics();
        return display.heightPixels;
    }
}
