package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.contract.VipCenterContract;
import com.iyoyogo.android.presenter.VipCenterPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import butterknife.BindView;

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

    private void initVipLevelUp() {
        View view = getLayoutInflater().inflate(R.layout.popup_up_vip, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(getApplicationContext(), 300), DensityUtil.dp2px(getApplicationContext(), 320), true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        ImageView img_wechat = view.findViewById(R.id.img_wechat);
        ImageView img_wechat_circle = view.findViewById(R.id.img_wechat_circle);
        ImageView img_qq = view.findViewById(R.id.img_qq);
        ImageView img_sina = view.findViewById(R.id.img_sina);
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
        String url = "http://192.168.0.145/home/share/levelup/user_id/" + SpUtils.getString(getApplicationContext(), "user_id", null);
        UMWeb web = new UMWeb(url);
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
                initVipLevelUp();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
