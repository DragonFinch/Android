package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigPictureActivity extends BaseActivity {

    @BindView(R.id.img_big)
    ImageView imgBig;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_picture;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Glide.with(this).load(url).into(imgBig);
    }
}
