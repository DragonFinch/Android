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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.ToastUtil;

/**
 * 创建时间：2018/6/30
 * 描述：
 */
public class AddLabelDialog extends BaseDialog {
    private Context context;
    private View view;
    private ImageView dialog_close;
    private TextView dialog_cancel;
    private TextView dialog_confirm;
    private TextView title_dialog;
    private EditText lable_et;
    private View div;
    private AddLabelDialogCallback labelDialogCallback;

    public AddLabelDialogCallback getLabelDialogCallback() {
        return labelDialogCallback;
    }

    public void setLabelDialogCallback(AddLabelDialogCallback labelDialogCallback) {
        this.labelDialogCallback = labelDialogCallback;
    }

    public AddLabelDialog(@NonNull Context context) {
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
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) context).isFinishing() && isShowing()) {
                    dismiss();
                    if (labelDialogCallback != null) {
                        labelDialogCallback.onCancel();
                    }
                }

            }
        });
        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) context).isFinishing() && isShowing()) {
                    dismiss();
                }
            }
        });

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = lable_et.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(context, "请输入标签名");
                    return;
                }
                if (!((Activity) context).isFinishing() && isShowing()) {
                    dismiss();
                    if (labelDialogCallback != null) {
                        labelDialogCallback.onConfirm(content);
                        lable_et.setText("");
                    }
                }
            }
        });
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_add_label, null);
        setContentView(view);
        dialog_close = view.findViewById(R.id.dialog_close);
        dialog_cancel = view.findViewById(R.id.dialog_cancel);
        dialog_confirm = view.findViewById(R.id.dialog_confirm);
        title_dialog = view.findViewById(R.id.title_dialog);
        lable_et = view.findViewById(R.id.lable_et);
        div = view.findViewById(R.id.div);
    }

    public interface AddLabelDialogCallback {
        void onConfirm(String content);

        void onCancel();
    }
}
