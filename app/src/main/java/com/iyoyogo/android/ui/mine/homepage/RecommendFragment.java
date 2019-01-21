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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CommentAttentionAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.contract.CommendAttentionContract;
import com.iyoyogo.android.presenter.CommendAttentionPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

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
//    @BindView(R.id.refresh_layout)
//    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private String user_id;
    private String user_token;
    private boolean falg = false;
    private TextView btu_guanzhu;
    //    MyRefreshAnimHeader mRefreshAnimHeader;

    /**
     * 设置刷新header风格
     *
     * @param
     */
//    private void setHeader(RefreshHeader header) {
//        refreshLayout.setRefreshHeader(header);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getCommendAttention(getActivity(),user_id, user_token);
        //初始化header
//        mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
//        setHeader(mRefreshAnimHeader);
//        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCommendAttention(getActivity(),user_id, user_token);
    }

    @Override
    protected CommendAttentionContract.Presenter createPresenter() {
        return new CommendAttentionPresenter(getActivity(),this);
    }

    @Override
    public void getCommendAttentionSuccess(CommendAttentionBean commendAttentionBean) {
        List<CommendAttentionBean.DataBean.ListBean> list = commendAttentionBean.getData().getList();
        CommentAttentionAdapter adapter = new CommentAttentionAdapter(R.layout.item_recommend_recycleview, list);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                btu_guanzhu = view.findViewById(R.id.btu_guanzhu);
                mPresenter.addAttention1(getActivity(),user_id, user_token, list.get(position).getUser_id());
            }
        });

    }

    @Override
    public void addAttentionSuccess(AttentionBean attentionBean) {
        int status = attentionBean.getData().getStatus();
        if (status == 1) {
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("已关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888888"));
        }else {
            btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
            btu_guanzhu.setText("+关注");
            btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void deleteAttention(AttentionBean attentionBean) {

    }

}
