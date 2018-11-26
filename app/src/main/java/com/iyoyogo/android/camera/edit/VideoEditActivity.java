package com.iyoyogo.android.camera.edit;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.VideoFragment;
import com.iyoyogo.android.camera.data.AssetInfoDescription;
import com.iyoyogo.android.camera.data.BackupData;
import com.iyoyogo.android.camera.data.BitmapData;
import com.iyoyogo.android.camera.edit.Caption.CaptionActivity;
import com.iyoyogo.android.camera.edit.adapter.AssetRecyclerViewAdapter;
import com.iyoyogo.android.camera.edit.adapter.SpaceItemDecoration;
import com.iyoyogo.android.camera.edit.animatesticker.AnimateStickerActivity;
import com.iyoyogo.android.camera.edit.clipEdit.EditActivity;
import com.iyoyogo.android.camera.edit.filter.FilterActivity;
import com.iyoyogo.android.camera.edit.music.MusicActivity;
import com.iyoyogo.android.camera.edit.theme.ThemeActivity;
import com.iyoyogo.android.camera.edit.transition.TransitionActivity;
import com.iyoyogo.android.camera.edit.watermark.WaterMarkActivity;
import com.iyoyogo.android.camera.interfaces.OnItemClickListener;
import com.iyoyogo.android.camera.interfaces.OnTitleBarClickListener;
import com.iyoyogo.android.camera.record.RecordActivity;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.MediaScannerUtil;
import com.iyoyogo.android.camera.utils.ParameterSettingValues;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.ToastUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.dataInfo.CaptionInfo;
import com.iyoyogo.android.camera.utils.dataInfo.MusicInfo;
import com.iyoyogo.android.camera.utils.dataInfo.RecordAudioInfo;
import com.iyoyogo.android.camera.utils.dataInfo.StickerInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.dataInfo.TransitionInfo;
import com.iyoyogo.android.camera.utils.dataInfo.VideoClipFxInfo;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.meicam.sdk.NvsAudioTrack;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoTrack;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.iyoyogo.android.app.Constants.VIDEOVOLUME_MAXSEEKBAR_VALUE;
import static com.iyoyogo.android.app.Constants.VIDEOVOLUME_MAXVOLUMEVALUE;

