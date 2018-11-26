package com.iyoyogo.android.view;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.DensityUtil;

/**
 * 创建时间：2018/6/3
 * 描述：
 */
public class YoyogoCustomDialog extends BaseDialog {
    private Context context;
    private View view;
    private ImageView dialog_close;
    private TextView dialog_cancel;
    private TextView dialog_confirm;
    private YoyogoCuStomDialogCallback yoyogoCuStomDialogCallback;
    private TextView title_dialog;
    private TextView dialogMsg;
    private View div;

    public void setYoyogoCuStomDialogCallback(YoyogoCuStomDialogCallback yoyogoCuStomDialogCallback) {
        this.yoyogoCuStomDialogCallback = yoyogoCuStomDialogCallback;
    }

    public interface YoyogoCuStomDialogCallback {
        void onConfirm();

        void onCancel();
    }

    public YoyogoCustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
        initListener();
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = DensityUtil.dp2px(context, 183.5f);
        lp.width = DensityUtil.dp2px(context, 250);
        win.setAttributes(lp);
    }

    private void initListener() {
        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) context).isFinishing() && isShowing()) {
                    dismiss();
                }
            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) context).isFinishing() && isShowing()) {
                    dismiss();
                    if (yoyogoCuStomDialogCallback != null) {
                        yoyogoCuStomDialogCallback.onCancel();
                    }
                }
            }
        });
        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yoyogoCuStomDialogCallback != null) {
                    yoyogoCuStomDialogCallback.onConfirm();
                }
            }
        });
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.login_tips_dialog, null);
        setContentView(view);
        dialog_close = view.findViewById(R.id.dialog_close);
        dialog_cancel = view.findViewById(R.id.dialog_cancel);
        dialog_confirm = view.findViewById(R.id.dialog_confirm);
        title_dialog = view.findViewById(R.id.title_dialog);
        dialogMsg = view.findViewById(R.id.msg_dialog);
        div = view.findViewById(R.id.div);
    }

    public void setDialogTitle(String title) {
        if (title_dialog != null && !TextUtils.isEmpty(title)) {
            title_dialog.setText(title);
        }
    }

    public void setDialogContent(String content) {
        if (dialogMsg != null && !TextUtils.isEmpty(content)) {
            dialogMsg.setText(content);
        }
    }

    public void setCancleText(String text) {
        if (dialog_cancel != null && !TextUtils.isEmpty(text)) {
            dialog_cancel.setText(text);
        }
        dialog_cancel.setVisibility(View.VISIBLE);
        setDivStatus();
    }

    private void setDivStatus() {
        if (dialog_cancel.getVisibility() == View.VISIBLE && dialog_confirm.getVisibility() == View.VISIBLE) {
            div.setVisibility(View.VISIBLE);
        }
    }

    public void setConfirmText(String text) {
        if (dialog_confirm != null & !TextUtils.isEmpty(text)) {
            dialog_confirm.setText(text);
        }
        dialog_confirm.setVisibility(View.VISIBLE);
        setDivStatus();
    }
}
