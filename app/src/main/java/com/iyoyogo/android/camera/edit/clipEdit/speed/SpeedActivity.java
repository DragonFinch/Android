package com.iyoyogo.android.camera.edit.clipEdit.speed;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.iyoyogo.android.R;

import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.BackupData;
import com.iyoyogo.android.camera.edit.clipEdit.SingleClipFragment;
import com.iyoyogo.android.camera.interfaces.OnTitleBarClickListener;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoTrack;

import java.util.ArrayList;

public class SpeedActivity extends BaseActivity implements View.OnClickListener {
    private CustomTitleBar mTitleBar;
    private RelativeLayout mBottomLayout;
    private CustomSpeedSeekBar mChangeSpeedSeekBar;
    private ImageView mSpeedFinish;
    private SingleClipFragment mClipFragment;

    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    ArrayList<ClipInfo> mClipArrayList;
    private int mCurClipIndex = 0;
    private static final float floatZero = 0.000001f;

    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mChangeSpeedSeekBar = (CustomSpeedSeekBar)findViewById(R.id.changeSpeed_seekBar);
        mBottomLayout = (RelativeLayout)findViewById(R.id.bottomLayout);
        mSpeedFinish = (ImageView)findViewById(R.id.speedFinish);
    }

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_speed;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.speed);
        mTitleBar.setBackImageVisible(View.GONE);
    }
    private void updateSpeedSeekBar(float speedVal){
        int progressVal = 3;
        if(speedVal >= 0){
            for (int progress = 0;progress < 6;++progress){
                float newSpeed = (float) Math.pow(2, progress - 3);
                float result = newSpeed - speedVal;
                if( result >= -floatZero && result<= floatZero){
                    progressVal = progress;
                    break;
                }
            }
        }
        mChangeSpeedSeekBar.setProgress(progressVal);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mClipArrayList =  BackupData.instance().cloneClipInfoData();
        mCurClipIndex = BackupData.instance().getClipIndex();
        if(mCurClipIndex < 0 || mCurClipIndex >= mClipArrayList.size())
            return;
        ClipInfo clipInfo = mClipArrayList.get(mCurClipIndex);
        if(clipInfo == null)
            return;
        mTimeline = TimelineUtil.createSingleClipTimeline(clipInfo,true);
        if(mTimeline == null)
            return;
        initClipFragment();
        updateSpeedSeekBar(clipInfo.getSpeed());
        mTitleBar.setOnTitleBarClickListener(new OnTitleBarClickListener() {
            @Override
            public void OnBackImageClick() {
                removeTimeline();
            }

            @Override
            public void OnCenterTextClick() {

            }

            @Override
            public void OnRightTextClick() {

            }
        });
        mSpeedFinish.setOnClickListener(this);
        mChangeSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    double newSpeed = Math.pow(2, progress - 3);
                    NvsVideoTrack videoTrack = mTimeline.getVideoTrackByIndex(0);
                    if(videoTrack == null)
                        return;
                    NvsVideoClip videoClip = videoTrack.getClipByIndex(0);
                    if(videoClip == null)
                        return;
                    videoClip.changeSpeed(newSpeed);
                    mClipArrayList.get(mCurClipIndex).setSpeed((float)newSpeed);
                    mClipFragment.updateCurPlayTime(0);
                    mClipFragment.updateTotalDuration();
                    mClipFragment.seekTimeline(0, NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.speedFinish:
                BackupData.instance().setClipInfoData(mClipArrayList);
                removeTimeline();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                AppManager.getInstance().finishActivity();
                break;
            default:
                break;
        }
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

    private void initClipFragment() {
        mClipFragment = new SingleClipFragment();
        mClipFragment.setFragmentLoadFinisedListener(new SingleClipFragment.OnFragmentLoadFinisedListener() {
            @Override
            public void onLoadFinished() {
                mClipFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline), NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
            }
        });
        mClipFragment.setTimeline(mTimeline);
        Bundle bundle = new Bundle();
        bundle.putInt("titleHeight",mTitleBar.getLayoutParams().height);
        bundle.putInt("bottomHeight",mBottomLayout.getLayoutParams().height);
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        mClipFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.spaceLayout, mClipFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mClipFragment);
    }
}
