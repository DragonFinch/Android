package com.iyoyogo.android.ui.mine.draft;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.DraftAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.contract.DraftContract;
import com.iyoyogo.android.presenter.DraftPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DraftActivity extends BaseActivity<DraftContract.Presenter> implements DraftContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.recycler_draft)
    RecyclerView recyclerDraft;
    private String user_id;
    private String user_token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draft;
    }

    @Override
    protected DraftContract.Presenter createPresenter() {
        return new DraftPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("我的草稿");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(DraftActivity.this, "user_id", null);
        user_token = SpUtils.getString(DraftActivity.this, "user_token", null);
        mPresenter.getDraft(user_id, user_token, 1, 20);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getDraftSuccess(List<DraftBean.DataBean.ListBean> list) {
        recyclerDraft.setLayoutManager(new LinearLayoutManager(DraftActivity.this));
        DraftAdapter draftAdapter = new DraftAdapter(DraftActivity.this, list);
        recyclerDraft.setAdapter(draftAdapter);
    }
}
