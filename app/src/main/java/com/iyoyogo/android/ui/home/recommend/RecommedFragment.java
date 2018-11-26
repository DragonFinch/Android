package com.iyoyogo.android.ui.home.recommend;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.HomeRecyclerViewAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.contract.HomeContract;
import com.iyoyogo.android.presenter.HomePresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommedFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.recycler_recommend)
    RecyclerView recyclerHome;

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        String user_id = SpUtils.getString(getContext(), "user_id", null);
        String user_token = SpUtils.getString(getContext(), "user_token", null);

        mPresenter.banner(user_id, user_token, "commend");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommed;
    }

    @Override
    protected HomeContract.Presenter createPresenter() {
        return new HomePresenter(this);
    }


    @Override
    public void bannerSuccess(HomeViewPagerBean.DataBean data) {
        List<HomeViewPagerBean.DataBean> mList = new ArrayList<>();
        mList.add(data);
        Log.d("HomeFragment", "mList.size():" + mList.size());
        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), mList);
        Log.d("HomeFragment", "mList.size():" + mList.size());
        recyclerHome.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerHome.setAdapter(homeRecyclerViewAdapter);
    }



}
