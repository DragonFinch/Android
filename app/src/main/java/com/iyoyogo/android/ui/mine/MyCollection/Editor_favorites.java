package com.iyoyogo.android.ui.mine.MyCollection;


import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iyoyogo.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Editor_favorites extends AppCompatActivity {
    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.editsc_tv_id)
    TextView editscTvId;
    @BindView(R.id.editbc_tv_id)
    TextView editbcTvId;
    private View pop_view;
    private PopupWindow popMenu;
    private TextView poptitle;
    private TextView popcontent;
    private TextView popno;
    private ImageView popim;
    private TextView popyes;

    //编辑收藏夹
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_favorites);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.back_iv_id, R.id.editsc_tv_id, R.id.editbc_tv_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.editsc_tv_id:
                initPopuptWindow();
                poptitle.setText(R.string.mazi);
                popcontent.setText(R.string.shifoubaocun);
                popno.setText(R.string.fangqi);
                popyes.setText(R.string.zhunle);
                popim.setImageResource(R.mipmap.stamp_shijian);
                break;

            case R.id.editbc_tv_id:
                initPopupPraise();
                break;
        }
    }

    @SuppressLint("NewApi")
    private void initPopuptWindow() {
        pop_view = View.inflate(this, R.layout.item_praise_popup, null);


        poptitle = pop_view.findViewById(R.id.pop_title_id);
        popcontent = pop_view.findViewById(R.id.pop_content_id);
        popno = pop_view.findViewById(R.id.popup_no_id);
        popyes = pop_view.findViewById(R.id.popup_yes_id);
        popim = pop_view.findViewById(R.id.pop_im_id);


        pop_view.findViewById(R.id.popup_im_id).setOnClickListener(new View.OnClickListener() {
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

    @SuppressLint("NewApi")
    private void initPopupPraise() {
        pop_view = View.inflate(this, R.layout.popup_favorites_prompt, null);
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
