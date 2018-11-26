package com.iyoyogo.android.camera.edit.clipEdit.correctionColor;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
import com.meicam.sdk.NvsVideoFx;
import com.meicam.sdk.NvsVideoTrack;

import java.util.ArrayList;

public class CorrectionColorActivity extends BaseActivity implements View.OnClickListener {
    private CustomTitleBar mTitleBar;
    private RelativeLayout mBottomLayout;
    private SeekBar mBrightnessSeekBar;
    private SeekBar mContrastSeekBar;
    private SeekBar mSaturationSeekBar;
    private TextView mBrightnessSeekBarValue;
    private TextView mContrastSeekBarValue;
    private TextView mSaturationSeekBarValue;
    private ImageView mCorrectionColorFinish;
    private SingleClipFragment mClipFragment;
    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    NvsVideoFx mVideoFx;
    ArrayList<ClipInfo> mClipArrayList;
    private int mCurClipIndex = 0;
    private static final float floatZero = 0.000001f;
    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mBottomLayout = (RelativeLayout)findViewById(R.id.bottomLayout);
        mBrightnessSeekBar = (SeekBar)findViewById(R.id.brightnessSeekBar);
        mBrightnessSeekBar.setMax(100);
        mContrastSeekBar = (SeekBar)findViewById(R.id.contrastSeekBar);
        mContrastSeekBar.setMax(100);
        mSaturationSeekBar = (SeekBar)findViewById(R.id.saturationSeekBar);
        mSaturationSeekBar.setMax(100);
        mCorrectionColorFinish = (ImageView)findViewById(R.id.correctionColorFinish);
        mBrightnessSeekBarValue = (TextView)findViewById(R.id.brightnessSeekBarValue);
        mContrastSeekBarValue = (TextView)findViewById(R.id.contrastSeekBarValue);
        mSaturationSeekBarValue = (TextView)findViewById(R.id.saturationSeekBarValue);
    }

    private void updateBrightnessSeekBarValue(int value){
        mBrightnessSeekBar.setProgress(value);
        mBrightnessSeekBarValue.setText(String.valueOf(value));
    }
    private void updateContrastSeekBarValue(int value){
        mContrastSeekBar.setProgress(value);
        mContrastSeekBarValue.setText(String.valueOf(value));
    }
    private void updateSaturationSeekBarValue(int value){
        mSaturationSeekBar.setProgress(value);
        mSaturationSeekBarValue.setText(String.valueOf(value));
    }

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_correction_color;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.correctionColor);
        mTitleBar.setBackImageVisible(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mClipArrayList =  BackupData.instance().cloneClipInfoData();
        mCurClipIndex = BackupData.instance().getClipIndex();
        mCurClipIndex = BackupData.instance().getClipIndex();
        if(mCurClipIndex < 0 || mCurClipIndex >= mClipArrayList.size())
            return;
        ClipInfo clipInfo = mClipArrayList.get(mCurClipIndex);
        if(clipInfo == null)
            return;
        mTimeline = TimelineUtil.createSingleClipTimeline(clipInfo,true);
        if(mTimeline == null)
            return;

        int brightnessVal = getCurProgress(clipInfo.getBrightnessVal());
        int contrastVal = getCurProgress(clipInfo.getContrastVal());
        int saturationVal = getCurProgress(clipInfo.getSaturationVal());
        updateBrightnessSeekBarValue(brightnessVal);
        updateContrastSeekBarValue(contrastVal);
        updateSaturationSeekBarValue(saturationVal);
        updateClipColorVal(clipInfo);
        initVideoFragment();
    }

    private float getFloatColorVal(int progress){
        return progress < 50 ? progress / 50.f : progress * 9 / 50.0f - 8;
    }
    private int getCurProgress(float colVal){
        int curProgress = 50;
        if(colVal < 0)
            return curProgress;
        for (int progress = 0;progress < 100;++progress){
            float result =  progress < 50 ? progress / 50.0f : progress * 9 / 50.0f - 8;

            result = result - colVal;
            if( result >= -floatZero && result<= floatZero){
                curProgress = progress;
                break;
            }
        }
        return curProgress;
    }
    private void updateClipColorVal(ClipInfo clipInfo){
        NvsVideoTrack videoTrack = mTimeline.getVideoTrackByIndex(0);
        if(videoTrack == null)
            return;
        NvsVideoClip videoClip = videoTrack.getClipByIndex(0);
        if(videoClip == null)
            return;
        int fxCount = videoClip.getFxCount();
        for(int index = 0;index < fxCount;++index){
            NvsVideoFx videoFx = videoClip.getFxByIndex(index);
            if(videoFx == null)
                continue;

            String fxName = videoFx.getBuiltinVideoFxName();
            if(!TextUtils.isEmpty(fxName) && fxName.compareTo("Color Property") == 0){
                mVideoFx = videoFx;
                break;
            }
        }
        if(mVideoFx == null)
            mVideoFx = videoClip.appendBuiltinFx("Color Property");
        if(mVideoFx == null)
            return;

        float brightVal = clipInfo.getBrightnessVal();
        float contrastVal = clipInfo.getContrastVal();
        float saturationVal = clipInfo.getSaturationVal();
        if(brightVal >= 0 || contrastVal >= 0 || saturationVal >= 0){
            if(brightVal >= 0)
               mVideoFx.setFloatVal("Brightness",brightVal);
            if(contrastVal >= 0)
                mVideoFx.setFloatVal("Contrast",contrastVal);
            if(saturationVal >= 0)
                mVideoFx.setFloatVal("Saturation",saturationVal);
        }
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
        mCorrectionColorFinish.setOnClickListener(this);
        mBrightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    if(mVideoFx != null){
                        updateBrightnessSeekBarValue(progress);
                        float brightnessVal = getFloatColorVal(progress);
                        mVideoFx.setFloatVal("Brightness",brightnessVal);
                        mClipArrayList.get(mCurClipIndex).setBrightnessVal(brightnessVal);
                        if(mClipFragment.getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_PLAYBACK)
                            mClipFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mContrastSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    if(mVideoFx != null){
                        updateContrastSeekBarValue(progress);
                        float contrastVal = getFloatColorVal(progress);
                        mVideoFx.setFloatVal("Contrast",contrastVal);
                        mClipArrayList.get(mCurClipIndex).setContrastVal(contrastVal);
                        if(mClipFragment.getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_PLAYBACK)
                            mClipFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSaturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    if(mVideoFx != null){
                        updateSaturationSeekBarValue(progress);
                        float saturationVal = getFloatColorVal(progress);
                        mVideoFx.setFloatVal("Saturation",saturationVal);
                        mClipArrayList.get(mCurClipIndex).setSaturationVal(saturationVal);
                        if(mClipFragment.getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_PLAYBACK)
                            mClipFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
                    }
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
            case R.id.correctionColorFinish:
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

    private void initVideoFragment() {
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
