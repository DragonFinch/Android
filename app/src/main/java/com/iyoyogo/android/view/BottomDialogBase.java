package com.iyoyogo.android.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.iyoyogo.android.R;


/**
 * 创建时间：2018/5/30
 * 描述：
 */
public abstract class BottomDialogBase extends Dialog {
    protected Context mContext;

    public BottomDialogBase(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    protected void init() {
        Window win = this.getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        onCreate();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.BottomInAndOutStyle;
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);
        win.setBackgroundDrawableResource(android.R.color.transparent);
    }

    protected abstract void onCreate();
}