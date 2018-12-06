package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.contract.UserAndSecurityContract;
import com.iyoyogo.android.presenter.UserAndSecurityPresenter;
import com.iyoyogo.android.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_and_security;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(UserAndSecurityActivity.this, "user_id", null);
        user_token = SpUtils.getString(UserAndSecurityActivity.this, "user_token", null);
        mPresenter.getBindInfo(user_id, user_token);
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

                break;
            case R.id.wb_rl_id:

                break;
            case R.id.qq_rl_id:

                break;
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
