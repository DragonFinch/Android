package com.iyoyogo.android.utils;


import android.content.Context;
import android.view.View;

import com.iyoyogo.android.view.LoadingPopupWindow;

/**
 * 创建时间：2018/6/7
 * 描述：
 */
public class PopwindowLoadingUtil {
    static LoadingPopupWindow popupWindow;

    public static void showLoadingPop(View v, Context mContext) {
//        if (alertDialog==null) {
//            alertDialog = new LoadingDialog(mContext);
//        }
//        alertDialog.show();
        popupWindow = new LoadingPopupWindow(mContext);
        popupWindow.popWindowshow(v);
    }

    public static void dismissLoadingPop() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.popWindowDismiss();
            popupWindow = null;
        }
    }
}
