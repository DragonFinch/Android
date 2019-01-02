package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.mine.AddCollectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 关注
 */
public class MyFollowActivity extends BaseActivity {

    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.like_me_title_tv_id)
    TextView likeMeTitleTvId;
    @BindView(R.id.tv_recommend_id)
    TextView tvRecommendId;
    @BindView(R.id.tv_follow_id)
    TextView tvFollowId;
    @BindView(R.id.fragment)
    View fragment;
    private FollowFragment followFragment = new FollowFragment();//关注的人
    private RecommendFragment recommendFragment = new RecommendFragment();//推荐话题
    private Fragment currentFragment = new Fragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_follow;
    }

    protected void initView() {
        super.initView();
        statusbar();
        switchFragment(recommendFragment).commit();
        tvRecommendId.setTextColor(Color.parseColor("#333333"));
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

    @OnClick({R.id.tv_recommend_id, R.id.tv_follow_id, R.id.add_collection, R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recommend_id://推荐
                switchFragment(recommendFragment).commit();
                break;
            case R.id.tv_follow_id://关注的人
                switchFragment(followFragment).commit();
                break;
            case R.id.add_collection://添加关注
                startActivity(new Intent(this, AddCollectionActivity.class));
                break;
            case R.id.message_center_back_im_id://finsh
                finish();
                break;
        }
    }
}
