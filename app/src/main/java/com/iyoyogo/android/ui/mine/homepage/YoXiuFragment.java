package com.iyoyogo.android.ui.mine.homepage;


import android.os.Bundle;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.contract.YoXiuContentContract;
import com.iyoyogo.android.presenter.YoXiuContentPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * yo秀内容
 */
public class YoXiuFragment extends BaseFragment<YoXiuContentContract.Presenter> implements YoXiuContentContract.View {


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
        mPresenter.getYoXiuContent(user_id,user_token,yo_user_id,1+"",20+"");
    }

    @Override
    protected YoXiuContentContract.Presenter createPresenter() {
        return new YoXiuContentPresenter(this);
    }

    @Override
    public void getYoXiuContentSuccess(YoXiuContentBean.DataBean data) {
        List<YoXiuContentBean.DataBean.ListBean> list = data.getList();

    }
}
