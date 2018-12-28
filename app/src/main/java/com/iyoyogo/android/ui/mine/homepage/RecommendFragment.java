package com.iyoyogo.android.ui.mine.homepage;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CommentAttentionAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.contract.CommendAttentionContract;
import com.iyoyogo.android.presenter.CommendAttentionPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * 个人主页 — 推荐
 */
public class RecommendFragment extends BaseFragment<CommendAttentionContract.Presenter> implements CommendAttentionContract.View {

    @BindView(R.id.myRecyclerView)
    RecyclerView myRecyclerView;
    private String user_id;
    private String user_token;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getCommendAttention(user_id, user_token);
    }

    @Override
    protected void initView() {
        super.initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getActivity().getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getActivity().getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCommendAttention(user_id, user_token);
    }

    @Override
    protected CommendAttentionContract.Presenter createPresenter() {
        return new CommendAttentionPresenter(this);
    }

    @Override
    public void getCommendAttentionSuccess(CommendAttentionBean commendAttentionBean) {
        List<CommendAttentionBean.DataBean.ListBean> list = commendAttentionBean.getData().getList();
        CommentAttentionAdapter adapter = new CommentAttentionAdapter(R.layout.item_recommend_recycleview, list);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
