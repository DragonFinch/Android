package com.iyoyogo.android.ui.mine.homepage;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
    private String user_id;
    private String user_token;
    private String yo_user_id;

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
        mPresenter.getAttentions(user_id, user_token, yo_user_id, 1 + "", 20 + "");
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
        mPresenter.getAttentions(user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    protected AttentionsContract.Presenter createPresenter() {
        return new AttentionsPresenter(this);
    }

    @Override
    public void getAttentionsSuccess(AttentionsBean attentionsBean) {
        List<AttentionsBean.DataBean.ListBean> list = attentionsBean.getData().getList();
        AttentionsAdapter adapter = new AttentionsAdapter(R.layout.item_addconcern_recycleview, list);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView btu_guanzhu = view.findViewById(R.id.tv_guanzhu);
                int status = list.get(position).getStatus();
                Log.d("id", Integer.getInteger(list.get(position).getUser_id()) + "///--++//");
                if (status == 0) {//未关注
                    mPresenter.addAttention1(user_id, user_token, list.get(position).getUser_id());
                    btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
                    btu_guanzhu.setText("已关注");
                    btu_guanzhu.setTextColor(Color.parseColor("#888888"));
                }
                if (status == 1) {//已关注
                    mPresenter.addAttention1(user_id, user_token, list.get(position).getUser_id());
                    btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
                    btu_guanzhu.setText("+关注");
                    btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
                }
                mPresenter.getAttentions(user_id, user_token, yo_user_id, 1 + "", 20 + "");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void addAttentionSuccess(AttentionBean attentionBean) {

    }

}
