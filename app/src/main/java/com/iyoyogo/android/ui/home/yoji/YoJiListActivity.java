package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.YoJiListHorizontalAdapter;
import com.iyoyogo.android.adapter.YoJiListAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * yo记列表
 */
public class YoJiListActivity extends BaseActivity {

    int currentPage = 1;
    @BindView(R.id.recycler_yoji_list_two)
    RecyclerView recyclerYojiListTwo;
    @BindView(R.id.refresh_layout1)
    SmartRefreshLayout refreshLayout1;
    @BindView(R.id.refresh_layout2)
    SmartRefreshLayout refreshLayout2;
    private int num = 1;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.img_replace)
    ImageView imgReplace;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.recycler_yoji_list)
    RecyclerView recyclerYojiList;
    private YoJiListAdapter yoJiListAdapter;
    boolean isVertical;
    private YoJiListHorizontalAdapter yoJiListHorizontalAdapter;
    MyRefreshAnimHeader mRefreshAnimHeader2;
    MyRefreshAnimHeader mRefreshAnimHeader1;
    private List<YoJiListBean.DataBean.ListBean> mList;
    private List<YoJiListBean.DataBean.ListBean> list;
    private String user_id;
    private String user_token;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private boolean flag = false;
    private String type;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader1(RefreshHeader header) {
        refreshLayout1.setRefreshHeader(header);
    }

    private void setHeader2(RefreshHeader header) {
        refreshLayout2.setRefreshHeader(header);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mRefreshAnimHeader1 = new MyRefreshAnimHeader(this);
        setHeader1(mRefreshAnimHeader1);
        refreshLayout1.setRefreshFooter(new MyRefreshAnimFooter(this));

        refreshLayout2.setRefreshFooter(new MyRefreshAnimFooter(this));
        mRefreshAnimHeader2 = new MyRefreshAnimHeader(this);
        setHeader2(mRefreshAnimHeader2);

        //下拉刷新
        recyclerYojiList.setLayoutManager(linearLayoutManager);
//        refreshLayout1.setEnableRefresh(true);

        //流式布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        type = intent.getStringExtra("type");
        String sign = intent.getStringExtra("sign");
        if (position.equals("")) {

            if (!sign.equals("")) {
                imgReplace.setVisibility(View.GONE);
                tvMessage.setText(sign);
                recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                refreshLayout2.setEnableRefresh(true);

                user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
                user_token = SpUtils.getString(getApplicationContext(), "user_token", null);

                DataManager.getFromRemote()
                        .getYoJiLabel(YoJiListActivity.this,user_id, user_token, sign, 1, "20")
                        .subscribe(new Observer<YoJiListBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YoJiListBean yoJiListBean) {
                                list = yoJiListBean.getData().getList();
                                mList.addAll(list);
                                //横向
                                Log.e("123", "recyclerYojiList" + "isVertical:" + isVertical);
                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList,type);
                                staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                yoJiListAdapter.setOnItemClickListener(new YoJiListAdapter.OnClickListener() {
                                    @Override
                                    public void setOnClickListener(View v, int position) {
                                        int id = mList.get(position).getYo_id();
                                        Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", id);
                                        startActivity(intent);
                                    }
                                });


                                yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                linearLayoutManager = new LinearLayoutManager(YoJiListActivity.this);
                                recyclerYojiList.setLayoutManager(linearLayoutManager);
                                recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                yoJiListHorizontalAdapter.setOnItemClickListener(new YoJiListHorizontalAdapter.OnClickListener() {
                                    @Override
                                    public void onClick(View v, int position) {
                                        int id = mList.get(position).getYo_id();
                                        Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", id);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @Override
                            public void onError(Throwable e) {
                                shortToast(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                refreshLayout1.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoJiLabel(YoJiListActivity.this,user_id, user_token, sign, 1, "20")
                                .subscribe(new Consumer<YoJiListBean>() {
                                    @Override
                                    public void accept(YoJiListBean yoJiListBean) throws Exception {
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        Log.d("YoJiListActivity", mList.size() + "");
                                        Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                        yoJiListHorizontalAdapter.notifyItemInserted(mList.size());
                                    }
                                });

                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        DataManager.getFromRemote().getYoJiLabel(YoJiListActivity.this,user_id, user_token, sign, 1, "20")
                                .subscribe(new Observer<YoJiListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YoJiListBean yoJiListBean) {
                                        mList.clear();
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                        recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("YoJiListActivity", e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        refreshLayout.finishRefresh();
                    }
                });

                refreshLayout2.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoJiLabel(YoJiListActivity.this,user_id, user_token, sign, 1, "20")
                                .subscribe(new Consumer<YoJiListBean>() {
                                    @Override
                                    public void accept(YoJiListBean yoJiListBean) throws Exception {
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        Log.d("YoJiListActivity", mList.size() + "");
                                        Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                        yoJiListAdapter.notifyItemInserted(mList.size());
                                        yoJiListHorizontalAdapter.notifyItemChanged(mList.size());
                                    }
                                });

                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        DataManager.getFromRemote().getYoJiLabel(YoJiListActivity.this,user_id, user_token, sign, 1, "20")
                                .subscribe(new Observer<YoJiListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YoJiListBean yoJiListBean) {
                                        mList.clear();
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
//                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList);
//                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                        yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                                        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                        recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                        recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("YoXiuListActivity", e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        refreshLayout.finishRefresh();
                    }
                });
            }
            if (type.equals("attention")){
                recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                refreshLayout2.setEnableRefresh(true);

                user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
                user_token = SpUtils.getString(getApplicationContext(), "user_token", null);

                DataManager.getFromRemote()
                        .getYoJiAttentionList(YoJiListActivity.this,user_id, user_token, 1, 20)
                        .subscribe(new Observer<YoJiListBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YoJiListBean yoJiListBean) {
                                list = yoJiListBean.getData().getList();
                                mList.addAll(list);
                                //横向
                                Log.e("123", "recyclerYojiList" + "isVertical:" + isVertical);
                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                                staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                yoJiListAdapter.setOnItemClickListener(new YoJiListAdapter.OnClickListener() {
                                    @Override
                                    public void setOnClickListener(View v, int position) {
                                        int id = mList.get(position).getYo_id();
                                        Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", id);
                                        startActivity(intent);
                                    }
                                });

                                yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                linearLayoutManager = new LinearLayoutManager(YoJiListActivity.this);
                                recyclerYojiList.setLayoutManager(linearLayoutManager);
                                recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                yoJiListHorizontalAdapter.setOnItemClickListener(new YoJiListHorizontalAdapter.OnClickListener() {
                                    @Override
                                    public void onClick(View v, int position) {
                                        int id = mList.get(position).getYo_id();
                                        Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", id);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @Override
                            public void onError(Throwable e) {
                                shortToast(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                refreshLayout1.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoJiAttentionList(YoJiListActivity.this,user_id, user_token, currentPage, 20)
                                .subscribe(new Consumer<YoJiListBean>() {
                                    @Override
                                    public void accept(YoJiListBean yoJiListBean) throws Exception {
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        Log.d("YoJiListActivity", mList.size() + "");
                                        Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                        yoJiListHorizontalAdapter.notifyItemInserted(mList.size());
                                    }
                                });

                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        DataManager.getFromRemote().getYoJiAttentionList(YoJiListActivity.this,user_id, user_token, 1, 20)
                                .subscribe(new Observer<YoJiListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YoJiListBean yoJiListBean) {
                                        mList.clear();
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                        recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("YoJiListActivity", e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        refreshLayout.finishRefresh();
                    }
                });

                refreshLayout2.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoJiAttentionList(YoJiListActivity.this,user_id, user_token, currentPage, 20)
                                .subscribe(new Consumer<YoJiListBean>() {
                                    @Override
                                    public void accept(YoJiListBean yoJiListBean) throws Exception {
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        Log.d("YoJiListActivity", mList.size() + "");
                                        Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                        yoJiListAdapter.notifyItemInserted(mList.size());
                                        yoJiListHorizontalAdapter.notifyItemChanged(mList.size());
                                    }
                                });

                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, 1, 20)
                                .subscribe(new Observer<YoJiListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YoJiListBean yoJiListBean) {
                                        mList.clear();
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
//                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList);
//                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                        yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                                        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                        recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                        recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("YoXiuListActivity", e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        refreshLayout.finishRefresh();
                    }
                });
            }else {
                recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                refreshLayout2.setEnableRefresh(true);

                user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
                user_token = SpUtils.getString(getApplicationContext(), "user_token", null);

                DataManager.getFromRemote()
                        .getYoJiList(YoJiListActivity.this,user_id, user_token, 1, 20)
                        .subscribe(new Observer<YoJiListBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YoJiListBean yoJiListBean) {
                                list = yoJiListBean.getData().getList();
                                mList.addAll(list);
                                //横向
                                Log.e("123", "recyclerYojiList" + "isVertical:" + isVertical);
                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                                staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                yoJiListAdapter.setOnItemClickListener(new YoJiListAdapter.OnClickListener() {
                                    @Override
                                    public void setOnClickListener(View v, int position) {
                                        int id = mList.get(position).getYo_id();
                                        Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", id);
                                        startActivity(intent);
                                    }
                                });

                                yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                linearLayoutManager = new LinearLayoutManager(YoJiListActivity.this);
                                recyclerYojiList.setLayoutManager(linearLayoutManager);
                                recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                yoJiListHorizontalAdapter.setOnItemClickListener(new YoJiListHorizontalAdapter.OnClickListener() {
                                    @Override
                                    public void onClick(View v, int position) {
                                        int id = mList.get(position).getYo_id();
                                        Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", id);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @Override
                            public void onError(Throwable e) {
                                shortToast(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                refreshLayout1.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, currentPage, 20)
                                .subscribe(new Consumer<YoJiListBean>() {
                                    @Override
                                    public void accept(YoJiListBean yoJiListBean) throws Exception {
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        Log.d("YoJiListActivity", mList.size() + "");
                                        Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                        yoJiListHorizontalAdapter.notifyItemInserted(mList.size());
                                    }
                                });

                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, 1, 20)
                                .subscribe(new Observer<YoJiListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YoJiListBean yoJiListBean) {
                                        mList.clear();
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                        recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("YoJiListActivity", e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        refreshLayout.finishRefresh();
                    }
                });

                refreshLayout2.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, currentPage, 20)
                                .subscribe(new Consumer<YoJiListBean>() {
                                    @Override
                                    public void accept(YoJiListBean yoJiListBean) throws Exception {
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
                                        Log.d("YoJiListActivity", mList.size() + "");
                                        Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                        yoJiListAdapter.notifyItemInserted(mList.size());
                                        yoJiListHorizontalAdapter.notifyItemChanged(mList.size());
                                    }
                                });

                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, 1, 20)
                                .subscribe(new Observer<YoJiListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YoJiListBean yoJiListBean) {
                                        mList.clear();
                                        List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                        mList.addAll(list);
//                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList);
//                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                        yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                                        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                        recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                        recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("YoXiuListActivity", e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        refreshLayout.finishRefresh();
                    }
                });
            }
        } else {
            tvMessage.setText(position);
            recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
            refreshLayout2.setEnableRefresh(true);

            user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
            user_token = SpUtils.getString(getApplicationContext(), "user_token", null);

            DataManager.getFromRemote()
                    .getYoJiPosition(YoJiListActivity.this,user_id, user_token, position, 2, 1, "20")
                    .subscribe(new Observer<YoJiListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(YoJiListBean yoJiListBean) {
                            list = yoJiListBean.getData().getList();
                            mList.addAll(list);
                            //横向
                            Log.e("123", "recyclerYojiList" + "isVertical:" + isVertical);
                            yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                            recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                            yoJiListAdapter.setOnItemClickListener(new YoJiListAdapter.OnClickListener() {
                                @Override
                                public void setOnClickListener(View v, int position) {
                                    int id = mList.get(position).getYo_id();
                                    Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                    intent.putExtra("yo_id", id);
                                    startActivity(intent);
                                }
                            });

                            yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                            linearLayoutManager = new LinearLayoutManager(YoJiListActivity.this);
                            recyclerYojiList.setLayoutManager(linearLayoutManager);
                            recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                            yoJiListHorizontalAdapter.setOnItemClickListener(new YoJiListHorizontalAdapter.OnClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    int id = mList.get(position).getYo_id();
                                    Intent intent = new Intent(YoJiListActivity.this, YoJiDetailActivity.class);
                                    intent.putExtra("yo_id", id);
                                    startActivity(intent);
                                }
                            });

                        }

                        @Override
                        public void onError(Throwable e) {
                            shortToast(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            refreshLayout1.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    currentPage++;
                    Log.d("currentPage", "currentPage:" + currentPage);
                    DataManager.getFromRemote().getYoJiPosition(YoJiListActivity.this,user_id, user_token, position, 2, 1, "20")
                            .subscribe(new Consumer<YoJiListBean>() {
                                @Override
                                public void accept(YoJiListBean yoJiListBean) throws Exception {
                                    List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                    mList.addAll(list);
                                    Log.d("YoJiListActivity", mList.size() + "");
                                    Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                    yoJiListHorizontalAdapter.notifyItemInserted(mList.size());
                                }
                            });

                    refreshLayout.finishLoadMore(2000);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, 1, 20)
                            .subscribe(new Observer<YoJiListBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(YoJiListBean yoJiListBean) {
                                    mList.clear();
                                    List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                    mList.addAll(list);
                                    yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList, type);
                                    recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("YoJiListActivity", e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                    refreshLayout.finishRefresh();
                }
            });

            refreshLayout2.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    currentPage++;
                    Log.d("currentPage", "currentPage:" + currentPage);
                    DataManager.getFromRemote().getYoJiPosition(YoJiListActivity.this,user_id, user_token, position, 2, 1, "20")
                            .subscribe(new Consumer<YoJiListBean>() {
                                @Override
                                public void accept(YoJiListBean yoJiListBean) throws Exception {
                                    List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                    mList.addAll(list);
                                    Log.d("YoJiListActivity", mList.size() + "");
                                    Log.d("YoJiListActivity", "recyclerYojiListTwo.getVisibility():" + recyclerYojiListTwo.getVisibility());
                                    yoJiListAdapter.notifyItemInserted(mList.size());
                                    yoJiListHorizontalAdapter.notifyItemChanged(mList.size());
                                }
                            });

                    refreshLayout.finishLoadMore(2000);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    DataManager.getFromRemote().getYoJiList(YoJiListActivity.this,user_id, user_token, 1, 20)
                            .subscribe(new Observer<YoJiListBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(YoJiListBean yoJiListBean) {
                                    mList.clear();
                                    List<YoJiListBean.DataBean.ListBean> list = yoJiListBean.getData().getList();
                                    mList.addAll(list);
//                                yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList);
//                                recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                    yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList, type);
                                    staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                    recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                                    recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("YoXiuListActivity", e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                    refreshLayout.finishRefresh();
                }
            });
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initView() {
        super.initView();
        mList = new ArrayList<>();
//        statusbar();
        StatusBarUtils.setWindowStatusBarColor(YoJiListActivity.this, R.color.orange);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    public void initPraises() {
       /* LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < imageData.size(); i++) {
            CircleImageView imageView = (CircleImageView) inflater.inflate(R.layout.item_head_image, pileLayout, false);
            Glide.with(this).load(imageData.get(i)).into(imageView);
            imageView.setId(i);
            // 为item绑定数据
            imageView.setTag(imageData.get(i));
            // 为item设置点击事件
            imageView.setOnClickListener(this);
            pileLayout.addView(imageView);*/


    }

    @OnClick({R.id.back, R.id.img_replace})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.img_replace:
//                num++;
//                if (num % 2 == 0) {
//                    imgReplace.setImageResource(R.mipmap.view2);
//                    refreshLayout1.setVisibility(View.GONE);
//                    refreshLayout2.setVisibility(View.VISIBLE);
////                    recyclerYojiList.setVisibility(View.GONE);
////                    recyclerYojiListTwo.setVisibility(View.VISIBLE);
//                } else if (num % 2 == 1) {
//                    imgReplace.setImageResource(R.mipmap.view1);
//                    refreshLayout1.setVisibility(View.VISIBLE);
//                    refreshLayout2.setVisibility(View.GONE);
////                    recyclerYojiList.setVisibility(View.VISIBLE);
////                    recyclerYojiListTwo.setVisibility(View.GONE);
//                }
                if (flag == false) {
                    flag = true;
                    imgReplace.setImageResource(R.mipmap.view1);
                    refreshLayout1.setVisibility(View.GONE);
                    refreshLayout2.setVisibility(View.VISIBLE);
//                    recyclerYojiList.setVisibility(View.GONE);
//                    recyclerYojiListTwo.setVisibility(View.VISIBLE);
                } else {
                    flag = false;
                    imgReplace.setImageResource(R.mipmap.view2);
                    refreshLayout1.setVisibility(View.VISIBLE);
                    refreshLayout2.setVisibility(View.GONE);
//                    recyclerYojiList.setVisibility(View.VISIBLE);
//                    recyclerYojiListTwo.setVisibility(View.GONE);
                }
                break;
        }
    }
}
