package com.iyoyogo.android.view;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.iyoyogo.android.R;

/**
 * 创建时间：2018/6/3
 * 描述：
 */
public class BaseDialog extends Dialog {
    /**
     *
     * @param context
     */
    public BaseDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    /**
     * 基础对话框
     * @param context
     * @param themeResId
     */
    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


}
