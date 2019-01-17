package com.iyoyogo.android.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.SimpleAnimationUtils;

/**
 * @author zhuhui
 * @date 2019/1/17
 * @description
 */
public class CommonPopup extends BasePopupWindow implements View.OnClickListener {

    private TextView  mTvFirst;
    private TextView  mTvSecond;
    private TextView  mTvThird;
    private ImageView mPopImId;
    private TextView  mTvCancel;
    private TextView  mTvDone;

    private OnCommonClick mOnCommonClick;

    public CommonPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        setBackgroundColor(Color.parseColor("#8f000000"));

        mTvFirst = findViewById(R.id.tv_first);
        mTvSecond = findViewById(R.id.tv_second);
        mTvThird = findViewById(R.id.tv_third);
        mPopImId = findViewById(R.id.pop_im_id);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvDone = findViewById(R.id.tv_done);

        mTvCancel.setOnClickListener(this);
        mTvDone.setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_common);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return SimpleAnimationUtils.getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return SimpleAnimationUtils.getDefaultScaleAnimation(false);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.tv_done:
                if (mOnCommonClick != null) {
                    mOnCommonClick.onCommonClick(v);
                }
                break;
//            case R.id.tv_cancel:
//                dismiss();
//                break;
        }
    }

    public void setContent(String first, String second, String third) {
        mTvFirst.setText(first);
        mTvSecond.setText(second);
        mTvThird.setText(third);
    }

    public void setBtnText(String cancel, String done) {
        mTvDone.setText(done);
        mTvCancel.setText(cancel);
    }

    public void setOnCommonClick(OnCommonClick onCommonClick) {
        mOnCommonClick = onCommonClick;
    }

    public interface OnCommonClick {
        void onCommonClick(View v);
    }
}