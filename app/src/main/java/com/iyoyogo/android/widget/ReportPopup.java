package com.iyoyogo.android.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.iyoyogo.android.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author zhuhui
 * @date 2019/1/18
 * @description
 */
public class ReportPopup extends BasePopupWindow implements View.OnClickListener {

    public TextView mTvFirst;
    public TextView mTvSecond;
    public TextView mTvThird;
    public TextView mTvFourth;
    public TextView mTvCancel;

    private OnReportListener mOnReportListener;

    public ReportPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        setBackgroundColor(Color.parseColor("#8f000000"));
        mTvFirst = findViewById(R.id.tv_first);
        mTvSecond = findViewById(R.id.tv_second);
        mTvThird = findViewById(R.id.tv_third);
        mTvFourth = findViewById(R.id.tv_fourth);
        mTvCancel = findViewById(R.id.tv_cancel);

        mTvFirst.setOnClickListener(this);
        mTvSecond.setOnClickListener(this);
        mTvThird.setOnClickListener(this);
        mTvFourth.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_report);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 260);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 260);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mOnReportListener != null) {
            switch (v.getId()) {
                case R.id.tv_first:
                    mOnReportListener.onReport(mTvFirst.getText().toString());
                    break;
                case R.id.tv_second:
                    mOnReportListener.onReport(mTvSecond.getText().toString());
                    break;
                case R.id.tv_third:
                    mOnReportListener.onReport(mTvThird.getText().toString());
                    break;
                case R.id.tv_fourth:
                    mOnReportListener.onReport(mTvFourth.getText().toString());
                    break;
            }
        }
    }

    public void setOnReportListener(OnReportListener onReportListener) {
        mOnReportListener = onReportListener;
    }

    public interface OnReportListener {
        void onReport(String content);
    }
}
