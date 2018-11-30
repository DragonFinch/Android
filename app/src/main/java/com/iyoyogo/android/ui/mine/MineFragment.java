package com.iyoyogo.android.ui.mine;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.utils.StatusBarUtils;


public class MineFragment extends BaseFragment {






    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarUtils.setWindowStatusBarColor(getActivity(), R.color.white);
    }
}
