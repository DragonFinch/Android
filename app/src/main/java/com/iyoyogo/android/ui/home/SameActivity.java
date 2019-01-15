package com.iyoyogo.android.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.SameAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.contract.SameContract;
import com.iyoyogo.android.presenter.SamePresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.iyoyogo.android.utils.util.UiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SameActivity extends BaseActivity<SamePresenter> implements SameContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView       mRecyclerView;
    @BindView(R.id.status_bar)
    View               mStatusBar;
    @BindView(R.id.iv_back)
    ImageView          mIvBack;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private SameAdapter mAdapter;
    private String      userId;
    private String      token;

    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_same;
    }

    @Override
    protected SamePresenter createPresenter() {
        return new SamePresenter(this);
    }


    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
//        statusbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getStatusHeight(this)));
        }
        mAdapter = new SameAdapter(R.layout.item_same);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (i == 0) {
                    return 6;
                } else if (i > 0 && i < 3) {
                    return 3;
                } else {
                    return 2;
                }
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRefreshLayout.setRefreshFooter(new MyRefreshAnimFooter(this));
        mRefreshLayout.setRefreshHeader(new MyRefreshAnimHeader(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        userId = SpUtils.getString(this, "user_id", null);
        token = SpUtils.getString(this, "user_token", null);
        page = 1;
        mPresenter.getSameList(userId, token, "0.0", "0.0", page, "20");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        mPresenter.getSameList(userId, token, "0.0", "0.0", page, "20");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.getSameList(userId, token, "0.0", "0.0", page, "20");
    }

    @Override
    public void onSameList(SameBean data) {
        mAdapter.setNewData(data.getData().getList());
        mRefreshLayout.finishRefresh(2000);
    }

    @Override
    public void onMoreSameList(SameBean data) {
        mAdapter.getData().addAll(data.getData().getList());
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore(2000);
    }
}
