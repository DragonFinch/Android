package com.iyoyogo.android.ui.home.yoxiu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoXiuListAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.contract.YoXiuListContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
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

/*
yo秀列表
 */
public class YoXiuListActivity extends BaseActivity {
    int currentPage = 1;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.share_img)
    ImageView shareImg;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.recycler_yoxiu_list)
    RecyclerView recyclerYoxiuList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private YoXiuListAdapter yoXiuListAdapter;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private String user_id;
    private String user_token;
    private List<YouXiuListBean.DataBean.ListBean> mList;

    MyRefreshAnimHeader mRefreshAnimHeader;
    private MyRefreshAnimFooter mRefreshAnimFooter;
    private String position;
    private String type;
    private String mCity;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);

    }

    private void setFooter(RefreshFooter footer) {

        refreshLayout.setRefreshFooter(footer);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        super.initView();
//        statusbar();
        mLayoutManager = new LinearLayoutManager(YoXiuListActivity.this);
        shareImg.setVisibility(View.GONE);
//        statusbar();
        StatusBarUtils.setWindowStatusBarColor(YoXiuListActivity.this, R.color.orange);
        mList = new ArrayList<>();
        //初始化header
        mRefreshAnimHeader = new MyRefreshAnimHeader(this);
        mRefreshAnimFooter = new MyRefreshAnimFooter(this);
        setHeader(mRefreshAnimHeader);
//        setFooter(mRefreshAnimFooter);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_xiu_list;
    }

    @Override
    protected YoXiuListContract.Presenter createPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mCity = SpUtils.getString(YoXiuListActivity.this, "city", "");
        refreshLayout.setRefreshFooter(new MyRefreshAnimFooter(this));
        //下拉刷新
        user_id = SpUtils.getString(YoXiuListActivity.this, "user_id", null);
        user_token = SpUtils.getString(YoXiuListActivity.this, "user_token", null);
        recyclerYoxiuList.setLayoutManager(new LinearLayoutManager(YoXiuListActivity.this));
        refreshLayout.setEnableRefresh(true);
