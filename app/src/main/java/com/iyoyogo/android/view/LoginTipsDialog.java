package com.iyoyogo.android.view;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.DensityUtil;

/**
 * 创建时间：2018/6/2
 * 描述：
 */
public class LoginTipsDialog extends Dialog {
    private Context context;

    public LoginTipsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.login_tips_dialog, null);
        setContentView(view);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = DensityUtil.dp2px(context, 183.5f);
        lp.width = DensityUtil.dp2px(context, 250);
        win.setAttributes(lp);
    }
}
