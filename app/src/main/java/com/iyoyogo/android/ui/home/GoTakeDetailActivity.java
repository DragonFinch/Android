package com.iyoyogo.android.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.GoTakeDetailAdapter;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.contract.SameContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.SamePresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.iyoyogo.android.utils.util.UiUtils;
import com.iyoyogo.android.view.OnViewPagerListener;
import com.iyoyogo.android.view.ViewPagerLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class GoTakeDetailActivity extends BaseActivity<SamePresenter> implements SameContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, OnViewPagerListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private GoTakeDetailAdapter    mAdapter;
    private ViewPagerLayoutManager layoutManager;

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

        mRecyclerView.scrollToPosition(position);

        mRefreshLayout.setRefreshFooter(new MyRefreshAnimFooter(this));
        mRefreshLayout.setRefreshHeader(new MyRefreshAnimHeader(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        int yo_id = intent.getIntExtra("yo_id", 0);
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getStatusHeight(this)));
        }

        layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        layoutManager.setOnViewPagerListener(this);
        mRecyclerView.setLayoutManager(layoutManager);
        String user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        String user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
//        DataManager.getFromRemote().goCameraDetail(user_id, user_token, yo_id)
//                .subscribe(new Consumer<SameBean.DataBean.ListBean>() {
//                    @Override
//                    public void accept(SameBean.DataBean.ListBean listBean) throws Exception {
//                        mAdapter = new GoTakeDetailAdapter(listBean, R.layout.item_go_detail);
//
//                    }
//                });
        mAdapter=new GoTakeDetailAdapter(R.layout.item_go_detail);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
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
     * 在这里写那几个功能，其他的都处理好了
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.ll_read:

                break;
            case R.id.ll_comment:

                break;
            case R.id.ll_collect:

                break;
            case R.id.ll_like:

                break;

            case R.id.iv_go_take:
                startActivity(new Intent(this, GoTakePhotoActivity.class).putExtra("data_url", mData.getData().getList().get(position).getFile_path()));
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mData.getData().getList().get(position).getFile_type() == 2) {
            View      itemView  = layoutManager.findViewByPosition(position);
            VideoView videoView = itemView.findViewById(R.id.video_view);
            ImageView ivVideo   = itemView.findViewById(R.id.iv_video);
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
            ivVideo.setVisibility(videoView.isPlaying() ? View.GONE : View.VISIBLE);
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

    @Override
    public void onInitComplete() {
        Log.d("GoTakeDetailActivity", "111111111111111111111");
    }

    @Override
    public void onPageRelease(boolean isNext, int position) {
        if (mData.getData().getList().get(position).getFile_type() == 2) {
            stopVideo(position);
        }
    }

    @Override
    public void onPageSelected(int position, boolean isBottom) {
        this.position = position;
        if (mData.getData().getList().get(position).getFile_type() == 2) {
            Log.d("GoTakeDetailActivity", "position:" + position);
            Log.d("GoTakeDetailActivity", "3333333333333333333333333");
            startVideo(mData.getData().getList().get(position).getVideo());

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopVideo(position);
    }

    private void stopVideo(int position) {
        View      view      = layoutManager.findViewByPosition(position);
        VideoView videoView = view.findViewById(R.id.video_view);
        videoView.stopPlayback();
    }


    private void startVideo(String url) {
        View      view      = layoutManager.findViewByPosition(position);
        VideoView videoView = view.findViewById(R.id.video_view);
        videoView.setOnCompletionListener(mp -> {
            if (mp != null) {
                mp.start();
                mp.setLooping(true);
            }
        });

        HttpProxyCacheServer proxy    = App.getProxy(this);
        String               proxyUrl = proxy.getProxyUrl(url);
        Log.d("GoTakeDetailActivity", "Use proxy url " + proxyUrl + " instead of original url " + url);
        videoView.setVideoPath(proxyUrl);
        videoView.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mData.getData().getList().get(position).getFile_type() == 2) {
            startVideo(mData.getData().getList().get(position).getVideo());
        }
    }
}
