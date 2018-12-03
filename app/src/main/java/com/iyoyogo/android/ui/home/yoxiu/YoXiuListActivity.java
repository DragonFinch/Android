package com.iyoyogo.android.ui.home.yoxiu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoXiuListAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.contract.YoXiuListContract;
import com.iyoyogo.android.presenter.YoXiuListPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class YoXiuListActivity extends BaseActivity<YoXiuListContract.Presenter>implements YoXiuListContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.share_img)
    ImageView shareImg;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.recycler_yoxiu_list)
    RecyclerView recyclerYoxiuList;

    @Override
    protected void initView() {
        super.initView();
        shareImg.setVisibility(View.GONE);
        tvMessage.setText("yo.秀");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_xiu_list;
    }

    @Override
    protected YoXiuListContract.Presenter createPresenter() {
        return new YoXiuListPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(YoXiuListActivity.this, "user_id", null);
        String user_token = SpUtils.getString(YoXiuListActivity.this, "user_token", null);
        mPresenter.getYoXiuList(user_id,user_token,1);
    }

    @OnClick({R.id.back, R.id.share_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
finish();
                break;
            case R.id.share_img:

                break;
        }
    }

    @Override
    public void getYoXiuListSuccess(YouXiuListBean.DataBean data) {
        List<YouXiuListBean.DataBean.ListBean> mList=new ArrayList<>();
        List<YouXiuListBean.DataBean.ListBean> list = data.getList();
        mList.addAll(list);
        recyclerYoxiuList.setLayoutManager(new LinearLayoutManager(YoXiuListActivity.this));
        YoXiuListAdapter yoXiuListAdapter = new YoXiuListAdapter(YoXiuListActivity.this,mList);

        recyclerYoxiuList.setAdapter(yoXiuListAdapter);
    }
}