//        refreshLayout.setFooterMaxDragRate()
//        refreshLayout.setFooterHeight(1.0f);
//        refreshLayout.setFooterHeight(1.0f);
//        refreshLayout.setEnableFooterFollowWhenLoadFinished(false);
        Intent intent = getIntent();
        position = intent.getStringExtra("position");
        type = intent.getStringExtra("type");
        Log.e("type1212", "initData: "+type );
        if (position.equals("")) {
            if (type.equals("attention")) {
                DataManager.getFromRemote().getYoXiuAttentionList(YoXiuListActivity.this,user_id, user_token, currentPage,mCity)
                        .subscribe(new Observer<YouXiuListBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YouXiuListBean youXiuListBean) {
                                List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                mList.addAll(list);

                                yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this, mList, type);
                                recyclerYoxiuList.setAdapter(yoXiuListAdapter);
                                yoXiuListAdapter.setOnItemClickListener(new YoXiuListAdapter.OnClickListener() {
                                    @Override
                                    public void setOnClickListener(View v, int position) {
                                        int id = mList.get(position).getId();
                                        Intent intent = new Intent(YoXiuListActivity.this, NewYoXiuDetailActivity.class);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("YoXiuListActivity", e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoXiuAttentionList(YoXiuListActivity.this,user_id, user_token, currentPage,mCity)
                                .subscribe(new Consumer<YouXiuListBean>() {
                                    @Override
                                    public void accept(YouXiuListBean youXiuListBean) throws Exception {
                                        List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                        mList.addAll(list);
                                        yoXiuListAdapter.notifyItemInserted(mList.size());
                                    }
                                });

//                yoXiuListAdapter.notifyItemInserted(mList.size());
                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        mList.clear();
                        DataManager.getFromRemote().getYoXiuAttentionList(YoXiuListActivity.this,user_id, user_token, 1,mCity)
                                .subscribe(new Observer<YouXiuListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YouXiuListBean youXiuListBean) {
                                        List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                        mList.addAll(list);

                                        yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this, mList, type);
                                        recyclerYoxiuList.setAdapter(yoXiuListAdapter);

                                        yoXiuListAdapter.setOnItemClickListener(new YoXiuListAdapter.OnClickListener() {
                                            @Override
                                            public void setOnClickListener(View v, int position) {
                                                int id = mList.get(position).getId();
                                                Intent intent = new Intent(YoXiuListActivity.this, NewYoXiuDetailActivity.class);
                                                intent.putExtra("id", id);
                                                startActivity(intent);
                                            }
                                        });
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
            } else {
                DataManager.getFromRemote().getYoXiuList(YoXiuListActivity.this,user_id, user_token, currentPage,mCity)
                        .subscribe(new Observer<YouXiuListBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YouXiuListBean youXiuListBean) {
                                List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                mList.addAll(list);

                                yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this, mList, type);
                                recyclerYoxiuList.setAdapter(yoXiuListAdapter);
                                yoXiuListAdapter.setOnItemClickListener(new YoXiuListAdapter.OnClickListener() {
                                    @Override
                                    public void setOnClickListener(View v, int position) {
                                        int id = mList.get(position).getId();
                                        Intent intent = new Intent(YoXiuListActivity.this, NewYoXiuDetailActivity.class);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("YoXiuListActivity", e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        currentPage++;
                        Log.d("currentPage", "currentPage:" + currentPage);
                        DataManager.getFromRemote().getYoXiuList(YoXiuListActivity.this,user_id, user_token, currentPage,mCity)
                                .subscribe(new Consumer<YouXiuListBean>() {
                                    @Override
                                    public void accept(YouXiuListBean youXiuListBean) throws Exception {
                                        List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                        mList.addAll(list);
                                        yoXiuListAdapter.notifyItemInserted(mList.size());
                                    }
                                });

//                yoXiuListAdapter.notifyItemInserted(mList.size());
                        refreshLayout.finishLoadMore(2000);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        mList.clear();
                        DataManager.getFromRemote().getYoXiuList(YoXiuListActivity.this,user_id, user_token, 1,mCity)
                                .subscribe(new Observer<YouXiuListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(YouXiuListBean youXiuListBean) {
                                        List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                        mList.addAll(list);

                                        yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this, mList, type);
                                        recyclerYoxiuList.setAdapter(yoXiuListAdapter);

                                        yoXiuListAdapter.setOnItemClickListener(new YoXiuListAdapter.OnClickListener() {
                                            @Override
                                            public void setOnClickListener(View v, int position) {
                                                int id = mList.get(position).getId();
                                                Intent intent = new Intent(YoXiuListActivity.this, NewYoXiuDetailActivity.class);
                                                intent.putExtra("id", id);
                                                startActivity(intent);
                                            }
                                        });
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
            DataManager.getFromRemote().getYoXiuPosition(YoXiuListActivity.this,user_id, user_token, position, 1, currentPage, "20")
                    .subscribe(new Observer<YouXiuListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(YouXiuListBean youXiuListBean) {
                            List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                            mList.addAll(list);

                            yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this, mList, type);
                            recyclerYoxiuList.setAdapter(yoXiuListAdapter);
                            yoXiuListAdapter.setOnItemClickListener(new YoXiuListAdapter.OnClickListener() {
                                @Override
                                public void setOnClickListener(View v, int position) {
                                    int id = mList.get(position).getId();
                                    Intent intent = new Intent(YoXiuListActivity.this, NewYoXiuDetailActivity.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("YoXiuListActivity", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    currentPage++;
                    Log.d("currentPage", "currentPage:" + currentPage);
                    DataManager.getFromRemote().getYoXiuPosition(YoXiuListActivity.this,user_id, user_token, position, 1, currentPage, "20")
                            .subscribe(new Consumer<YouXiuListBean>() {
                                @Override
                                public void accept(YouXiuListBean youXiuListBean) throws Exception {
                                    List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                    mList.addAll(list);
                                    yoXiuListAdapter.notifyItemInserted(mList.size());
                                }
                            });

//                yoXiuListAdapter.notifyItemInserted(mList.size());
                    refreshLayout.finishLoadMore(2000);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mList.clear();
                    DataManager.getFromRemote().getYoXiuPosition(YoXiuListActivity.this,user_id, user_token, position, 1, 1, "")
                            .subscribe(new Observer<YouXiuListBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(YouXiuListBean youXiuListBean) {
                                    List<YouXiuListBean.DataBean.ListBean> list = youXiuListBean.getData().getList();
                                    mList.addAll(list);

                                    yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this, mList, type);
                                    recyclerYoxiuList.setAdapter(yoXiuListAdapter);

                                    yoXiuListAdapter.setOnItemClickListener(new YoXiuListAdapter.OnClickListener() {
                                        @Override
                                        public void setOnClickListener(View v, int position) {
                                            int id = mList.get(position).getId();
                                            Intent intent = new Intent(YoXiuListActivity.this, NewYoXiuDetailActivity.class);
                                            intent.putExtra("id", id);
                                            startActivity(intent);
                                        }
                                    });
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
    protected void onResume() {
        super.onResume();


    }

    @OnClick({R.id.back, R.id.share_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share_img:

                break;
        }
    }
}
