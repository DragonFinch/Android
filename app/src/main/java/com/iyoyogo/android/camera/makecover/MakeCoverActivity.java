package com.iyoyogo.android.camera.makecover;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.VideoFragment;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.edit.filter.FilterAdapter;
import com.iyoyogo.android.camera.interfaces.OnTitleBarClickListener;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.PathUtils;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.ToastUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.dataInfo.VideoClipFxInfo;
import com.iyoyogo.android.ui.common.EmptyActivity;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoFx;
import com.meicam.sdk.NvsVideoTrack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ms on 2018/9/5 0030.
 */

public class MakeCoverActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "MakeCoverActivity";
    private final int FILTERREQUESTLIST = 1020;
    private final int UPDATE_IMAGE = 100;
    private VideoFragment mVideoFragment;
    private CustomTitleBar mTitleBar;
    private RelativeLayout mBottomLayout, mIntensityLayout, mDownloadMoreBtn;
    private SeekBar mIntensitySeekBar;

    private RecyclerView mFilterRecyclerView;
    private FilterAdapter mFilterAdapter;
    private ArrayList<FilterItem> mFilterItemArrayList;
    private int mAssetType = NvAsset.ASSET_FILTER;
    private NvAssetManager mAssetManager;
    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    private VideoClipFxInfo mVideoClipFxInfo;
    private int mSelectedPos = 0;
    private Bitmap mCoverBitmap;
    private Button mResetImageBtn;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case UPDATE_IMAGE:
                    mVideoFragment.setClipImageViewBitmap(mCoverBitmap, false);
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mIntensityLayout = (RelativeLayout) findViewById(R.id.intensity_layout);
        mIntensitySeekBar = (SeekBar) findViewById(R.id.intensitySeekBar);
        mFilterRecyclerView = (RecyclerView) findViewById(R.id.filter_list);
        mDownloadMoreBtn = (RelativeLayout) findViewById(R.id.download_more_btn);
        mResetImageBtn = (Button) findViewById(R.id.reset_image_btn);
    }

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_make_cover;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.edit);
        mTitleBar.setTextRightVisible(View.VISIBLE);
        mTitleBar.setTextRight("生成");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if(!init()) {
            return;
        }
        initFilterDataList();
        initVideoFragment();
        initFilterViewList();
        updateIntensityLayout();
        mDownloadMoreBtn.setOnClickListener(this);
        mResetImageBtn.setOnClickListener(this);
        mTitleBar.setOnTitleBarClickListener(new OnTitleBarClickListener() {
            @Override
            public void OnBackImageClick() {
                removeTimeline();
                AppManager.getInstance().finishActivity();
            }

            @Override
            public void OnCenterTextClick() {

            }

            @Override
            public void OnRightTextClick() {
                compileCoverImage();
            }
        });
        mIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float value = (float) (progress * 0.01);
                    mVideoClipFxInfo.setFxIntensity(value);
                    updateFxIntensity(value);
                    mVideoFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),0);
                    takeImageBitmap(0, false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.download_more_btn:
                mDownloadMoreBtn.setClickable(false);
                AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), EmptyActivity.class, null,FILTERREQUESTLIST);
                break;
            case R.id.reset_image_btn:
                if(mVideoFragment != null) {
                    mVideoFragment.resetClipImageView();
                }
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDownloadMoreBtn.setClickable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case FILTERREQUESTLIST:
                initFilterDataList();
                mFilterAdapter.setFilterDataList(mFilterItemArrayList);
                mFilterAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        removeTimeline();
        AppManager.getInstance().finishActivity();
        super.onBackPressed();
    }

    private void removeTimeline(){
        TimelineUtil.removeTimeline(mTimeline);
        mTimeline = null;
    }

    private boolean init(){
        mTimeline = TimelineUtil.createTimeline();
        if(mTimeline == null)
            return false;

        TimelineUtil.applyTheme(mTimeline, null);
        mFilterItemArrayList = new ArrayList<>();
        mVideoClipFxInfo = TimelineData.instance().getVideoClipFxData();
        if(mVideoClipFxInfo == null)
            mVideoClipFxInfo = new VideoClipFxInfo();
        mAssetManager = NvAssetManager.sharedInstance();
        mAssetManager.searchLocalAssets(mAssetType);
        String bundlePath = "filter";
        mAssetManager.searchReservedAssets(mAssetType,bundlePath);
        mIntensitySeekBar.setMax(100);
        float intensity = mVideoClipFxInfo.getFxIntensity();
        mIntensitySeekBar.setProgress((int) (intensity * 100));
        return true;
    }
    private void initFilterDataList(){
        mFilterItemArrayList.clear();
        FilterItem filterItem = new FilterItem();
        filterItem.setFilterName("无");
        filterItem.setImageId(R.mipmap.no);
        mFilterItemArrayList.add(filterItem);

        ArrayList<NvAsset> videoFxList = mAssetManager.getUsableAssets(mAssetType,NvAsset.AspectRatio_All,0);
        String bundlePath =  "filter/info.txt";
        Util.getBundleFilterInfo(this, videoFxList, bundlePath);

        int ratio = TimelineData.instance().getMakeRatio();
        for(NvAsset asset:videoFxList) {
            if((ratio & asset.aspectRatio) == 0)
                continue;

            FilterItem newFilterItem = new FilterItem();
            if(asset.isReserved) {
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUNDLE);
            } else {
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_PACKAGE);
            }
            newFilterItem.setFilterName(asset.name);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
            mFilterItemArrayList.add(newFilterItem);
        }

        int[] resImags = {
                R.mipmap.sage,R.mipmap.maid,R.mipmap.mace,
                R.mipmap.lace,R.mipmap.mall,R.mipmap.sap,R.mipmap.sara,
                R.mipmap.pinky,R.mipmap.sweet,R.mipmap.fresh
        };

        List<String> builtinVideoFxList = mStreamingContext.getAllBuiltinVideoFxNames();
        for(int i = 0;i < builtinVideoFxList.size();i++) {
            String transitionName = builtinVideoFxList.get(i);
            FilterItem newFilterItem = new FilterItem();
            newFilterItem.setFilterName(transitionName);
            if(i < resImags.length) {
                int imageId = resImags[i];
                newFilterItem.setImageId(imageId);
            }
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
            mFilterItemArrayList.add(newFilterItem);
        }

        mSelectedPos = getSelectedFilterPos();

        if(mFilterAdapter != null) {
            mFilterAdapter.setSelectPos(mSelectedPos);
        }
    }

    private int getSelectedFilterPos() {
        if (mFilterItemArrayList == null || mFilterItemArrayList.size() == 0)
            return -1;

        if (mVideoClipFxInfo != null) {
            String fxName = mVideoClipFxInfo.getFxId();
            if(TextUtils.isEmpty(fxName))
                return 0;

            for (int i = 0; i < mFilterItemArrayList.size(); i++) {
                FilterItem newFilterItem = mFilterItemArrayList.get(i);
                if (newFilterItem == null)
                    continue;

                int filterMode = newFilterItem.getFilterMode();
                String filterName;
                if (filterMode == FilterItem.FILTERMODE_BUILTIN) {
                    filterName = newFilterItem.getFilterName();
                } else {
                    filterName = newFilterItem.getPackageId();
                }

                if (fxName.equals(filterName)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void initVideoFragment() {
        mVideoFragment = new VideoFragment();
        mVideoFragment.setFragmentLoadFinisedListener(new VideoFragment.OnFragmentLoadFinisedListener() {
            @Override
            public void onLoadFinished() {
                mVideoFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),0);
                takeImageBitmap(0, true);
            }
        });
        mVideoFragment.setTimeline(mTimeline);
        Bundle bundle = new Bundle();
        bundle.putInt("titleHeight",mTitleBar.getLayoutParams().height);
        bundle.putInt("bottomHeight",mBottomLayout.getLayoutParams().height);
        bundle.putBoolean("playBarVisible",false);
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        mVideoFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_layout, mVideoFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mVideoFragment);
    }

    private void initFilterViewList() {
        mFilterAdapter = new FilterAdapter(this);
        mFilterAdapter.setFilterDataList(mFilterItemArrayList);
        mFilterAdapter.setSelectPos(mSelectedPos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFilterRecyclerView.setLayoutManager(linearLayoutManager);
        mFilterRecyclerView.setAdapter(mFilterAdapter);

        mFilterAdapter.setOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position == 0) {
                    mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_BUILTIN);
                    mVideoClipFxInfo.setFxId(null);
                } else {
                    if(mFilterItemArrayList.size() < position) {
                        return;
                    }

                    FilterItem filterItem = mFilterItemArrayList.get(position);
                    int filterMode = filterItem.getFilterMode();
                    if(filterMode == FilterItem.FILTERMODE_BUILTIN) {
                        String filterName = filterItem.getFilterName();
                        mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_BUILTIN);
                        mVideoClipFxInfo.setFxId(filterName);
                    } else if(filterMode == FilterItem.FILTERMODE_BUNDLE) {
                        String packageId = filterItem.getPackageId();
                        mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_PACKAGE);
                        mVideoClipFxInfo.setFxId(packageId);
                    } else {
                        String packageId = filterItem.getPackageId();
                        mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_PACKAGE);
                        mVideoClipFxInfo.setFxId(packageId);
                    }
                    mIntensitySeekBar.setProgress(100);
                    mVideoClipFxInfo.setFxIntensity(1.0f);
                }
                mSelectedPos = position;
                updateIntensityLayout();
                TimelineUtil.buildTimelineFilter(mTimeline, mVideoClipFxInfo);
                mVideoFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),0);
                takeImageBitmap(0, false);
            }

            @Override
            public void onSameItemClick() {
                mVideoFragment.playVideoButtonCilck();
            }
        });
    }

    private void updateIntensityLayout() {
        if(mSelectedPos <= 0) {
            mIntensityLayout.setVisibility(View.INVISIBLE);
        } else {
            mIntensityLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateFxIntensity(float value) {
        if(mTimeline == null)
            return;

        NvsVideoTrack videoTrack = mTimeline.getVideoTrackByIndex(0);
        if(videoTrack == null)
            return;

        for(int i = 0;i < videoTrack.getClipCount();i++) {
            NvsVideoClip videoClip = videoTrack.getClipByIndex(i);
            if(videoClip == null)
                continue;

            int fxCount = videoClip.getFxCount();
            for(int j=0;j<fxCount;j++) {
                NvsVideoFx fx = videoClip.getFxByIndex(j);
                if(fx == null)
                    continue;

                String name = fx.getBuiltinVideoFxName();
                if(name == null) {
                    continue;
                }
                if(name.equals("Color Property") || name.equals("Transform 2D")) {
                    continue;
                }
                fx.setFilterIntensity(value);
            }
        }
    }

    /**
     *  根据时间（微妙）获取timeline某一时刻的图像
     */
    private void takeImageBitmap(long time, boolean reset) {
        if (mStreamingContext == null || mTimeline == null) {
            return;
        }
        NvsRational rational = new NvsRational(1, 1);
        mCoverBitmap = mStreamingContext.grabImageFromTimeline(mTimeline, time, rational);
        if(reset) {
            mVideoFragment.setClipImageViewBitmap(mCoverBitmap, reset);
        } else {
            mHandler.sendEmptyMessage(UPDATE_IMAGE);
        }
    }

    /**
     *  导出封面图片
     */
    private void compileCoverImage() {
        Bitmap bitmap = mVideoFragment.getCoverImageBitmap();
        if(bitmap == null) {
            ToastUtil.showToast(this, "保存失败！");
            return;
        }
        String cover_path = PathUtils.getCoverImagePath();
        if(cover_path == null || cover_path.isEmpty()) {
            return;
        }
        Log.e("===>", "cover_path: " + cover_path);
        boolean ret = Util.saveBitmapToSD(bitmap, cover_path);
        if(ret) {
            ToastUtil.showToast(this, "保存成功！\n保存路径：" + cover_path);
        } else {
            ToastUtil.showToast(this, "保存失败！");
            File file = new File(cover_path);
            if(file.exists()) {
                file.delete();
            }
        }
    }
}
