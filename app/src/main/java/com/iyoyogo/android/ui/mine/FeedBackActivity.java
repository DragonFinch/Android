package com.iyoyogo.android.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.FeedBackContract;
import com.iyoyogo.android.presenter.FeedBackPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户反馈
 */
public class FeedBackActivity extends BaseActivity<FeedBackContract.Presenter> implements FeedBackContract.View {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.edit_feed_back)
    EditText editFeedBack;
    @BindView(R.id.logo_iv_id)
    ImageView logoIvId;
    private String user_id;
    private String user_token;

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setWindowStatusBarColor(FeedBackActivity.this, R.color.white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected FeedBackContract.Presenter createPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
    }

    @OnClick({R.id.back_iv_id, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.tv_complete:
                mPresenter.addFeedBack(user_id, user_token, editFeedBack.getText().toString().trim());
                break;
        }
    }

    @Override
    public void addFeedBackSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 200) {
            editFeedBack.setText("");
            Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
        }
//        finish();
    }
}
