package com.iyoyogo.android.ui.mine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.iyoyogo.android.R;

import butterknife.OnClick;

public class WeChatUntyingDialog extends Dialog {

    private Context context;

    public WeChatUntyingDialog(final Context context) {
        super(context, R.style.RemindDialog);// 必须调用父类的构造函数
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_untying, null);
        setContentView(contentView);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    @OnClick({R.id.tv_unty, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_unty:
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
