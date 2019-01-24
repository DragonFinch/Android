package com.iyoyogo.android.ui.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.EditImageOrVideoAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.edit.filter.FilterActivity;
import com.iyoyogo.android.camera.selectmedia.bean.MediaData;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.ui.home.yoji.NewPublishYoJiActivity;
import com.iyoyogo.android.ui.home.yoxiu.NewPublishYoXiuActivity;
import com.iyoyogo.android.utils.StatusBarUtil;
import com.iyoyogo.android.view.MyViewPagers;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yalantis.ucrop.model.CutInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditImageOrVideoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.viewPager)
    MyViewPagers mViewPager;
    @BindView(R.id.btn_done)
    Button mBtnDone;
    @BindView(R.id.iv_filter)
    ImageView mIvFilter;
    @BindView(R.id.iv_coup)
    ImageView mIvCoup;
    @BindView(R.id.iv_video)
    ImageView mIvVideo;
    @BindView(R.id.ll_option_image)
    LinearLayout mLlOptionImage;
    @BindView(R.id.rl_done)
    RelativeLayout mRlDone;

    private List<LocalMedia> localMedia;

    private boolean isEdit = false;

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
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
//        statusbar();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null&&bundle.containsKey("LOCALMEDIA")){
            localMedia = new ArrayList<>();
            localMedia.add(bundle.getParcelable("LOCALMEDIA"));
        }else{
            localMedia = PictureSelector.obtainMultipleResult(getIntent());
        }
        mViewPager.setCanScroll(true);
        mViewPager.setAdapter(new EditImageOrVideoAdapter(localMedia, this));
        if (localMedia != null && localMedia.size() > 0) {
            mIvVideo.setVisibility(localMedia.get(0).getPath().contains(".mp4") ? View.VISIBLE : View.GONE);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (localMedia.get(position).getPath().contains(".mp4")) {
                    mIvVideo.setVisibility(View.VISIBLE);
                } else {
                    mIvVideo.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        File foder = new File(Environment.getExternalStorageDirectory() + "/Yoyogo/Image");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File video = new File(Environment.getExternalStorageDirectory() + "/Yoyogo/Video");
        if (!video.exists()) {
            video.mkdirs();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.btn_done, R.id.iv_filter, R.id.iv_coup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                isEdit = !isEdit;
                mTvEdit.setText(isEdit ? "完成" : "编辑");
                mViewPager.setCanScroll(!isEdit);
                mRlDone.setVisibility(isEdit ? View.GONE : View.VISIBLE);
                mLlOptionImage.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn_done:
                int type = getIntent().getIntExtra("type", 1);
                if (type == 1) {
                    startActivity(getIntent().setClass(this, NewPublishYoJiActivity.class));
                } else if (type == 2) {
                    startActivity(getIntent().setClass(this, NewPublishYoXiuActivity.class));
                } else {
                    setResult(200, getIntent());
                }
                finish();
                break;

            case R.id.iv_filter:
                String filePath = TextUtils.isEmpty(localMedia.get(mViewPager.getCurrentItem()).getCompressPath()) ? localMedia.get(mViewPager.getCurrentItem()).getPath() : localMedia.get(mViewPager.getCurrentItem()).getCompressPath();
                startActivityForResult(new Intent(this, AddFilterActivity.class).putExtra("data", filePath), 0);
                break;
            case R.id.iv_coup:
                String path = TextUtils.isEmpty(localMedia.get(mViewPager.getCurrentItem()).getCompressPath()) ? localMedia.get(mViewPager.getCurrentItem()).getPath() : localMedia.get(mViewPager.getCurrentItem()).getCompressPath();
                if (path.contains(".mp4")) {
                    startActivityForResult(new Intent(this, CutVideoActivity.class).putExtra("data", path), 0);
                } else {
                    startActivityForResult(new Intent(this, CutImageActivity.class).putExtra("data", path), 0);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position = mViewPager.getCurrentItem();
        if (resultCode == RESULT_OK) {
            CutInfo cutInfo = (CutInfo) data.getSerializableExtra("data");
            LocalMedia local = this.localMedia.get(position);
            local.setCutPath(cutInfo.getCutPath());
            local.setCompressPath(cutInfo.getCutPath());
            local.setCut(cutInfo.isCut());
            local.setWidth(cutInfo.getImageWidth());
            local.setHeight(cutInfo.getImageHeight());
            mViewPager.setAdapter(new EditImageOrVideoAdapter(localMedia, this));
            mViewPager.setCurrentItem(position);
        } else if (resultCode == 100) {
            String path = data.getStringExtra("data");
            localMedia.get(mViewPager.getCurrentItem()).setPath(path);
            mViewPager.setAdapter(new EditImageOrVideoAdapter(localMedia, this));
            mViewPager.setCurrentItem(position);
        } else if (resultCode == 300) {
            String path = data.getStringExtra("data");
            localMedia.get(mViewPager.getCurrentItem()).setPath(path);
            localMedia.get(mViewPager.getCurrentItem()).setCompressPath(path);
            mViewPager.setAdapter(new EditImageOrVideoAdapter(localMedia, this));
            mViewPager.setCurrentItem(position);
        }
    }
}
