package com.iyoyogo.android.ui.mine;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.logo_iv_id)
    ImageView logoIvId;
    @BindView(R.id.wxpublic_rl_id)
    RelativeLayout wxpublicRlId;
    @BindView(R.id.sina_rl_id)
    RelativeLayout sinaRlId;
    @BindView(R.id.praise_rl_id)
    RelativeLayout praiseRlId;
    @BindView(R.id.tv_simi)
    TextView tvSimi;
    @BindView(R.id.tv_service)
    TextView tvService;
    private View pop_view;
    private PopupWindow popMenu;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initPopuptWindow() {
        pop_view = View.inflate(this, R.layout.item_praise_popup, null);
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
        backgroundAlpha(0.6f);
        popMenu.showAsDropDown(pop_view, Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        /**
         * PopupWindow消失监听方法
         */
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }
    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
    getWindow().setAttributes(lp); //act 是上下文context

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.back_iv_id, R.id.praise_rl_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.praise_rl_id:
                initPopuptWindow();
                break;
        }
    }
}
