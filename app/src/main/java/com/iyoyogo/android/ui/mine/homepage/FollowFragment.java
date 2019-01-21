package com.iyoyogo.android.ui.mine.homepage;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AttentionsAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AttentionsBean;
import com.iyoyogo.android.contract.AttentionsContract;
import com.iyoyogo.android.presenter.AttentionsPresenter;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * 关注的人
 */
public class FollowFragment extends BaseFragment<AttentionsContract.Presenter> implements AttentionsContract.View {

    @BindView(R.id.myRecyclerView)
    RecyclerView MyRecyclerView;
    private String user_id;
    private String user_token;
    private String yo_user_id;
    private boolean falg = false;
    private TextView btu_guanzhu;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
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
        AttentionsAdapter adapter = new AttentionsAdapter(R.layout.item_addconcern_recycleview, list);
        MyRecyclerView.setAdapter(adapter);
        MyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //取消关注
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                btu_guanzhu = view.findViewById(R.id.tv_guanzhu);
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
