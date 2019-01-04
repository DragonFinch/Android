package com.iyoyogo.android.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.iyoyogo.android.R;

public class LoadingDialog {
    public Dialog  mLoadingDialog;
    public Context context;

    private static LoadingDialog instance;

    public static LoadingDialog get() {
        if (instance == null) {
            instance = new LoadingDialog();
        }
        return instance;
    }

    public LoadingDialog create(Context context) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return this;
    }

    public void show() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void close() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                Context context = ((ContextWrapper) mLoadingDialog.getContext()).getBaseContext();
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                        mLoadingDialog.dismiss();
                } else {
                    mLoadingDialog.dismiss();
                }
                mLoadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
