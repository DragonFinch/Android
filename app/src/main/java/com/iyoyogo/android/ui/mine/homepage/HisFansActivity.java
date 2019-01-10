package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.HisFansAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.HisFansBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.contract.HisHansContract;
import com.iyoyogo.android.presenter.HisFansPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 获取 用户 粉丝人群
 */
public class HisFansActivity extends BaseActivity<HisHansContract.Presenter> implements HisHansContract.View {

    @BindView(R.id.myRecyclerView)
    RecyclerView myRecyclerView;
    @BindView(R.id.Lin_Bitmap)
    LinearLayout LinBitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_his_fans;
    }

    @Override
    protected HisHansContract.Presenter createPresenter() {
        return new HisFansPresenter(this);
    }

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(this, "user_id", null);
        String user_token = SpUtils.getString(this, "user_token", null);
        Intent intent = getIntent();
        String yo_user_id = intent.getStringExtra("yo_user_id");
        mPresenter.getHisHans(user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    public void getHisHansSuccess(HisFansBean hisFansBean) {
        List<HisFansBean.DataBean.ListBean> list = hisFansBean.getData().getList();
        if (list.size() == 0) {
            LinBitmap.setVisibility(View.VISIBLE);
        }else {
            LinBitmap.setVisibility(View.GONE);
            HisFansAdapter adapter = new HisFansAdapter(R.layout.item_addconcern_recycleview, list);
            myRecyclerView.setAdapter(adapter);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void addAttentionSuccess(AttentionBean attentionBean) {

    }

    @OnClick(R.id.message_center_back_im_id)
    public void onViewClicked() {
        finish();
    }

}
