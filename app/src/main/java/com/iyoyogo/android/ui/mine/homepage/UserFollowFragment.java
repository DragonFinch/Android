package com.iyoyogo.android.ui.mine.homepage;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AttentionsAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AttentionsBean;
import com.iyoyogo.android.contract.AttentionsContract;
import com.iyoyogo.android.presenter.AttentionsPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * 用户主页 — 关注
 */
public class UserFollowFragment extends BaseFragment<AttentionsContract.Presenter> implements AttentionsContract.View {


    @BindView(R.id.myRecyclerView)
    RecyclerView myRecyclerView;
    @BindView(R.id.follow_null)
    LinearLayout FollowNull;
    private String user_id;
    private String user_token;
    private String yo_user_id;
    @BindView(R.id.tv_tips)
    TextView tv_tips;
    private TextView btu_guanzhu;

    public UserFollowFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_follow;
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        Intent intent = getActivity().getIntent();
        yo_user_id = intent.getStringExtra("yo_user_id");
        mPresenter.getAttentions(getActivity(),user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    protected void initView() {
        super.initView();
     }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getAttentions(getActivity(),user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    protected AttentionsContract.Presenter createPresenter() {
        return new AttentionsPresenter(getActivity(),this);
    }

    @Override
    public void getAttentionsSuccess(AttentionsBean attentionsBean) {
        List<AttentionsBean.DataBean.ListBean> list = attentionsBean.getData().getList();
        if (list.size() == 0) {
            myRecyclerView.setVisibility(View.GONE);
            FollowNull.setVisibility(View.VISIBLE);
        } else {
            AttentionsAdapter adapter = new AttentionsAdapter(R.layout.item_addconcern_recycleview, list);
            myRecyclerView.setAdapter(adapter);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    btu_guanzhu = view.findViewById(R.id.tv_guanzhu);
                    mPresenter.addAttention1(getActivity(),user_id, user_token, list.get(position).getUser_id());
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
        } else {
            btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
            btu_guanzhu.setText("+关注");
            btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void deleteAttention(AttentionBean attentionBean) {

    }
}
