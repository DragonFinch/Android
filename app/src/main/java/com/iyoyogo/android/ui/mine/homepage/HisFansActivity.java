package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
    String user_id;
    String user_token;
    TextView btu_guanzhu;

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
        user_id = SpUtils.getString(this, "user_id", null);
        user_token = SpUtils.getString(this, "user_token", null);
        Intent intent = getIntent();
        String yo_user_id = intent.getStringExtra("yo_user_id");
        mPresenter.getHisHans(user_id, user_token, yo_user_id, 1 + "", 20 + "");
    }

    @Override
    public void getHisHansSuccess(HisFansBean hisFansBean) {
        List<HisFansBean.DataBean.ListBean> list = hisFansBean.getData().getList();
        if (list.size() == 0) {
            LinBitmap.setVisibility(View.VISIBLE);
        } else {
            LinBitmap.setVisibility(View.GONE);
            HisFansAdapter adapter = new HisFansAdapter(R.layout.item_addconcern_recycleview, list);
            myRecyclerView.setAdapter(adapter);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    btu_guanzhu = view.findViewById(R.id.tv_guanzhu);
                    mPresenter.addAttention1(user_id, user_token, list.get(position).getUser_id());
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

    @OnClick(R.id.message_center_back_im_id)
    public void onViewClicked() {
        finish();
    }

}
