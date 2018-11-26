package com.iyoyogo.android.camera.edit.clipEdit.adjust;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.BackupData;
import com.iyoyogo.android.camera.edit.clipEdit.SingleClipFragment;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.meicam.sdk.NvsPanAndScan;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoFx;
import com.meicam.sdk.NvsVideoTrack;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class AdjustActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "AdjustActivity";
    private CustomTitleBar mTitleBar;
    private RelativeLayout mBottomLayout;
    private RelativeLayout mHorizLayout;
    private RelativeLayout mVerticLayout;
    private RelativeLayout mRotateLayout;
    private RelativeLayout mResetLayout;
    private ImageView mAdjustFinish;
    private SingleClipFragment mClipFragment;
    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    NvsVideoFx mVideoFx;
    NvsVideoClip mVideoClip;
    ArrayList<ClipInfo> mClipArrayList;
    private int mCurClipIndex = 0;

    int mScaleX = 1;
    int mScaleY = 1;
    int mRotateAngle = 0;



    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mBottomLayout = (RelativeLayout)findViewById(R.id.bottomLayout);
        mHorizLayout = (RelativeLayout)findViewById(R.id.horizLayout);
        mVerticLayout = (RelativeLayout)findViewById(R.id.verticLayout);
        mRotateLayout = (RelativeLayout)findViewById(R.id.rotateLayout);
        mResetLayout = (RelativeLayout)findViewById(R.id.resetLayout);
        mAdjustFinish = (ImageView)findViewById(R.id.adjustFinish);
    }

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_adjust;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.adjust);
        mTitleBar.setBackImageVisible(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mClipArrayList =  BackupData.instance().cloneClipInfoData();
        mCurClipIndex = BackupData.instance().getClipIndex();
        if(mCurClipIndex < 0 || mCurClipIndex >= mClipArrayList.size())
            return;
        ClipInfo clipInfo = mClipArrayList.get(mCurClipIndex);
        int scaleX = clipInfo.getScaleX();
        int scaleY = clipInfo.getScaleY();
        if(scaleX >= -1)
            mScaleX = scaleX;
        if (scaleY >= -1)
            mScaleY = scaleY;
        mRotateAngle = clipInfo.getRotateAngle();
        mTimeline = TimelineUtil.createSingleClipTimeline(clipInfo,true);
        if(mTimeline == null)
            return;

        NvsVideoTrack videoTrack = mTimeline.getVideoTrackByIndex(0);
        if(videoTrack == null)
            return;
        mVideoClip = videoTrack.getClipByIndex(0);
        if(mVideoClip == null)
            return;

        initVideoFragment();
        adjustClip();
        mHorizLayout.setOnClickListener(this);
        mVerticLayout.setOnClickListener(this);
        mRotateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotateAngle = mVideoClip.getExtraVideoRotation();
                switch (rotateAngle){
                    case NvsVideoClip.ClIP_EXTRAVIDEOROTATION_0:
                        mRotateAngle = NvsVideoClip.ClIP_EXTRAVIDEOROTATION_90;
                        break;
                    case NvsVideoClip.ClIP_EXTRAVIDEOROTATION_90:
                        mRotateAngle = NvsVideoClip.ClIP_EXTRAVIDEOROTATION_180;
                        break;
                    case NvsVideoClip.ClIP_EXTRAVIDEOROTATION_180:
                        mRotateAngle = NvsVideoClip.ClIP_EXTRAVIDEOROTATION_270;
                        break;
                    case NvsVideoClip.ClIP_EXTRAVIDEOROTATION_270:
                        mRotateAngle = NvsVideoClip.ClIP_EXTRAVIDEOROTATION_0;
                        break;
                    default:
                        break;
                }
                rotateClip();
                mClipArrayList.get(mCurClipIndex).setRotateAngle(mRotateAngle);
                if(mClipFragment.getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_PLAYBACK) {
                    seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline));
                }
            }
        });
        mClipFragment.setOnScaleGesture(new SingleClipFragment.OnScaleGesture() {
            @Override
            public void onHorizScale(float scale) {
                NvsPanAndScan panAndScan = mVideoClip.getPanAndScan();
                float scan = (scale - 1.0f) * 2 + panAndScan.scan;
                Logger.e(TAG,"length = " + scan);
                if(scan > 1.0f)
                    scan = 1.0f;
                if(scan < 0.0f)
                    scan = 0.0f;
                mVideoClip.setPanAndScan(panAndScan.pan,scan);
                seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline));
            }

            @Override
            public void onVerticalTrans(float tranVal) {
                NvsPanAndScan panAndScan = mVideoClip.getPanAndScan();
                Logger.e(TAG,"tranVal = " + tranVal);
                float pan = tranVal + panAndScan.pan;
                Logger.e(TAG,"pan = " + pan);
                if(pan > 1.0f)
                    pan = 1.0f;
                if(pan < -1.0f)
                    pan = -1.0f;
                mVideoClip.setPanAndScan(pan,panAndScan.scan);
                seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline));
            }
        });
        mResetLayout.setOnClickListener(this);
        mAdjustFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.horizLayout:  //水平
                mScaleX = mScaleX > 0 ? -1 : 1;
                mVideoFx.setFloatVal("Scale X",mScaleX);
                mClipArrayList.get(mCurClipIndex).setScaleX(mScaleX);
                break;
            case R.id.verticLayout: //垂直
                mScaleY = mScaleY > 0 ? -1 : 1;
                mVideoFx.setFloatVal("Scale Y",mScaleY);
                mClipArrayList.get(mCurClipIndex).setScaleY(mScaleY);
                break;
            case R.id.resetLayout://复位
                mScaleX = 1;
                mScaleY = 1;
                mRotateAngle = 0;
                rotateClip();
                mVideoFx.setFloatVal("Scale X",mScaleX);
                mVideoFx.setFloatVal("Scale Y",mScaleY);
                mClipArrayList.get(mCurClipIndex).setScaleX(mScaleX);
                mClipArrayList.get(mCurClipIndex).setScaleY(mScaleY);
                mClipArrayList.get(mCurClipIndex).setRotateAngle(mRotateAngle);
                break;
            case R.id.adjustFinish:
                NvsPanAndScan panAndScan = mVideoClip.getPanAndScan();
                mClipArrayList.get(mCurClipIndex).setPan(panAndScan.pan);
                mClipArrayList.get(mCurClipIndex).setScan(panAndScan.scan);
                BackupData.instance().setClipInfoData(mClipArrayList);
                removeTimeline();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                AppManager.getInstance().finishActivity();
                break;
            default:
                break;
        }
        if(mClipFragment.getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_PLAYBACK)
            mClipFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
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

    private void rotateClip(){
        mVideoClip.setExtraVideoRotation(mRotateAngle);
    }
    private void adjustClip(){
        rotateClip();
        int fxCount = mVideoClip.getFxCount();
        for(int index = 0;index < fxCount;++index){
            NvsVideoFx videoFx = mVideoClip.getFxByIndex(index);
            if(videoFx == null)
                continue;
            if(videoFx.getBuiltinVideoFxName().compareTo("Transform 2D") == 0){
                mVideoFx = videoFx;
                break;
            }
        }
        if(mVideoFx == null)
            mVideoFx = mVideoClip.appendBuiltinFx("Transform 2D");
        if(mVideoFx == null)
            return;
        if(mScaleX >= -1)
            mVideoFx.setFloatVal("Scale X",mScaleX);
        if(mScaleY >= -1)
            mVideoFx.setFloatVal("Scale Y",mScaleY);
    }
    private void initVideoFragment() {
        mClipFragment = new SingleClipFragment();
        mClipFragment.setFragmentLoadFinisedListener(new SingleClipFragment.OnFragmentLoadFinisedListener() {
            @Override
            public void onLoadFinished() {
                seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline));
            }
        });
        mClipFragment.setTimeline(mTimeline);
        Bundle bundle = new Bundle();
        bundle.putInt("titleHeight",mTitleBar.getLayoutParams().height);
        bundle.putInt("bottomHeight",mBottomLayout.getLayoutParams().height);
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        bundle.putBoolean("isAddOnTouchEvent", true);
        mClipFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.spaceLayout, mClipFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mClipFragment);
    }
    private void seekTimeline(long timeStamp){
        mClipFragment.seekTimeline(timeStamp,0);
    }
}
