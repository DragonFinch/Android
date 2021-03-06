package com.iyoyogo.android.ui.mine.homepage;


import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.HisFansAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.HisFansBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.contract.HisHansContract;
import com.iyoyogo.android.presenter.HisFansPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * 用户主页 — 粉丝
 */
public class UserFansFragment extends BaseFragment<HisHansContract.Presenter> implements HisHansContract.View {


    @BindView(R.id.myRecyclerView)
    RecyclerView myRecyclerView;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.fans_null)
    LinearLayout fansNull;
    private String user_id;
    private String user_token;
    private String yo_user_id;
    private TextView btu_guanzhu;

    public UserFansFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_fans;
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        Intent intent = getActivity().getIntent();
        yo_user_id = intent.getStringExtra("yo_user_id");
        mPresenter.getHisHans(getActivity(),user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getHisHans(getActivity(),user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    protected HisHansContract.Presenter createPresenter() {
        return new HisFansPresenter(getActivity(),this);
    }

    @Override
    public void getHisHansSuccess(HisFansBean hisFansBean) {
        List<HisFansBean.DataBean.ListBean> list = hisFansBean.getData().getList();
        if (list.size() == 0) {
            myRecyclerView.setVisibility(View.GONE);
            fansNull.setVisibility(View.VISIBLE);
        } else {
            HisFansAdapter adapter = new HisFansAdapter(R.layout.item_addconcern_recycleview, list);
            myRecyclerView.setAdapter(adapter);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                setUserVisibleHint(true);
                    btu_guanzhu = view.findViewById(R.id.tv_guanzhu);
//                    int status = list.get(position).getStatus();
//                    if (status == 0) {//未关注
                        mPresenter.addAttention1(getActivity(),user_id, user_token, list.get(position).getUser_id());
//                        btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
//                        btu_guanzhu.setText("已关注");
//                        btu_guanzhu.setTextColor(Color.parseColor("#888888"));
//                    }
//                    if (status == 1) {//已关注
//                        mPresenter.addAttention1(user_id, user_token, list.get(position).getUser_id());
//                        btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
//                        btu_guanzhu.setText("+关注");
//                        btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
//                    }
//                    mPresenter.getHisHans(user_id, user_token, yo_user_id, 1 + "", 20 + "");
//                    adapter.notifyDataSetChanged();
                }
            });
        }
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
}
