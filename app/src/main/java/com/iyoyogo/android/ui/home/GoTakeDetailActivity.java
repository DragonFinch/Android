package com.iyoyogo.android.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.danikula.videocache.HttpProxyCacheServer;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.GoTakeDetailAdapter;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.contract.SameContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.SamePresenter;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.iyoyogo.android.utils.util.UiUtils;
import com.iyoyogo.android.view.OnViewPagerListener;
import com.iyoyogo.android.view.ViewPagerLayoutManager;
import com.iyoyogo.android.widget.AddCollectPopup;
import com.iyoyogo.android.widget.CommonPopup;
import com.iyoyogo.android.widget.SharePopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class GoTakeDetailActivity extends BaseActivity<SamePresenter> implements SameContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, OnViewPagerListener, BaseQuickAdapter.OnItemClickListener, AddCollectPopup.AddCollectListener, CommonPopup.OnCommonClick {

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

    private GoTakeDetailAdapter    mAdapter;
    private ViewPagerLayoutManager layoutManager;

    private String userId;
    private String token;

    private String lat;
    private String lng;

    private int page = 1;

    private SameBean mData;

    private int position = 0;

    private String user_id;
    private String user_token;

    private AddCollectPopup mAddCollectPopup;
    private CommonPopup     mCommonPopup;
    private SharePopup      mSharePopup;

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
        int    yo_id  = intent.getIntExtra("yo_id", 0);
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getStatusHeight(this)));
        }

        layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        layoutManager.setOnViewPagerListener(this);
        mRecyclerView.setLayoutManager(layoutManager);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
//        DataManager.getFromRemote().goCameraDetail(user_id, user_token, yo_id)
//                .subscribe(new Consumer<SameBean.DataBean.ListBean>() {
//                    @Override
//                    public void accept(SameBean.DataBean.ListBean listBean) throws Exception {
//                        mAdapter = new GoTakeDetailAdapter(listBean, R.layout.item_go_detail);
//
//                    }
//                });
        mAdapter = new GoTakeDetailAdapter(R.layout.item_go_detail);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mAddCollectPopup = new AddCollectPopup(this);
        mAddCollectPopup.setAddCollectListener(this);

        mCommonPopup = new CommonPopup(this);
        mCommonPopup.setOnCommonClick(this);

        mSharePopup = new SharePopup(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                SameBean.DataBean.ListBean listBean = mData.getData().getList().get(position);
                mSharePopup.setTitle(listBean.getFile_desc())
                        .setDesc(listBean.getFile_desc())
                        .setImage(listBean.getFile_path())
                        .setYoId(listBean.getYo_id())
                        .showPopupWindow();
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
                Intent intent = new Intent(this, AllCommentActivity.class);
                intent.putExtra("id", mData.getData().getList().get(position).getYo_id());
                startActivity(intent);
                break;
            case R.id.ll_collect:
                if (mData.getData().getList().get(position).getIs_my_collect() == 0) {
                    mAddCollectPopup.setYoId(mData.getData().getList().get(position).getYo_id());
                    mAddCollectPopup.showPopupWindow();
                } else {
                    mCommonPopup.showPopupWindow();
                }
                break;
            case R.id.ll_like:
                DataManager.getFromRemote().praise(user_id, user_token, mData.getData().getList().get(position).getYo_id(), 0).subscribe(baseBean -> {
                    View      contentView = layoutManager.findViewByPosition(position);
                    ImageView ivLike      = contentView.findViewById(R.id.iv_like);
                    TextView  tvLike      = contentView.findViewById(R.id.tv_like_num);
                    ivLike.setImageResource(baseBean.getData().getStatus() == 1 ? R.mipmap.yixihuan : R.mipmap.xihuan_bai);
                    mData.getData().getList().get(position).setIs_my_praise(baseBean.getData().getStatus());
                    mData.getData().getList().get(position).setCount_praise(mData.getData().getList().get(position).getCount_praise() + (baseBean.getData().getStatus() == 0 ? -1 : 1));
                    tvLike.setText(mData.getData().getList().get(position).getCount_praise() + "");
                });
                break;

            case R.id.iv_go_take:
                startActivity(new Intent(this, GoTakePhotoActivity.class).putExtra("data_url", mData.getData().getList().get(position).getFile_path()));
                break;

            case R.id.iv_user_pic:
            case R.id.tv_user_name:
                startActivity(new Intent(this, Personal_homepage_Activity.class)
                        .putExtra("yo_user_id", mData.getData().getList().get(position).getUser_id()));
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
    public void onCommonClick(View v) {
        if (v.getId() == R.id.tv_done) {
            DataManager.getFromRemote().delCollection(user_id, token, mData.getData().getList().get(position).getYo_id()).subscribe(new Consumer<BaseBean>() {
                @Override
                public void accept(BaseBean baseBean) throws Exception {
                    View      itemView  = layoutManager.findViewByPosition(position);
                    ImageView ivCollect = itemView.findViewById(R.id.iv_collect);
                    TextView  tvCollect = itemView.findViewById(R.id.tv_collect_num);
                    ivCollect.setImageResource(R.mipmap.shoucang_bai);
                    mData.getData().getList().get(position).setIs_my_collect(0);
                    mData.getData().getList().get(position).setCount_collect(mData.getData().getList().get(position).getCount_collect() - 1);
                    tvCollect.setText(mData.getData().getList().get(position).getCount_collect() + "");
                }
            });
        }
    }

    @Override
    public void onAddCollectSuccess(String id) {
        View      itemView  = layoutManager.findViewByPosition(position);
        ImageView ivCollect = itemView.findViewById(R.id.iv_collect);
        TextView  tvCollect = itemView.findViewById(R.id.tv_collect_num);
        ivCollect.setImageResource(R.mipmap.yishoucang);
        mData.getData().getList().get(position).setIs_my_collect(1);
        mData.getData().getList().get(position).setCount_collect(mData.getData().getList().get(position).getCount_collect() + 1);
        tvCollect.setText(mData.getData().getList().get(position).getCount_collect() + "");
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
