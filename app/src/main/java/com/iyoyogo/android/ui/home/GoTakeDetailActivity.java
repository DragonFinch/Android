package com.iyoyogo.android.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.GoTakeDetailAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.contract.SameContract;
import com.iyoyogo.android.presenter.SamePresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.iyoyogo.android.utils.util.UiUtils;
import com.iyoyogo.android.view.ViewPagerLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.OnClick;

public class GoTakeDetailActivity extends BaseActivity<SamePresenter> implements SameContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.status_bar)
    View               mStatusBar;
    @BindView(R.id.iv_back)
    ImageView          mIvBack;
    @BindView(R.id.iv_share)
    ImageView          mIvShare;
    @BindView(R.id.recyclerView)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private GoTakeDetailAdapter mAdapter;

    private String userId;
    private String token;

    private String lat;
    private String lng;

    private int page = 1;

    private SameBean mData;

    private int position = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_go_take_detail;
    }

    @Override
    protected SamePresenter createPresenter() {
        return new SamePresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        userId = SpUtils.getString(this, "user_id", null);
        token = SpUtils.getString(this, "user_token", null);

        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        page = getIntent().getIntExtra("page", 1);
        mData = new Gson().fromJson(getIntent().getStringExtra("data"), SameBean.class);
        position = getIntent().getIntExtra("index", 0);
        mAdapter.setNewData(mData.getData().getList());

        mRefreshLayout.setRefreshFooter(new MyRefreshAnimFooter(this));
        mRefreshLayout.setRefreshHeader(new MyRefreshAnimHeader(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getStatusHeight(this)));
        }

        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new GoTakeDetailAdapter(R.layout.item_go_detail);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                break;
        }
    }

    /**
     *  在这里写那几个功能，其他的都处理好了
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.ll_read:

                break;
            case R.id.ll_comment:

                break;
            case R.id.ll_collect:

                break;
            case R.id.ll_like:

                break;
        }
    }

    @Override
    public void onSameList(SameBean data) {
        mData = data;
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(2000);
    }

    @Override
    public void onMoreSameList(SameBean data) {
        mData.getData().getList().addAll(data.getData().getList());
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore(2000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        mPresenter.getSameList(userId, token, lng, lat, page, "20");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.getSameList(userId, token, lng, lat, page, "20");
    }
}
