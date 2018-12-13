package com.iyoyogo.android.ui.mine.homepage;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.iyoyogo.android.R;

public class VipCenterActivity extends AppCompatActivity {
//会员中心
    private View pop_view;
    private PopupWindow popMenu;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_center);


        init();


    }

    private void init() {

        relativeLayout = findViewById(R.id.vip_promote_rl_id);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupPraise();
            }
        });
    }


    private void initPopupPraise() {
        pop_view = View.inflate(this, R.layout.popup_vip_promote, null);
        pop_view.findViewById(R.id.popup_praise_im_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
            }
        });
        popMenu = new PopupWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popMenu.setFocusable(true);//设置pw中的控件能够获取焦点
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popMenu.setBackgroundDrawable(dw);//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
        popMenu.update();//刷新mPopupWindow
        popMenu.showAsDropDown(pop_view, Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        /**
         * PopupWindow消失监听方法
         */
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }
}
