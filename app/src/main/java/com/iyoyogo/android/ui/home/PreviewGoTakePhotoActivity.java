package com.iyoyogo.android.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.home.yoxiu.NewPublishYoXiuActivity;
import com.iyoyogo.android.utils.util.UiUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewGoTakePhotoActivity extends BaseActivity {

    @BindView(R.id.iv_take_image)
    ImageView      mIvTakeImage;
    @BindView(R.id.status_bar)
    View           mStatusBar;
    @BindView(R.id.iv_back)
    ImageView      mIvBack;
    @BindView(R.id.iv_contrast_image)
    ImageView      mIvContrastImage;
    @BindView(R.id.ll_again)
    LinearLayout   mLlAgain;
    @BindView(R.id.iv_save)
    ImageView      mIvSave;
    @BindView(R.id.ll_publish)
    LinearLayout   mLlPublish;
    @BindView(R.id.rl_option)
    RelativeLayout mRlOption;
    @BindView(R.id.video_view)
    VideoView      mVideoView;
    @BindView(R.id.iv_video)
    ImageView      mIvVideo;
    @BindView(R.id.iv_video_bg)
    ImageView      mIvVideoBg;
    @BindView(R.id.ll_image)
    LinearLayout   mLlImage;
    @BindView(R.id.iv_sw_contrast)
    ImageView      mIvSwContrast;
    @BindView(R.id.ll_contrast)
    LinearLayout   mLlContrast;

    private String path;
    private String contrastUrl;

    private boolean isShowContrast = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_go_take_photo;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getStatusHeight(this)));
        }

        mVideoView.setOnCompletionListener(mp -> {
            mIvVideoBg.setVisibility(View.VISIBLE);
            mIvVideo.setVisibility(View.VISIBLE);
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        path = getIntent().getStringExtra("path");
        contrastUrl = getIntent().getStringExtra("data_url");
        if (path.contains(".mp4")) {
            mVideoView.setVideoPath(path);
            Glide.with(this).load(path).into(mIvVideoBg);
            mVideoView.setVisibility(View.VISIBLE);
            mIvVideo.setVisibility(View.VISIBLE);
            mIvVideoBg.setVisibility(View.VISIBLE);
            mLlImage.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(path).into(mIvTakeImage);
            mLlImage.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.GONE);
            mIvVideo.setVisibility(View.GONE);
            mIvVideoBg.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(contrastUrl)) {
                mIvContrastImage.setVisibility(View.VISIBLE);
                mLlContrast.setVisibility(View.VISIBLE);
                Glide.with(this).load(contrastUrl).into(mIvContrastImage);
            } else {
                mLlContrast.setVisibility(View.GONE);
                mIvContrastImage.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_again, R.id.iv_save, R.id.ll_publish, R.id.rl_video, R.id.iv_sw_contrast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_again:
                onBackPressed();
                break;
            case R.id.iv_save:
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(new File(path));
                intent.setData(uri);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.ll_publish:
                List<LocalMedia> localMedia = new ArrayList<>();
                LocalMedia local = new LocalMedia();
                local.setCompressPath(path);
                local.setPath(path);
                localMedia.add(local);
                startActivity(PictureSelector.putIntentResult(localMedia).setClass(this, NewPublishYoXiuActivity.class));
                finish();
                break;

            case R.id.rl_video:
                if (!path.contains(".mp4")) {
                    return;
                }
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mIvVideo.setVisibility(View.VISIBLE);
                } else {
                    mVideoView.start();
                    mIvVideo.setVisibility(View.GONE);
                    mIvVideoBg.setVisibility(View.GONE);
                }
                break;

            case R.id.iv_sw_contrast:
                isShowContrast = !isShowContrast;
                mIvContrastImage.setVisibility(isShowContrast ? View.VISIBLE : View.GONE);
                mIvSwContrast.setImageResource(isShowContrast ? R.mipmap.on : R.mipmap.off);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
        super.onBackPressed();
    }
}
