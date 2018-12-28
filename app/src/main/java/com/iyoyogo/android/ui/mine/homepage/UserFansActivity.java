package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.mine.AddCollectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 用户主页 — 粉丝
 */

public class UserFansActivity extends BaseActivity {

    @BindView(R.id.tv_guanzhu_id)
    TextView tvFollowId;
    @BindView(R.id.tv_fans_id)
    TextView tvFansId;
    @BindView(R.id.fragment)
    View fragment;
    private Fragment currentFragment = new Fragment();
    private UserFollowFragment followFragment = new UserFollowFragment();
    private UserFansFragment fansFragment = new UserFansFragment();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_fans;
    }

    protected void initView() {
        super.initView();
        statusbar();
        switchFragment(fansFragment).commit();
        tvFollowId.setTextColor(Color.parseColor("#333333"));
    }

    protected void onRestart() {
        Log.e("zz", "onRestart");
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            switchFragment(followFragment).commit();
        }else if (id == 2){
            switchFragment(fansFragment).commit();
        }
        super.onRestart();
    }


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    private FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    @OnClick({R.id.tv_fans_id, R.id.tv_guanzhu_id, R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fans_id://粉丝
                switchFragment(fansFragment).commit();
                break;
            case R.id.tv_follow_id://关注
                switchFragment(followFragment).commit();
                break;
            case R.id.message_center_back_im_id:
                finish();
                break;
        }
    }
}
