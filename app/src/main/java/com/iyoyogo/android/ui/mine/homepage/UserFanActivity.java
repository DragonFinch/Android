package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 用户主页 — 粉丝
 */

public class UserFanActivity extends BaseActivity {

    @BindView(R.id.rb_yoji)
    RadioButton rbYoji;
    @BindView(R.id.rb_yoxiu)
    RadioButton rbYoxiu;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    private UserFollowFragment followFragment = new UserFollowFragment();
    private UserFansFragment fansFragment = new UserFansFragment();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_fans;
    }

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.white));
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if (id == 1) {
            rbYoji.setChecked(true);
            switchFragment(followFragment);
        } else if (id == 2) {
            rbYoxiu.setChecked(true);
            switchFragment(fansFragment);
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yoji:
                        switchFragment(followFragment);
                        break;
                    case R.id.rb_yoxiu:
                        switchFragment(fansFragment);
                        break;
                }
            }
        });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

//    private FragmentTransaction switchFragment(Fragment targetFragment) {
//        transaction = getSupportFragmentManager()
//                .beginTransaction();
//        if (!targetFragment.isAdded()) {
//            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
//            if (currentFragment != null) {
//                transaction.hide(currentFragment);
//            }
//            transaction.add(R.id.fragment, targetFragment, targetFragment.getClass().getName());
//        } else {
//            transaction
//                    .hide(currentFragment)
//                    .show(targetFragment);
//        }
//        currentFragment = targetFragment;
//        return transaction;
//    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment)
                .commitAllowingStateLoss();
    }


    @OnClick({R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_center_back_im_id:
                finish();
                break;
        }
    }

}
