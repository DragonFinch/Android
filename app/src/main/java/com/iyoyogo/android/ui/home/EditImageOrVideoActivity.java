package com.iyoyogo.android.ui.home;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.EditImageOrVideoAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.home.yoji.NewPublishYoJiActivity;
import com.iyoyogo.android.ui.home.yoxiu.NewPublishYoXiuActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditImageOrVideoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_edit)
    TextView  mTvEdit;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.btn_done)
    Button    mBtnDone;

    private List<LocalMedia> localMedia;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_image_or_video;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        localMedia = PictureSelector.obtainMultipleResult(getIntent());
        mViewPager.setAdapter(new EditImageOrVideoAdapter(localMedia, this));
    }

    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:

                break;
            case R.id.btn_done:
                if (getIntent().getIntExtra("type",1)==1){
                    startActivity(getIntent().setClass(this, NewPublishYoJiActivity.class));
                }else {
                    startActivity(getIntent().setClass(this, NewPublishYoXiuActivity.class));
                }
                finish();
                break;
        }
    }
}
