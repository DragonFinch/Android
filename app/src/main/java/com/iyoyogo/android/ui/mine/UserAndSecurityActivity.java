package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.contract.UserAndSecurityContract;
import com.iyoyogo.android.presenter.UserAndSecurityPresenter;

import com.iyoyogo.android.ui.mine.dialog.WeChatUntyingDialog;
import com.iyoyogo.android.utils.SpUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户安全
 */
public class UserAndSecurityActivity extends BaseActivity<UserAndSecurityContract.Presenter> implements UserAndSecurityContract.View {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.phone_iv)
    ImageView phoneIv;
    @BindView(R.id.phone_rl_id)
    RelativeLayout phoneRlId;
    @BindView(R.id.vx_iv)
    ImageView vxIv;
    @BindView(R.id.wx_rl_id)
    RelativeLayout wxRlId;
    @BindView(R.id.wb_iv)
    ImageView wbIv;
    @BindView(R.id.wb_rl_id)
    RelativeLayout wbRlId;
    @BindView(R.id.qq_iv)
    ImageView qqIv;
    @BindView(R.id.qq_rl_id)
    RelativeLayout qqRlId;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_sina)
    TextView tvSina;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    private String user_id;
    private String user_token;
    private String uid;
    private String name;
    private String iconurl;
    private int type;
    private int flag;
    private String openid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_and_security;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        user_id = SpUtils.getString(UserAndSecurityActivity.this, "user_id", null);
        user_token = SpUtils.getString(UserAndSecurityActivity.this, "user_token", null);
        openid = SpUtils.getString(UserAndSecurityActivity.this, "openid", null);
        mPresenter.getBindInfo(user_id, user_token);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

    @Override
    protected UserAndSecurityContract.Presenter createPresenter() {
        return new UserAndSecurityPresenter(this);
    }

    @OnClick({R.id.back_iv_id, R.id.phone_rl_id, R.id.wx_rl_id, R.id.wb_rl_id, R.id.qq_rl_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.phone_rl_id:
                startActivity(new Intent(UserAndSecurityActivity.this, ReplacePhoneActivity.class));
                break;
            case R.id.wx_rl_id:
                flag = 1;
                if (tvWx.getText().toString().trim().equals("已绑定")) {
                    initPopupPraise();
                } else {
                    weiXinLogin();
                }
                break;
            case R.id.wb_rl_id:
                flag = 3;
                if (tvSina.getText().toString().trim().equals("已绑定")) {
                    initPopupPraise();
                } else {
                    sinaLogin();
                }
                break;
            case R.id.qq_rl_id:
                flag = 2;
                if (tvQq.getText().toString().trim().equals("已绑定")) {
                    initPopupPraise();
                } else {
                    qqLogin();
                }
                break;
        }
    }

    private void initPopupPraise() {
        View pop_view = View.inflate(this, R.layout.dialog_untying, null);
        PopupWindow popMenu = new PopupWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView unty = pop_view.findViewById(R.id.tv_unty);
        TextView cancel = pop_view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
            }
        });
        unty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    mPresenter.userBind(user_id, user_token, 1, "");
                } else if (flag == 2) {
                    mPresenter.userBind(user_id, user_token, 2, "");
                } else if (flag == 3) {
                    mPresenter.userBind(user_id, user_token, 3, "");
                }
                popMenu.dismiss();
            }
        });
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


    public void qqLogin() {
        type = 2;
        authorization(SHARE_MEDIA.QQ, 2);

    }

    public void weiXinLogin() {
        type = 1;
        authorization(SHARE_MEDIA.WEIXIN, 1);

    }

    public void sinaLogin() {

        type = 3;
        authorization(SHARE_MEDIA.SINA, 3);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media, int type) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d("UserAndSecurityActivity", "onStart " + "授权开始");


            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d("UserAndSecurityActivity", "onComplete " + "授权完成");
                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                name = map.get("name");
                String gender = map.get("gender");
                iconurl = map.get("iconurl");
                Log.d("UserAndSecurityActivity", "uid=" + uid + "name=" + name + ",gender=" + gender + "iconurl=" + iconurl);
//                Toast.makeText(getApplicationContext(), "uid=" + uid + "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();

                SpUtils.putString(UserAndSecurityActivity.this, "openid", uid);
                SpUtils.putString(UserAndSecurityActivity.this, "nickname", name);
                SpUtils.putString(UserAndSecurityActivity.this, "logo", iconurl);
                //拿到信息去请求登录接口。。。
                mPresenter.updateBind(user_id, user_token, type, uid, name, iconurl);
               /* if (address!=null){

                    mPresenter.login(address, android.os.Build.BRAND + "_" + android.os.Build.MODEL, Build.VERSION.RELEASE, type, "", "", uid, name, iconurl);
                }else {
                    mPresenter.login("", android.os.Build.BRAND + "_" + android.os.Build.MODEL, Build.VERSION.RELEASE, type, "", "", uid, name, iconurl);
                }*/
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("UserAndSecurityActivity", "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d("UserAndSecurityActivity", "onCancel " + "授权取消");
            }
        });
    }

    @Override
    public void getBindInfoSuccess(GetBindInfoBean.DataBean data) {
        String user_phone = data.getUser_phone();
        tvPhone.setText(user_phone);
        int user_qq = data.getUser_qq();
        int user_wb = data.getUser_wb();
        int user_wx = data.getUser_wx();
        if (user_qq == 1) {
            tvQq.setText("已绑定");
        } else {
            tvQq.setText("未绑定");
        }
        if (user_wb == 1) {
            tvSina.setText("已绑定");
        } else {
            tvSina.setText("未绑定");
        }
        if (user_wx == 1) {
            tvWx.setText("已绑定");
        } else {
            tvWx.setText("未绑定");
        }

    }

    @Override
    public void updateBindSuccess(BaseBean baseBean) {
        mPresenter.getBindInfo(user_id, user_token);
    }

    @Override
    public void userBindSuccess(BaseBean baseBean) {
        int code = baseBean.getCode();
        if (code == 200) {
            Toast.makeText(this, "解绑成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);

        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