public class VideoEditActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "VideoEditActivity";
    public static final int REQUESTRESULT_THEME = 1001;
    public static final int REQUESTRESULT_EDIT = 1002;
    public static final int REQUESTRESULT_FILTER = 1003;
    public static final int REQUESTRESULT_STICKER = 1004;
    public static final int REQUESTRESULT_CAPTION = 1005;
    public static final int REQUESTRESULT_TRANSITION = 1006;
    public static final int REQUESTRESULT_MUSIC = 1007;
    public static final int REQUESTRESULT_RECORD = 1008;
    public static final int REQUESTRESULT_WATERMARK = 1009;

    private CustomTitleBar mTitleBar;

    private RelativeLayout mBottomLayout;
    private RecyclerView mAssetRecycleView;
    private AssetRecyclerViewAdapter mAssetRecycleAdapter;
    private ArrayList<AssetInfoDescription> mArrayAssetInfo;
    private LinearLayout mVolumeUpLayout;
    private SeekBar mVideoVoiceSeekBar;
    private SeekBar mMusicVoiceSeekBar;
    private SeekBar mDubbingSeekBarSeekBar;
    private TextView mVideoVoiceSeekBarValue;
    private TextView mMusicVoiceSeekBarValue;
    private TextView mDubbingSeekBarSeekBarValue;
    private ImageView mSetVoiceFinish;

    private String mCompileVideoPath;
    private RelativeLayout mCompilePage;
    private ProgressBar mCompileProgressBar;
    private TextView mCompileProgressVal;
    private ImageView mCompileCancel;

    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    private NvsVideoTrack mVideoTrack;
    private NvsAudioTrack mMusicTrack;
    private NvsAudioTrack mRecordAudioTrack;



    private VideoFragment mVideoFragment;
    private boolean m_waitFlag = false;
    private boolean m_fromCapture = false;

    int[] videoEditImageId = {
            R.mipmap.icon_edit_theme,
            R.mipmap.icon_edit_edit,
            R.mipmap.icon_edit_filter,
            R.mipmap.icon_edit_sticker,
            R.mipmap.icon_edit_caption,
            R.mipmap.icon_watermark,
            R.mipmap.icon_edit_transition,
            R.mipmap.icon_edit_music,
            R.mipmap.icon_edit_voice
    };

    //


    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mAssetRecycleView = (RecyclerView) findViewById(R.id.assetRecycleList);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
        mVolumeUpLayout = (LinearLayout) findViewById(R.id.volumeUpLayout);
        mVideoVoiceSeekBar = (SeekBar) findViewById(R.id.videoVoiceSeekBar);
        mMusicVoiceSeekBar = (SeekBar) findViewById(R.id.musicVoiceSeekBar);
        mDubbingSeekBarSeekBar = (SeekBar) findViewById(R.id.dubbingSeekBar);
        mVideoVoiceSeekBarValue = (TextView) findViewById(R.id.videoVoiceSeekBarValue);
        mMusicVoiceSeekBarValue = (TextView) findViewById(R.id.musicVoiceSeekBarValue);
        mDubbingSeekBarSeekBarValue = (TextView) findViewById(R.id.dubbingSeekBarValue);
        mCompileProgressVal = (TextView) findViewById(R.id.compileProgressVal);
        mSetVoiceFinish = (ImageView) findViewById(R.id.finish);
        mCompilePage = (RelativeLayout) findViewById(R.id.compilePage);
        mCompileProgressBar = (ProgressBar) findViewById(R.id.compileProgressBar);
        mCompileProgressVal = (TextView) findViewById(R.id.compileProgressVal);
        mCompileCancel = (ImageView) findViewById(R.id.compileCancel);
        mCompileProgressBar.setMax(100);
    }

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_video_edit;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.videoEdit);
        mTitleBar.setTextRight(R.string.compile);
        mTitleBar.setTextRightVisible(View.VISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mTimeline = TimelineUtil.createTimeline();
        if (mTimeline == null)
            return;
        mVideoTrack = mTimeline.getVideoTrackByIndex(0);
        if (mVideoTrack == null)
            return;

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                m_fromCapture = bundle.getBoolean(Constants.START_ACTIVITY_FROM_CAPTURE);
            }
        }
        initVideoFragment();
        initAssetInfo();
        initAssetRecycleAdapter();
        initVoiceSeekBar();
        mTitleBar.setOnTitleBarClickListener(new OnTitleBarClickListener() {
            @Override
            public void OnBackImageClick() {
                removeTimeline();
                clearData();
            }

            @Override
            public void OnCenterTextClick() {

            }

            @Override
            public void OnRightTextClick() {
                videoCompile();
            }
        });
        mVideoVoiceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setVideoVoice(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMusicVoiceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setMusicVoice(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mDubbingSeekBarSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setDubbingVoice(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSetVoiceFinish.setOnClickListener(this);
        mCompilePage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mStreamingContext.setCompileCallback(new NvsStreamingContext.CompileCallback() {
            @Override
            public void onCompileProgress(NvsTimeline nvsTimeline, int i) {
                updateVideoCompileProgress(i);
            }

            @Override
            public void onCompileFinished(NvsTimeline nvsTimeline) {
                mCompilePage.setVisibility(View.GONE);
                mStreamingContext.setCompileConfigurations(null);

                MediaScannerUtil.scanFile(mCompileVideoPath, "video/mp4");
            }

            @Override
            public void onCompileFailed(NvsTimeline nvsTimeline) {
                mCompilePage.setVisibility(View.GONE);
                Util.showDialog(VideoEditActivity.this, "生成失败", "请检查手机存储空间");
            }
        });
        mStreamingContext.setCompileCallback2(new NvsStreamingContext.CompileCallback2() {
            @Override
            public void onCompileCompleted(NvsTimeline nvsTimeline, boolean isCanceled) {
                if (!isCanceled) {
                    mCompilePage.setVisibility(View.GONE);
                    ToastUtil.showToast(VideoEditActivity.this, "生成成功！\n 保存路径: " + mCompileVideoPath);
                }
            }
        });
        mCompileCancel.setOnClickListener(this);
        mVideoFragment.setVideoVolumeListener(new VideoFragment.VideoVolumeListener() {
            @Override
            public void onVideoVolume() {
                mVolumeUpLayout.setVisibility(View.VISIBLE);
            }
        });
        mVolumeUpLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        m_waitFlag = false;
        mMusicTrack = mTimeline.getAudioTrackByIndex(0);
        mRecordAudioTrack = mTimeline.getAudioTrackByIndex(1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeTimeline();
        clearData();
        AppManager.getInstance().finishActivity();
    }

    //清空数据
    private void clearData() {
        TimelineData.instance().clear();
        BackupData.instance().clear();
        BitmapData.instance().clear();
    }

    private void removeTimeline() {
        TimelineUtil.removeTimeline(mTimeline);
        mTimeline = null;
    }

    private void initVoiceSeekBar() {
        mVideoVoiceSeekBar.setMax(VIDEOVOLUME_MAXSEEKBAR_VALUE);
        mMusicVoiceSeekBar.setMax(VIDEOVOLUME_MAXSEEKBAR_VALUE);
        mDubbingSeekBarSeekBar.setMax(VIDEOVOLUME_MAXSEEKBAR_VALUE);
        if(mVideoTrack == null)
            return;
        int volumeVal = (int)Math.floor(mVideoTrack.getVolumeGain().leftVolume / VIDEOVOLUME_MAXVOLUMEVALUE * VIDEOVOLUME_MAXSEEKBAR_VALUE + 0.5D);
        updateVideoVoiceSeekBar(volumeVal);
        updateMusicVoiceSeekBar(volumeVal);
        updateDubbingVoiceSeekBar(volumeVal);
    }

    private void updateVideoVoiceSeekBar(int volumeVal){
        mVideoVoiceSeekBar.setProgress(volumeVal);
        mVideoVoiceSeekBarValue.setText(String.valueOf(volumeVal));
    }
    private void updateMusicVoiceSeekBar(int volumeVal){
        mMusicVoiceSeekBar.setProgress(volumeVal);
        mMusicVoiceSeekBarValue.setText(String.valueOf(volumeVal));
    }
    private void updateDubbingVoiceSeekBar(int volumeVal){
        mDubbingSeekBarSeekBar.setProgress(volumeVal);
        mDubbingSeekBarSeekBarValue.setText(String.valueOf(volumeVal));
    }
    private void setVideoVoice(int voiceVal) {
        if(mVideoTrack == null)
            return;
        updateVideoVoiceSeekBar(voiceVal);
        float volumeVal = voiceVal* VIDEOVOLUME_MAXVOLUMEVALUE / VIDEOVOLUME_MAXSEEKBAR_VALUE;
        mVideoTrack.setVolumeGain(volumeVal, volumeVal);
        TimelineData.instance().setOriginVideoVolume(volumeVal);
    }

    private void setMusicVoice(int voiceVal) {
        if (mMusicTrack == null)
            return;
        updateMusicVoiceSeekBar(voiceVal);
        float volumeVal = voiceVal * VIDEOVOLUME_MAXVOLUMEVALUE / VIDEOVOLUME_MAXSEEKBAR_VALUE;
        mMusicTrack.setVolumeGain(volumeVal, volumeVal);
        TimelineData.instance().setMusicVolume(volumeVal);
    }

    private void setDubbingVoice(int voiceVal) {
        if (mRecordAudioTrack == null)
            return;
        updateDubbingVoiceSeekBar(voiceVal);
        float volumeVal = voiceVal * VIDEOVOLUME_MAXVOLUMEVALUE / VIDEOVOLUME_MAXSEEKBAR_VALUE;
        mRecordAudioTrack.setVolumeGain(volumeVal, volumeVal);
        TimelineData.instance().setRecordVolume(volumeVal);
    }

    private void initVideoFragment() {
        mVideoFragment = new VideoFragment();
        mVideoFragment.setFragmentLoadFinisedListener(new VideoFragment.OnFragmentLoadFinisedListener() {
            @Override
            public void onLoadFinished() {
                mVideoFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline), 0);
            }
        });
        mVideoFragment.setTimeline(mTimeline);
        mVideoFragment.setAutoPlay(true);
        Bundle bundle = new Bundle();
        bundle.putInt("titleHeight", mTitleBar.getLayoutParams().height);
        bundle.putInt("bottomHeight", mBottomLayout.getLayoutParams().height);
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        bundle.putBoolean("playBarVisible", true);
        bundle.putBoolean("voiceButtonVisible", true);
        mVideoFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_layout, mVideoFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mVideoFragment);
    }

    private void initAssetInfo() {
        mArrayAssetInfo = new ArrayList<>();
        String[] assetName = getResources().getStringArray(R.array.videoEdit);
        for (int i = 0; i < assetName.length; i++) {
            mArrayAssetInfo.add(new AssetInfoDescription(assetName[i], videoEditImageId[i]));
        }
    }

    private void initAssetRecycleAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(VideoEditActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mAssetRecycleView.setLayoutManager(layoutManager);
        mAssetRecycleAdapter = new AssetRecyclerViewAdapter(VideoEditActivity.this);
        mAssetRecycleAdapter.updateData(mArrayAssetInfo);
        mAssetRecycleView.setAdapter(mAssetRecycleAdapter);
        mAssetRecycleView.addItemDecoration(new SpaceItemDecoration(27, 27));
        mAssetRecycleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if (m_waitFlag)
                    return;
                mStreamingContext.stop();
                String tag = (String) view.getTag();
                if (tag.equals(getStringResourse(R.string.edit_theme))) {
                    onItemClickToActivity(ThemeActivity.class, VideoEditActivity.REQUESTRESULT_THEME);
                } else if (tag.equals(getStringResourse(R.string.edit_edit))) {
                    onItemClickToActivity(EditActivity.class, VideoEditActivity.REQUESTRESULT_EDIT);
                } else if (tag.equals(getStringResourse(R.string.edit_filter))) {

                    onItemClickToActivity(FilterActivity.class, VideoEditActivity.REQUESTRESULT_FILTER);
                } else if (tag.equals(getStringResourse(R.string.edit_tags))) {
                    onItemClickToActivity(AnimateStickerActivity.class, VideoEditActivity.REQUESTRESULT_STICKER);
                } else if (tag.equals(getStringResourse(R.string.edit_caption))) {
                    onItemClickToActivity(CaptionActivity.class, VideoEditActivity.REQUESTRESULT_CAPTION);
                } else if (tag.equals(getStringResourse(R.string.edit_watermark))) {
                    onItemClickToActivity(WaterMarkActivity.class, VideoEditActivity.REQUESTRESULT_WATERMARK);
                } else if (tag.equals(getStringResourse(R.string.edit_cutto))) {
                    if (mTimeline != null) {
                        NvsVideoTrack videoTrack = mTimeline.getVideoTrackByIndex(0);
                        if (videoTrack != null) {
                            int clipCount = videoTrack.getClipCount();
                            if (clipCount <= 1) {
                                Util.showDialog(VideoEditActivity.this, "提示", "两个以上素材可添加转场");
                                return;
                            }
                        }
                    }
                    onItemClickToActivity(TransitionActivity.class, VideoEditActivity.REQUESTRESULT_TRANSITION);
                } else if (tag.equals(getStringResourse(R.string.edit_music))) {
                    onItemClickToActivity(MusicActivity.class, VideoEditActivity.REQUESTRESULT_MUSIC);
                } else if (tag.equals(getStringResourse(R.string.edit_dub))) {
                    onItemClickToActivity(RecordActivity.class, VideoEditActivity.REQUESTRESULT_RECORD);
                } else {
                    Util.showDialog(VideoEditActivity.this, "提示", "敬请期待！", "可移步官网联系商务人员");
                }
            }
        });
    }

    private void onItemClickToActivity(Class<? extends Activity> cls, int requstcode) {
        m_waitFlag = true;
        AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), cls, null, requstcode);
    }

    private String getStringResourse(int id) {
        return getApplicationContext().getResources().getString(id);
    }

    private String getVideoStoragePath() {
        File compileDir = new File(Environment.getExternalStorageDirectory(), "NvStreamingSdk" + File.separator + "Compile");
        if (!compileDir.exists() && !compileDir.mkdirs()) {
            Log.d(TAG, "Failed to make Compile directory");
            return null;
        }
        return compileDir.toString();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.finish:
                mVolumeUpLayout.setVisibility(View.GONE);
                break;
            case R.id.compileCancel:
                if (mStreamingContext != null) {
                    mStreamingContext.stop();
                    mCompilePage.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (data == null)
            return;
        long cur_time = mStreamingContext.getTimelineCurrentPosition(mTimeline);
        switch (requestCode) {
            case REQUESTRESULT_THEME:
                String themeId = TimelineData.instance().getThemeData();
                TimelineUtil.applyTheme(mTimeline, themeId);
                cur_time = 0;
                break;
            case REQUESTRESULT_EDIT:
                TimelineUtil.reBuildVideoTrack(mTimeline);
                cur_time = 0;
                break;
            case REQUESTRESULT_FILTER:
                VideoClipFxInfo videoClipFxInfo = TimelineData.instance().getVideoClipFxData();
                TimelineUtil.buildTimelineFilter(mTimeline, videoClipFxInfo);
                break;
            case REQUESTRESULT_STICKER:
                ArrayList<StickerInfo> stickerArray = TimelineData.instance().getStickerData();
                TimelineUtil.setSticker(mTimeline, stickerArray);
                break;
            case REQUESTRESULT_CAPTION:
                ArrayList<CaptionInfo> captionArray = TimelineData.instance().getCaptionData();
                TimelineUtil.setCaption(mTimeline, captionArray);
                break;
            case REQUESTRESULT_TRANSITION:
                TransitionInfo transitionInfo = TimelineData.instance().getTransitionData();
                TimelineUtil.setTransition(mTimeline, transitionInfo);
                break;
            case REQUESTRESULT_MUSIC:
                List<MusicInfo> musicInfos = TimelineData.instance().getMusicData();
                TimelineUtil.buildTimelineMusic(mTimeline, musicInfos);
                break;
            case REQUESTRESULT_RECORD:
                Logger.e(TAG,"录音界面");
                ArrayList<RecordAudioInfo> audioInfos = TimelineData.instance().getRecordAudioData();
                TimelineUtil.buildTimelineRecordAudio(mTimeline, audioInfos);
                break;
            case REQUESTRESULT_WATERMARK:
                Logger.e(TAG,"水印界面");
                boolean cleanWaterMark = data.getBooleanExtra(WaterMarkActivity.WATER_CLEAN,true);
                if (cleanWaterMark){
                    mTimeline.deleteWatermark();
                }else {
                    String picturePath = data.getStringExtra(WaterMarkActivity.WATER_PICTURE_PATH);
                    int pictureW = data.getIntExtra(WaterMarkActivity.WATER_PICTURE_W,0);
                    int pictureH = data.getIntExtra(WaterMarkActivity.WATER_PICTURE_H,0);
                    int marginX = data.getIntExtra(WaterMarkActivity.WATER_MARGIN_X,0);
                    int marginY = data.getIntExtra(WaterMarkActivity.WATER_MARGIN_Y,0);
                    mTimeline.addWatermark(picturePath, pictureW,pictureH,1, NvsTimeline.NvsTimelineWatermarkPosition_TopRight , marginX, marginY);
                }
                break;
            default:
                break;
        }
        mVideoFragment.seekTimeline(cur_time, 0);
        mVideoFragment.updateTotalDuarationText();
        mVideoFragment.updateSeekBarMaxValue();
    }

    private void updateVideoCompileProgress(int progress){
        mCompileProgressBar.setProgress(progress);
        String strProgress = String.valueOf(progress) + "%";
        mCompileProgressVal.setText(strProgress);
    }
    private void videoCompile() {
        String compilePath = getVideoStoragePath();
        if (compilePath == null)
            return;
        updateVideoCompileProgress(0);
        long currentMilis = System.currentTimeMillis();
        String videoName = "meicam_" + String.valueOf(currentMilis) + ".mp4";
        compilePath += "/";
        compilePath += videoName;
        mCompileVideoPath = compilePath;
        int compileResolutionGrade = ParameterSettingValues.instance().getCompileResolutionGrade();
        double bitrate = ParameterSettingValues.instance().getCompileBitrate();
        if (bitrate != 0) {
            Hashtable<String, Object> config = new Hashtable<>();
            config.put(NvsStreamingContext.COMPILE_BITRATE, bitrate * 1000000);
            mStreamingContext.setCompileConfigurations(config);
        }
        int encoderFlag = 0;
        if (ParameterSettingValues.instance().disableDeviceEncorder()) {
            encoderFlag = NvsStreamingContext.STREAMING_ENGINE_COMPILE_FLAG_DISABLE_HARDWARE_ENCODER;
        }

        mStreamingContext.compileTimeline(mTimeline, 0, mTimeline.getDuration(), mCompileVideoPath, compileResolutionGrade, NvsStreamingContext.COMPILE_BITRATE_GRADE_HIGH, encoderFlag);
        mCompilePage.setVisibility(View.VISIBLE);
    }
}


