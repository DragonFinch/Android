package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.contract.VipCenterContract;
import com.iyoyogo.android.presenter.VipCenterPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.UiUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员中心
 */

public class VipCenterActivity extends BaseActivity<VipCenterContract.Presenter> implements VipCenterContract.View {
    @BindView(R.id.my_icon)
    ImageView       myIcon;
    @BindView(R.id.tv_vip)
    TextView        tvVip;
    @BindView(R.id.tv_name)
    TextView        tvName;
    @BindView(R.id.tv_flag)
    TextView        tvFlag;
    @BindView(R.id.img_logo)
    CircleImageView imgLogo;
    @BindView(R.id.img_vip_sign)
    ImageView       imgVipSign;
    @BindView(R.id.img_level)
    ImageView       imgLevel;
    @BindView(R.id.progressBar)
    ProgressBar     mProgressBar;
    @BindView(R.id.tv_current_score)
    TextView        mTvCurrentScore;
    @BindView(R.id.tv_level1)
    TextView        mTvLevel1;
    @BindView(R.id.tv_level2)
    TextView        mTvLevel2;
    @BindView(R.id.tv_level3)
    TextView        mTvLevel3;
    @BindView(R.id.tv_level4)
    TextView        mTvLevel4;
    @BindView(R.id.tv_level5)
    TextView        mTvLevel5;
    @BindView(R.id.tv_level6)
    TextView        mTvLevel6;
    //会员中心
    private View           pop_view;
    private PopupWindow    popMenu;
    private RelativeLayout relativeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_center;
    }

    protected void initView() {
        super.initView();
        statusbar();
        init();
    }

    private void initVipLevelUp() {
        View        view        = getLayoutInflater().inflate(R.layout.popup_up_vip, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(getApplicationContext(), 300), DensityUtil.dp2px(getApplicationContext(), 320), true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        ImageView img_wechat        = view.findViewById(R.id.img_wechat);
        ImageView img_wechat_circle = view.findViewById(R.id.img_wechat_circle);
        ImageView img_qq            = view.findViewById(R.id.img_qq);
        ImageView img_sina          = view.findViewById(R.id.img_sina);
        img_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                shareWeb(SHARE_MEDIA.QQ);
            }
        });
        img_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN);
            }
        });
        img_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                shareWeb(SHARE_MEDIA.SINA);
            }
        });
        img_wechat_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAtLocation(findViewById(R.id.activity_vip_center), Gravity.CENTER, 0, 0);
    }


    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = Constants.BASE_URL + "home/share/levelup/user_id/" + SpUtils.getString(getApplicationContext(), "user_id", null);
        UMWeb  web = new UMWeb(url);
        web.setTitle("title");//标题
        UMImage thumb = new UMImage(getApplicationContext(), R.mipmap.logo);
        web.setThumb(thumb);  //缩略图
        web.setDescription("YoYoGo");//描述
       /* if (!TextUtils.isEmpty(desc)) {

        }*/
        new ShareAction(VipCenterActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @OnClick(R.id.return_img)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id    = SpUtils.getString(this, "user_id", null);
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
        List<VipCenterBean.DataBean.LevelBean> list      = vipCenterBean.getData().getLevel();
        VipCenterBean.DataBean.UserInfoBean    user_info = vipCenterBean.getData().getUser_info();
        tvName.setText(user_info.getUser_nickname());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.default_touxiang).placeholder(R.mipmap.default_touxiang);
        Glide.with(this).load(user_info.getUser_logo()).apply(requestOptions).into(imgLogo);

        List<VipCenterBean.DataBean.LevelBean> level = vipCenterBean.getData().getLevel();
        mTvLevel1.setText(level.get(0).getScore() + "");
        mTvLevel2.setText(level.get(1).getScore() + "");
        mTvLevel3.setText(level.get(2).getScore() + "");
        mTvLevel4.setText(level.get(3).getScore() + "");
        mTvLevel5.setText(level.get(4).getScore() + "");
        mTvLevel6.setText(level.get(5).getScore() + "");

        int score = vipCenterBean.getData().getUser_info().getScore();
        int max   = level.get(level.size() - 1).getScore();
        mProgressBar.setMax(max);
        mTvCurrentScore.setText(score+"");
        for (int i = 0; i < level.size(); i++) {
            if (i != level.size() - 1) {
                if (score >= level.get(i).getScore() && score < level.get(i + 1).getScore()) {
                    double d = (double) max / (double) 5;
                    double s = (double) score / (double) level.get(i + 1).getScore();
                    mProgressBar.setProgress((int) (d * i + s * d));
                }
            } else {
                if (score >= level.get(i).getScore()) {
                    mProgressBar.setProgress(max);
                }
            }
        }

        if (score >= 0 && score < level.get(1).getScore()) {
            tvVip.setText("入门");
            imgLevel.setVisibility(View.GONE);
            imgVipSign.setImageResource(R.mipmap.level_zero);
            tvFlag.setText(list.get(0).getName());
            myIcon.setBackgroundResource(R.mipmap.mem_foxi);
        } else if (score >= level.get(1).getScore() && score < level.get(2).getScore()) {
            tvVip.setText("Lv1");
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv1);
            imgVipSign.setImageResource(R.mipmap.level_one);
            tvFlag.setText(list.get(1).getName());
            myIcon.setBackgroundResource(R.mipmap.mem_xiansan);
        } else if (score >= level.get(2).getScore() && score < level.get(3).getScore()) {
            tvVip.setText("Lv2");
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv2);
            imgVipSign.setImageResource(R.mipmap.level_two);
            tvFlag.setText(list.get(2).getName());
            myIcon.setBackgroundResource(R.mipmap.mem_gongcheng);
        } else if (score >= level.get(3).getScore() && score < level.get(4).getScore()) {
            tvVip.setText("Lv3");
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv3);
            imgVipSign.setImageResource(R.mipmap.level_three);
            tvFlag.setText(list.get(3).getName());
            myIcon.setBackgroundResource(R.mipmap.mem_mingri);
        } else if (score >= level.get(4).getScore() && score < level.get(5).getScore()) {
            tvVip.setText("Lv4");
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv4);
            imgVipSign.setImageResource(R.mipmap.level_four);
            tvFlag.setText(list.get(4).getName());
            myIcon.setBackgroundResource(R.mipmap.mem_shouxi);
        } else if (score == level.get(5).getScore()) {
            tvVip.setText("Lv5");
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv5);
            imgVipSign.setImageResource(R.mipmap.level_five);
            tvFlag.setText(list.get(5).getName());
            myIcon.setBackgroundResource(R.mipmap.mem_lvxingjia);
        }

//            int score0 = list.get(0).getScore();
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
//
//
//        }
        setScore();
    }

    private void setScore() {
        int   w     = UiUtils.dip2px(25 * 6 + 50 * 5 + 5);
        float index = (float) mProgressBar.getProgress() / (float) mProgressBar.getMax();
        float pro   = index * w - UiUtils.dip2px(20f);
        pro = pro > (w - UiUtils.dip2px(30)) ? (w - UiUtils.dip2px(30)) : pro;
        TranslateAnimation ani;
        ani = new TranslateAnimation(mTvCurrentScore.getX(), pro, 0, 0);
        ani.setDuration(10);
        ani.setFillAfter(true);
        mTvCurrentScore.startAnimation(ani);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
