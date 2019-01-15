package com.iyoyogo.android.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.VideoFilterListAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.dataInfo.VideoClipFxInfo;
import com.iyoyogo.android.utils.AssetFxUtil;
import com.iyoyogo.android.view.LoadingDialog;
import com.meicam.sdk.NvsAudioResolution;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoResolution;
import com.meicam.sdk.NvsVideoTrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class AddFilterActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SeekBar.OnSeekBarChangeListener, NvsStreamingContext.CompileCallback {

    @BindView(R.id.liveWindow)
    NvsLiveWindow mLiveWindow;
    @BindView(R.id.iv_back)
    ImageView     mIvBack;
    @BindView(R.id.tv_edit)
    TextView      mTvEdit;
    @BindView(R.id.seekbar)
    SeekBar       mSeekbar;
    @BindView(R.id.ll_seek)
    LinearLayout  mLlSeek;
    @BindView(R.id.recyclerView)
    RecyclerView  mRecyclerView;

    private NvsStreamingContext mStreamingContext;
    private NvsTimeline         mTimeline;
    private NvsVideoTrack       mVideoTrack;

    private String                path;
    private NvAssetManager        mAssetManager;
    private ArrayList<FilterItem> mFilterItemArrayList;

    private VideoFilterListAdapter mAdapter;
    private VideoClipFxInfo        mVideoClipFxInfo;
    private String filterPath="";

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.init(this, null);//初始化Streaming Context
        return R.layout.activity_add_filter;
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
        path = getIntent().getStringExtra("data");

        mLiveWindow.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        mLiveWindow.setBackgroundColor(255, 255, 255);
        if (!mStreamingContext.connectCapturePreviewWithLiveWindow(mLiveWindow)) {
            return;
        }

        NvsVideoResolution videoEditRes = new NvsVideoResolution();
        /*
            注意：请在使用LiveWindow预览的时候，将NvsLiveWindow的宽高比与此处保持一致。
        */
        if (path.contains(".mp4")) {
            videoEditRes.imageWidth = 720; /*视频分辨率的宽*/
            videoEditRes.imageHeight = 1280; /*视频分辨率的高*/
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            if (options.outHeight > options.outWidth) {
                videoEditRes.imageWidth = 720;
                videoEditRes.imageHeight = 720 * options.outHeight / options.outWidth;
            } else {
                videoEditRes.imageWidth = 720 * options.outWidth / options.outHeight;
                videoEditRes.imageHeight = 720;
            }
        }
        videoEditRes.imagePAR = new NvsRational(1, 1); /*像素比，设为1:1*/

        /*创建时间线*/
        mTimeline =  TimelineUtil.newTimeline(videoEditRes);

        //将timeline连接到LiveWindow控件
        mStreamingContext.connectTimelineWithLiveWindow(mTimeline, mLiveWindow);
        mStreamingContext.setCompileCallback(this);//设置生成回调接口

        if (mTimeline==null){
            Toast.makeText(this, "图片有误，请重新选择图片", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        /*添加视频轨道，如果不做画中画，添加一条视频轨道即可*/
        mVideoTrack = mTimeline.appendVideoTrack();
        NvsVideoClip clip = mVideoTrack.appendClip(path);

//        mStreamingContext.playbackTimeline(mTimeline, 0, mTimeline.getDuration(), NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, true, 0);
        mStreamingContext.seekTimeline(mTimeline, 0, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, 0);

        mVideoClipFxInfo = TimelineData.instance().getVideoClipFxData();
        if (mVideoClipFxInfo == null) {
            mVideoClipFxInfo = new VideoClipFxInfo();
        }
        mAssetManager = NvAssetManager.sharedInstance();
        mAssetManager.searchLocalAssets(NvAsset.ASSET_FILTER);
        String bundlePath = "filter";
        mAssetManager.searchReservedAssets(NvAsset.ASSET_FILTER, bundlePath);


        mSeekbar.setOnSeekBarChangeListener(this);
    }

    private void initFilterDataList() {
        mFilterItemArrayList = AssetFxUtil.getFilterData(this,
                getLocalData(),
                mStreamingContext.getAllBuiltinCaptureVideoFxNames(),
                true,
                true);
    }


    private ArrayList<NvAsset> getLocalData() {
        return mAssetManager.getUsableAssets(NvAsset.ASSET_FILTER, NvAsset.AspectRatio_All, 0);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mAssetManager = NvAssetManager.sharedInstance();
        mAssetManager.searchLocalAssets(NvAsset.ASSET_FILTER);
        String bundlePath = "filter";
        mAssetManager.searchReservedAssets(NvAsset.ASSET_FILTER, bundlePath);
        initFilterDataList();
        mAdapter = new VideoFilterListAdapter(mFilterItemArrayList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setSelectPosition(position);

        mSeekbar.setProgress(100);
        if (position == 0) {
            mLlSeek.setVisibility(View.INVISIBLE);
            mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_BUILTIN);
            mVideoClipFxInfo.setFxId(null);
            TimelineUtil.buildTimelineFilters(mTimeline, mVideoClipFxInfo);
            mStreamingContext.seekTimeline(mTimeline, 0, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, 0);
            return;
        } else {
            mLlSeek.setVisibility(View.VISIBLE);
            FilterItem filterItem = mFilterItemArrayList.get(position);
            int        filterMode = filterItem.getFilterMode();
            if (filterMode == FilterItem.FILTERMODE_BUILTIN) {
                String filterId = filterItem.getFilterId();
                mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_BUILTIN);
                mVideoClipFxInfo.setFxId(filterId);
                mVideoClipFxInfo.setIsCartoon(filterItem.getIsCartoon());
                mVideoClipFxInfo.setGrayScale(filterItem.getGrayScale());
                mVideoClipFxInfo.setStrokenOnly(filterItem.getStrokenOnly());
            } else {
                String packageId = filterItem.getPackageId();
                mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_PACKAGE);
                mVideoClipFxInfo.setFxId(packageId);
            }
            mVideoClipFxInfo.setFxIntensity(1.0f);
            TimelineUtil.buildTimelineFilters(mTimeline, mVideoClipFxInfo);
        }

        mStreamingContext.seekTimeline(mTimeline, 0, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.tv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                if (path.contains(".mp4")) {
                    LoadingDialog.get().create(this).show();
                    filterPath     = Environment.getExternalStorageDirectory()+"/Yoyogo/Video" + "/filter_" + System.currentTimeMillis() + ".mp4";
                    long   duration = mTimeline.getDuration();
                    mStreamingContext.compileTimeline(mTimeline, 0, duration, filterPath, NvsStreamingContext.COMPILE_VIDEO_RESOLUTION_GRADE_720, NvsStreamingContext.COMPILE_BITRATE_GRADE_HIGH, 0);
                } else {
                    Bitmap bitmap = mStreamingContext.grabImageFromTimeline(mTimeline, 0, new NvsRational(1, 1));
                    String path   = saveImage(bitmap);
                    setResult(300, new Intent().putExtra("data", path));
                    finish();
                }
                break;
        }
    }

    public String saveImage(Bitmap bitmap) {
        File foder = new File(Environment.getExternalStorageDirectory()+"/Yoyogo/Image");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory()+"/Yoyogo/Image", "filter_" + System.currentTimeMillis() + ".png");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri    uri    = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mVideoClipFxInfo.setFxIntensity((float) progress / 100.0f);
        TimelineUtil.buildTimelineFilters(mTimeline, mVideoClipFxInfo);
        mStreamingContext.seekTimeline(mTimeline, 0, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onCompileProgress(NvsTimeline nvsTimeline, int i) {

    }

    @Override
    public void onCompileFinished(NvsTimeline nvsTimeline) {
        LoadingDialog.get().close();
        setResult(300, new Intent().putExtra("data", filterPath));
        finish();
    }

    @Override
    public void onCompileFailed(NvsTimeline nvsTimeline) {
        LoadingDialog.get().close();
        Toast.makeText(this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
        finish();
    }
}
