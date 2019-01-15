package com.iyoyogo.android.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.home.video.VideoTrimListener;
import com.iyoyogo.android.ui.home.video.VideoTrimmerView;
import com.iyoyogo.android.view.LoadingDialog;

import butterknife.BindView;

public class CutVideoActivity extends BaseActivity implements VideoTrimListener {

    @BindView(R.id.trimmer_view)
    VideoTrimmerView trimmerView;
    private String mVideoPath = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_video;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mVideoPath = getIntent().getStringExtra("data");

        trimmerView.setOnTrimVideoListener(this);
        trimmerView.initVideoByURI(Uri.parse(mVideoPath));
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onStartTrim() {
        LoadingDialog.get().create(this).show();
    }

    @Override
    public void onFinishTrim(String url) {
        LoadingDialog.get().close();
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        Log.d("VideoClipActivity", "url" + url);
        setResult(100, new Intent().putExtra("data", url));
        finish();
    }

    @Override
    public void onCancel() {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        trimmerView.onVideoPause();
        trimmerView.setRestoreState(true);
    }
}
