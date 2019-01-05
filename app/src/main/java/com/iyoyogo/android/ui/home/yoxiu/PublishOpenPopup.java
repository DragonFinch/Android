package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.iyoyogo.android.R;

import razerdp.basepopup.BasePopupWindow;


/**
 * @author zhuhui
 * @date 2019/1/5
 * @description
 */
public class PublishOpenPopup extends BasePopupWindow implements View.OnClickListener {

    private OpenPopupClick mOpenPopupClick;

    public PublishOpenPopup(Context context) {
        super(context);
        setPopupFadeEnable(true);
        bindEvent();
        setPopupGravity(Gravity.TOP|Gravity.RIGHT);
    }

    private void bindEvent() {
        findViewById(R.id.tv_public).setOnClickListener(this);
        findViewById(R.id.tv_private).setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_publish_open);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mOpenPopupClick != null) {
            switch (v.getId()) {
                case R.id.tv_public:
                    mOpenPopupClick.onOpenClick(1, "公开");
                    break;
                case R.id.tv_private:
                    mOpenPopupClick.onOpenClick(2, "私密");
                    break;
            }
        }
    }

    public void setOpenPopupClick(OpenPopupClick openPopupClick) {
        mOpenPopupClick = openPopupClick;
    }

    public interface OpenPopupClick {
        void onOpenClick(int type, String typeName);
    }
}
