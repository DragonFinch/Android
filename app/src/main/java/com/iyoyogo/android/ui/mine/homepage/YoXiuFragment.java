package com.iyoyogo.android.ui.mine.homepage;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoXiuContentAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.contract.YoXiuContentContract;
import com.iyoyogo.android.presenter.YoXiuContentPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * yo秀内容
 */
public class YoXiuFragment extends BaseFragment<YoXiuContentContract.Presenter> implements YoXiuContentContract.View {


    @BindView(R.id.recycler_yoxiu)
    RecyclerView recyclerYoxiu;
    Unbinder unbinder;
    @BindView(R.id.list_blank)
    LinearLayout listBlank;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    private String user_id;
    private String user_token;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yo_xiu;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        String yo_user_id = bundle.getString("yo_user_id");
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getYoXiuContent(user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    protected YoXiuContentContract.Presenter createPresenter() {
        return new YoXiuContentPresenter(this);
    }

    @Override
    public void getYoXiuContentSuccess(YoXiuContentBean.DataBean data) {
        List<YoXiuContentBean.DataBean.ListBean> list = data.getList();
        if (list.size() == 0) {
            listBlank.setVisibility(View.VISIBLE);
            recyclerYoxiu.setVisibility(View.GONE);
            tvTips.setText("TA好像比较低调，还没有发布任何yo·秀yo～");
        } else {
            listBlank.setVisibility(View.GONE);
            recyclerYoxiu.setVisibility(View.VISIBLE);
            YoXiuContentAdapter yoXiuContentAdapter = new YoXiuContentAdapter(getContext(), list);
            recyclerYoxiu.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerYoxiu.setAdapter(yoXiuContentAdapter);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
