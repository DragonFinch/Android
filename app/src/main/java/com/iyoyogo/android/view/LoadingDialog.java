package com.iyoyogo.android.view;


import android.content.Context;
import android.support.annotation.NonNull;
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

/**
 * 创建时间：2018/6/30
 * 描述：
 */
public class LoadingDialog extends BaseDialog {
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

    public LoadingDialog(@NonNull Context context) {
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

    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.loading_view, null);
        setContentView(view);
    }

    public interface AddLabelDialogCallback {
        void onConfirm();

        void onCancel();
    }
}
