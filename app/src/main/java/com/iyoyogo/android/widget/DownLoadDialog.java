package com.iyoyogo.android.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.ResUtils;


/**
 * 此类的作用：自定义下载进度框
 * <p>
 * Created by LiuHW on 2017/8/15.
 * <p>
 * 邮箱：zixuan06010@126.com
 */

public class DownLoadDialog extends Dialog {

    private Context context;
    private ImageView loding;
    private LinearLayout layout;
    private TextView textView;

    private LoadingBack loadingback;

    public DownLoadDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DownLoadDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public DownLoadDialog(@NonNull Context context, @StyleRes int themeResId, LoadingBack tran) {
        super(context, themeResId);
        this.context = context;
        loadingback = tran;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_down_loading, null);
        setContentView(view);
        loding = (ImageView) view.findViewById(R.id.image_dialog_loading);
        layout = (LinearLayout) view.findViewById(R.id.layout_loading);
        textView = (TextView) view.findViewById(R.id.text_dialog_loading);
        if (loadingback != null) {
            switch (loadingback) {
                case TRAN:
                    layout.setBackgroundColor(ResUtils.getColor(R.color.tran));
                    textView.setVisibility(View.GONE);
                    break;
            }
        }
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        loding.startAnimation(hyperspaceJumpAnimation);
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        Display d = manager.getDefaultDisplay();
        params.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(params);
        setCancelable(false);
    }


    public void showDialog() {
        if (!isShowing()) {
            show();
        }
    }

    public void dismisDialog() {
        if (isShowing()) {
            dismiss();
        }
    }
}
