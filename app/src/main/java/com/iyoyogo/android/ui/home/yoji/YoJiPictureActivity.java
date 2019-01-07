package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoJiPictureAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoJiPictureActivity extends BaseActivity {


    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.back)
    ImageView back;
    private int index;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_picture;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        index = intent.getIntExtra("position", 0);
        ArrayList<String> logos = intent.getStringArrayListExtra("logos");
        tvCount.setText(index+1 + "/" + logos.size());
        YoJiPictureAdapter yoJiPictureAdapter = new YoJiPictureAdapter(getApplicationContext(), logos);
        vp.setAdapter(yoJiPictureAdapter);
        vp.setCurrentItem(index);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCount.setText(position+1  + "/" + logos.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.back, R.id.tv_like, R.id.tv_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_like:
                break;
            case R.id.tv_collection:
                break;
        }
    }
}
