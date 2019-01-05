package com.iyoyogo.android.ui.mine.homepage;


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

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoJiCenterAdapter;
import com.iyoyogo.android.adapter.YoXiuContentAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.contract.YoXiuContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoXiuContentPresenter;
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
 * A simple {@link Fragment} subclass.
 */

/**
 * yo秀内容
 */
public class YoXiuFragment extends BaseFragment<YoXiuContentContract.Presenter> implements YoXiuContentContract.View {

    int currentPage = 1;
    @BindView(R.id.recycler_yoxiu)
    RecyclerView recyclerYoxiu;
    Unbinder unbinder;
    @BindView(R.id.list_blank)
    LinearLayout listBlank;
    @BindView(R.id.list_blank_me)
    LinearLayout listBlankMe;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private String user_id;
    private String user_token;
    private String yo_user_id;
    MyRefreshAnimHeader mRefreshAnimHeader;
    private  List<YoXiuContentBean.DataBean.ListBean> mList;
    private YoXiuContentAdapter yoXiuContentAdapter;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yo_xiu;
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //初始化header
        mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
        setHeader(mRefreshAnimHeader);
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        //下拉刷新
        Bundle bundle = getArguments();
        yo_user_id = bundle.getString("yo_user_id");
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getYoXiuContent(user_id, user_token, yo_user_id, 1 + "", 20 + "");
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                Log.d("currentPage", "currentPage:" + currentPage);
                DataManager.getFromRemote()
                        .getYoXiuContent(user_id, user_token, yo_user_id, currentPage+"", 20 + "")
                        .subscribe(new Consumer<YoXiuContentBean>() {
                            @Override
                            public void accept(YoXiuContentBean yoXiuContentBean) throws Exception {
                                List<YoXiuContentBean.DataBean.ListBean> list = yoXiuContentBean.getData().getList();
                                mList.addAll(list);
                                yoXiuContentAdapter.notifyItemInserted(mList.size());
                            }
                        });
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                DataManager.getFromRemote().getYoXiuContent(user_id, user_token, yo_user_id,currentPage+"",20+"")
                        .subscribe(new Observer<YoXiuContentBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YoXiuContentBean yoXiuContentBean) {
                                List<YoXiuContentBean.DataBean.ListBean> list = yoXiuContentBean.getData().getList();
                                mList.addAll(list);
                                yoXiuContentAdapter = new YoXiuContentAdapter(getContext(), mList);
                                recyclerYoxiu.setAdapter(yoXiuContentAdapter);
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
    protected YoXiuContentContract.Presenter createPresenter() {
        return new YoXiuContentPresenter(this);
    }

    @Override
    public void getYoXiuContentSuccess(YoXiuContentBean.DataBean data) {
        List<YoXiuContentBean.DataBean.ListBean> list = data.getList();

        if (list.size() == 0) {
            if (yo_user_id.equals(user_id)) {
                listBlankMe.setVisibility(View.VISIBLE);
                recyclerYoxiu.setVisibility(View.GONE);
            } else {
                listBlank.setVisibility(View.VISIBLE);
                recyclerYoxiu.setVisibility(View.GONE);
                tvTips.setText("TA好像比较低调，还没有发布任何yo·秀yo～");
            }
        } else {
            listBlank.setVisibility(View.GONE);
            recyclerYoxiu.setVisibility(View.VISIBLE);
            yoXiuContentAdapter = new YoXiuContentAdapter(getContext(), list);
            recyclerYoxiu.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerYoxiu.setAdapter(yoXiuContentAdapter);
        }

    }

}
