package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoJiCenterAdapter;
import com.iyoyogo.android.adapter.YoXiuListAdapter;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.contract.YoJiContentContract;
import com.iyoyogo.android.contract.YoXiuContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;
import com.iyoyogo.android.presenter.YoJiContentPresenter;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuListActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * yo记内容
 */
public class YoJiFragment extends BaseFragment<YoJiContentContract.Presenter> implements YoJiContentContract.View {

    int currentPage = 1;
    @BindView(R.id.recycler_yoji)
    RecyclerView recyclerYoji;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.list_blank)
    LinearLayout listBlank;
    @BindView(R.id.list_blank_me)
    LinearLayout listBlankMe;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private String user_id;
    private String user_token;
    private String yo_user_id;
    MyRefreshAnimHeader mRefreshAnimHeader;
    private YoJiCenterAdapter yoJiCenterAdapter;
    private List<YoJiContentBean.DataBean.ListBean> mList;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        //初始化header
        mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
        setHeader(mRefreshAnimHeader);
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        //下拉刷新
        Bundle bundle = getArguments();
        yo_user_id = bundle.getString("yo_user_id");
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        recyclerYoji.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter.getYoJiContent(user_id,user_token,yo_user_id,currentPage,"20");
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setFooterHeight(1.0f);

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                Log.d("currentPage", "currentPage:" + currentPage);
                DataManager.getFromRemote()
                        .getYoJiContent(user_id, user_token, yo_user_id, currentPage, 20 + "")
                        .subscribe(new Consumer<YoJiContentBean>() {
                            @Override
                            public void accept(YoJiContentBean yoJiContentBean) throws Exception {
                                List<YoJiContentBean.DataBean.ListBean> list = yoJiContentBean.getData().getList();
                                mList.addAll(list);
                                yoJiCenterAdapter.notifyItemInserted(mList.size());
                            }
                        });
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                DataManager.getFromRemote().getYoJiContent(user_id, user_token, yo_user_id,currentPage,20+"")
                        .subscribe(new Observer<YoJiContentBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YoJiContentBean yoJiContentBean) {
                                List<YoJiContentBean.DataBean.ListBean> list = yoJiContentBean.getData().getList();
                                mList.addAll(list);

                                yoJiCenterAdapter = new YoJiCenterAdapter(getContext(), mList);
                                recyclerYoji.setAdapter(yoJiCenterAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("YoJiFragment", e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yo_ji;
    }

    @Override
    protected YoJiContentContract.Presenter createPresenter() {
        return new YoJiContentPresenter(this);
    }


    @Override
    public void getYoJiContentSuccess(YoJiContentBean.DataBean data) {
        List<YoJiContentBean.DataBean.ListBean> list = data.getList();
        if (list.size() == 0) {
            if (yo_user_id.equals(user_id)) {
                recyclerYoji.setVisibility(View.GONE);
                listBlankMe.setVisibility(View.VISIBLE);
            } else {
                recyclerYoji.setVisibility(View.GONE);
                listBlank.setVisibility(View.VISIBLE);
            }
        } else {
            yoJiCenterAdapter = new YoJiCenterAdapter(getContext(), list);
            recyclerYoji.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerYoji.setAdapter(yoJiCenterAdapter);
            yoJiCenterAdapter.setOnItemClickListener(new YoJiCenterAdapter.OnClickListener() {
                @Override
                public void onClick(View v, int position) {
                    int yo_id = list.get(position).getYo_id();
                    Intent intent = new Intent(getContext(), YoJiDetailActivity.class);
                    intent.putExtra("yo_id", yo_id);
                    startActivity(intent);
                }
            });
        }
    }
}
