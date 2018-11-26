package com.iyoyogo.android.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.iyoyogo.android.R;

/**
 * 创建时间：2018/6/28
 * 描述：
 */
public class ShareDialog extends BottomDialogBase {
    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.dialog_share);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
