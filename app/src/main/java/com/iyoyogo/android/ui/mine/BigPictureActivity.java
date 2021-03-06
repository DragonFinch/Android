package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        RequestOptions requestOptions1 = new RequestOptions();
        requestOptions1.placeholder(R.mipmap.default_touxiang);
        requestOptions1.error(R.mipmap.default_touxiang);
        Glide.with(this).load(url).apply(requestOptions1).into(imgBig);
        imgBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
