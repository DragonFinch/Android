package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.WelcomeViewPagerAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 欢迎页
 */

public class WelcomeActivity extends BaseActivity {
    int[] image_ids = {R.mipmap.welcome2, R.mipmap.welcome3, R.mipmap.welcome4};
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.welcome_enter_btn)
    Button welcomeEnterBtn;
    @BindView(R.id.ll_dot_group)
    LinearLayout ll_dot_group;
    private int previousPosition;


    @Override
    protected void initView() {
        super.initView();
        statusbar();
        boolean isTrue = SpUtils.getBoolean(WelcomeActivity.this, "isTrue", false);
        if (isTrue) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
        initViewPagerData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    private void initViewPagerData() {


        View dotView;
        for (int i = 0; i < image_ids.length; i++) {
            //准备小圆点的数据
            dotView = new View(this);
            dotView.setBackgroundResource(R.drawable.selector_dot);
            //设置小圆点的宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 4);
            //设置每个小圆点之间距离
            if (i != 0) {
                params.leftMargin = 5;
            }
            dotView.setLayoutParams(params);
            //设置小圆点默认状态
            dotView.setEnabled(false);
            //把dotview加入到线性布局中
            ll_dot_group.addView(dotView);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //取出postion位置的小圆点 设置为true
                ll_dot_group.getChildAt(position).setEnabled(true);
                //把一个小圆点设置为false
                ll_dot_group.getChildAt(previousPosition).setEnabled(false);
                previousPosition = position;
                if (position == image_ids.length - 1) {
                    welcomeEnterBtn.setVisibility(View.VISIBLE);
                } else {
                    welcomeEnterBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new WelcomeViewPagerAdapter(this, image_ids));
        viewPager.setCurrentItem(0);
        ll_dot_group.getChildAt(0).setEnabled(true);
    }


    @OnClick(R.id.welcome_enter_btn)
    public void onViewClicked() {
        SpUtils.putBoolean(WelcomeActivity.this, "isTrue", true);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }
}
