package com.iyoyogo.android.ui.home.recommend;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.utils.DensityUtil;

public class YoXiuDetailActivity extends BaseActivity {



    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_xiu_detail;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    public void share(){
        View view = getLayoutInflater().inflate(R.layout.popup_share, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup_share.setBackgroundDrawable(new ColorDrawable());
        popup_share.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_share.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout qq_layout=view.findViewById(R.id.qq_layout);
        LinearLayout comment_layout=view.findViewById(R.id.comment_layout);
        LinearLayout wechat_layout=view.findViewById(R.id.wechat_layout);
        LinearLayout sina_layout=view.findViewById(R.id.sina_layout);
        TextView tv_cancel=view.findViewById(R.id.cancel);
        ImageView img_close=view.findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        sina_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);

    }


    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }
}
