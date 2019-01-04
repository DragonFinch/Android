package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoJiCenterAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.contract.YoJiContentContract;
import com.iyoyogo.android.presenter.YoJiContentPresenter;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * yo记内容
 */
public class YoJiFragment extends BaseFragment<YoJiContentContract.Presenter> implements YoJiContentContract.View {

    @BindView(R.id.recycler_yoji)
    RecyclerView recyclerYoji;
    Unbinder unbinder;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.list_blank)
    LinearLayout listBlank;
    Unbinder unbinder1;
    private String user_id;
    private String user_token;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        String yo_user_id = bundle.getString("yo_user_id");
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getYoJiContent(user_id, user_token, yo_user_id, "1", "20");
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
            recyclerYoji.setVisibility(View.GONE);
            listBlank.setVisibility(View.VISIBLE);
        } else {
            YoJiCenterAdapter yoJiCenterAdapter = new YoJiCenterAdapter(getContext(), list);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
