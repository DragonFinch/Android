package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.DensityUtil;

public class LoadingPopupWindow extends BasePopupWindow {
    private View mContentView;
    private Context context;

    public LoadingPopupWindow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.loading_view, null);
        setContentView(mContentView);
        setWidth(DensityUtil.dp2px(context, 250));
        setHeight(DensityUtil.dp2px(context, 183.5f));
        setTouchable(false);
        setFocusable(false);
        setBackgroundDrawable(new ColorDrawable());
        // 重写onKeyListener
        mContentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public View getmContentView() {
        return mContentView;
    }

    public void popWindowshow(View v) {
        this.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    public void popWindowDismiss() {
        this.dismiss();
        ;
    }
}
