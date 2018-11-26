package com.iyoyogo.android.widget;


import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.App;

/**
 * Created by wgheng on 2017/12/25.
 * Description : spanText处理
 */

public class CustomSpan extends ClickableSpan {

    private final boolean hasUnderline;
    private OnClickListener listener;

    public CustomSpan(boolean hasUnderline) {
        this.hasUnderline = hasUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(hasUnderline);
        ds.setColor(ContextCompat.getColor(App.context, R.color.theme_blue));
    }

    @Override
    public void onClick(View widget) {
        if (listener != null) listener.onClick();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick();
    }
}
