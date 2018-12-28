package com.iyoyogo.android.ui.mine.homepage;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.contract.VipCenterContract;
import com.iyoyogo.android.presenter.VipCenterPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipCenterActivity extends BaseActivity<VipCenterContract.Presenter> implements VipCenterContract.View {
    @BindView(R.id.my_icon)
    ImageView myIcon;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    //会员中心
    private View pop_view;
    private PopupWindow popMenu;
    private RelativeLayout relativeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_center;
    }

    protected void initView() {
        super.initView();
        statusbar();
        StatusBarUtils.setWindowStatusBarColor(VipCenterActivity.this, R.color.white);
        init();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(this, "user_id", null);
        String user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getVipCenter(user_id, user_token);
    }

    @Override
    protected VipCenterContract.Presenter createPresenter() {
        return new VipCenterPresenter(this);
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

    @Override
    public void getVipCenterSuccess(VipCenterBean vipCenterBean) {
        List<VipCenterBean.DataBean.LevelBean> list = vipCenterBean.getData().getLevel();
        for (int i = 0; i < list.size(); i++) {
            int level = list.get(i).getLevel();
            if (level == 0) {
                myIcon.setBackgroundResource(R.mipmap.mem_foxi);
            } else if (level == 1) {
                myIcon.setBackgroundResource(R.mipmap.mem_xiansan);
            } else if (level == 2) {
                myIcon.setBackgroundResource(R.mipmap.mem_gongcheng);
            } else if (level == 3) {
                myIcon.setBackgroundResource(R.mipmap.mem_mingri);
            } else if (level == 4) {
                myIcon.setBackgroundResource(R.mipmap.mem_shouxi);
            } else if (level == 5) {
                myIcon.setBackgroundResource(R.mipmap.mem_lvxingjia);
            }
//            int score = list.get(i).getScore();
//            if (score <= 99) {
//                tvVip.setText("入门");
//            } else if (score <= 100 && score < 199){
//                tvVip.setText("Lv1");
//            }else if (score <= 200 && score < 499){
//                tvVip.setText("Lv2");
//            }else if (score <= 500 && score < 199){
//                tvVip.setText("Lv3");
//            }else if (score <= 199 && score < 99){
//                tvVip.setText("Lv4");
//            }else if (score <= 199 && score < 99){
//                tvVip.setText("Lv5");
//            }else if (score <= 199 && score < 99){
//                tvVip.setText("Lv6");
//            }
//                int score0 = list.get(0).getScore();
//            int score1 = list.get(1).getScore();
//            int score2 = list.get(2).getScore();
//            int score3 = list.get(3).getScore();
//            int score4 = list.get(4).getScore();
//            int score5 = list.get(5).getScore();
//
//            Log.e("1", score1 - score0 + "/////+");//100
//            Log.e("2", score2 - score1 + "/////++");//100
//            Log.e("3", score3 - score2 + "/////+++");//300
//            Log.e("4", score4 - score3 + "/////++++");//1500
//            Log.e("5", score5 - score4 + "/////+++++");//8000


        }
    }
}
