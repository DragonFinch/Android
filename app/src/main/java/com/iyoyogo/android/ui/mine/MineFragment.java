package com.iyoyogo.android.ui.mine;

import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.common.MainActivity;
import com.iyoyogo.android.ui.home.HomeFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseFragment {


    @BindView(R.id.jump_home)
    TextView jumpHome;
    Unbinder unbinder;

    public MineFragment() {
        // Required empty public constructor
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }



    @OnClick(R.id.jump_home)
    public void onViewClicked() {
        HomeFragment homeFragment = new HomeFragment();
        ((MainActivity)mActivity).switchFragment(homeFragment);
    }
}
