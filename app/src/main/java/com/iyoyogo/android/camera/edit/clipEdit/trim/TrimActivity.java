package com.iyoyogo.android.camera.edit.clipEdit.trim;
//com.meishe.sdkdemo.edit.clipEdit.tri

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.BackupData;
import com.iyoyogo.android.camera.data.BitmapData;
import com.iyoyogo.android.camera.edit.clipEdit.SingleClipFragment;
import com.iyoyogo.android.camera.interfaces.OnTitleBarClickListener;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.DensityUtil;
import com.iyoyogo.android.camera.utils.ScreenUtils;
import com.iyoyogo.android.camera.utils.TimeFormatUtil;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.ui.common.EmptyActivity;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.iyoyogo.android.widget.NvsTimelineEditor;
import com.iyoyogo.android.widget.NvsTimelineTimeSpanExt;
import com.meicam.sdk.NvsMultiThumbnailSequenceView;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoTrack;


import java.util.ArrayList;

public class TrimActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "NvsTimelineTimeSpanExt";
    private CustomTitleBar mTitleBar;
    private RelativeLayout mBottomLayout;
    private TextView mTrimDurationVal;
    private NvsTimelineEditor mTimelineEditor;
    private ImageView mTrimFinish;
    private long mTrimInPoint = 0;
    private long mTrimOutPoint = 0;
    private SingleClipFragment mClipFragment;
    NvsTimelineTimeSpanExt mTimlineTimeSpanExt;
    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    ArrayList<ClipInfo> mClipArrayList;
    private int mCurClipIndex = 0;


    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
        mTrimDurationVal = (TextView) findViewById(R.id.trimDurationVal);
        mTimelineEditor = (NvsTimelineEditor) findViewById(R.id.timelineEditor);
        mTrimFinish = (ImageView) findViewById(R.id.trimFinish);
    }

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_trim;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    public void initTitle() {
        mTitleBar.setTextCenter(R.string.trim);
        mTitleBar.setBackImageVisible(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mClipArrayList = BackupData.instance().cloneClipInfoData();
        mCurClipIndex = BackupData.instance().getClipIndex();
        if (mCurClipIndex < 0 || mCurClipIndex >= mClipArrayList.size())
            return;
        mTimeline = TimelineUtil.createSingleClipTimeline(mClipArrayList.get(mCurClipIndex), false);
        if (mTimeline == null)
            return;
        updateClipInfo();
        initVideoFragment();
        initMultiSequence();
        mTrimFinish.setOnClickListener(this);
        if (mTimlineTimeSpanExt != null) {
            mTimlineTimeSpanExt.setOnChangeListener(new NvsTimelineTimeSpanExt.OnTrimInChangeListener() {
                @Override
                public void onChange(long timeStamp, boolean isDragEnd) {
                    mTrimInPoint = timeStamp;
                    long totalDuration = mTrimOutPoint - mTrimInPoint;
                    setTrimDurationText(totalDuration);
                    float speed = mClipArrayList.get(mCurClipIndex).getSpeed();
                    speed = speed <= 0 ? 1.0f : speed;
                    long newTrimIn = (long) (timeStamp * speed);
                    mClipArrayList.get(mCurClipIndex).changeTrimIn(newTrimIn);
                    if (isDragEnd) {
                        mClipFragment.playVideo(mTrimInPoint, mTrimOutPoint);
                    } else {
                        mClipFragment.seekTimeline(timeStamp, NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
                    }

                }
            });
            mTimlineTimeSpanExt.setOnChangeListener(new NvsTimelineTimeSpanExt.OnTrimOutChangeListener() {
                @Override
                public void onChange(long timeStamp, boolean isDragEnd) {
                    mTrimOutPoint = timeStamp;
                    long totalDuration = mTrimOutPoint - mTrimInPoint;
                    setTrimDurationText(totalDuration);
                    float speed = mClipArrayList.get(mCurClipIndex).getSpeed();
                    speed = speed <= 0 ? 1.0f : speed;
                    long newTrimOut = (long) (timeStamp * speed);
                    mClipArrayList.get(mCurClipIndex).changeTrimOut(newTrimOut);
                    if (isDragEnd) {
                        mClipFragment.playVideo(mTrimInPoint, mTrimOutPoint);
                    } else {
                        mClipFragment.seekTimeline(timeStamp, NvsStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
                    }
                }
            });
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

        mClipFragment.setVideoFragmentCallBack(new SingleClipFragment.VideoFragmentListener() {
            @Override
            public void playBackEOF(NvsTimeline timeline) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mClipFragment.playVideo(mTrimInPoint, mTrimOutPoint);
                    }
                });
            }

            @Override
            public void playStopped(NvsTimeline timeline) {

            }

            @Override
            public void playbackTimelinePosition(NvsTimeline timeline, long stamp) {

            }

            @Override
            public void streamingEngineStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.trimFinish:
                Bitmap bitmap = Util.getBitmapFromClipInfo(this, mClipArrayList.get(mCurClipIndex));
                BitmapData.instance().replaceBitmap(mCurClipIndex, bitmap);
                BackupData.instance().setClipInfoData(mClipArrayList);
                removeTimeline();
                Intent intent = new Intent(TrimActivity.this, EmptyActivity.class);
                startActivity(intent);
                finish();
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

    private void removeTimeline() {
        mClipFragment.stopEngine();
        TimelineUtil.removeTimeline(mTimeline);
        mTimeline = null;
    }

    private void updateClipInfo() {
        NvsVideoTrack videoTrack = mTimeline.getVideoTrackByIndex(0);
        if (videoTrack == null)
            return;
        NvsVideoClip videoClip = videoTrack.getClipByIndex(0);
        if (videoClip == null)
            return;
        long trimIn = mClipArrayList.get(mCurClipIndex).getTrimIn();
        if (trimIn < 0)
            mClipArrayList.get(mCurClipIndex).changeTrimIn(videoClip.getTrimIn());
        long trimOut = mClipArrayList.get(mCurClipIndex).getTrimOut();
        if (trimOut < 0)
            mClipArrayList.get(mCurClipIndex).changeTrimOut(videoClip.getTrimOut());
    }

    private void initMultiSequence() {
        long duration = mTimeline.getDuration();
        ArrayList<NvsMultiThumbnailSequenceView.ThumbnailSequenceDesc> sequenceDescsArray = new ArrayList<>();
        NvsMultiThumbnailSequenceView.ThumbnailSequenceDesc sequenceDescs = new NvsMultiThumbnailSequenceView.ThumbnailSequenceDesc();
        sequenceDescs.mediaFilePath = mClipArrayList.get(mCurClipIndex).getFilePath();
        sequenceDescs.trimIn = 0;
        sequenceDescs.trimOut = duration;
        sequenceDescs.inPoint = 0;
        sequenceDescs.outPoint = duration;
        sequenceDescs.stillImageHint = false;
        sequenceDescsArray.add(sequenceDescs);
        double pixelPerMicrosecond = getPixelMicrosecond(duration);
        mTimelineEditor.setPixelPerMicrosecond(pixelPerMicrosecond);
        int sequenceLeftPadding = DensityUtil.dip2px(this, 13);
        mTimelineEditor.setSequencLeftPadding(sequenceLeftPadding);
        mTimelineEditor.setSequencRightPadding(sequenceLeftPadding);
        mTimelineEditor.setTimeSpanLeftPadding(sequenceLeftPadding);
        mTimelineEditor.initTimelineEditor(sequenceDescsArray, duration);
        mTimelineEditor.getMultiThumbnailSequenceView().getLayoutParams().height = DensityUtil.dip2px(this, 64);
        //warning: 使用addTimeSpanExt之前必须设置setTimeSpanType()
        mTimelineEditor.setTimeSpanType("NvsTimelineTimeSpanExt");
        float speed = mClipArrayList.get(mCurClipIndex).getSpeed();
        speed = speed <= 0 ? 1.0f : speed;
        mTrimInPoint = (long) (mClipArrayList.get(mCurClipIndex).getTrimIn() / speed);
        mTrimOutPoint = (long) (mClipArrayList.get(mCurClipIndex).getTrimOut() / speed);
        mTimlineTimeSpanExt = mTimelineEditor.addTimeSpanExt(mTrimInPoint, mTrimOutPoint);
        setTrimDurationText(mTrimOutPoint - mTrimInPoint);
    }

    private void setTrimDurationText(long duration) {
        String totalStr = "裁剪后总时长为" + TimeFormatUtil.formatUsToString1(duration);
        mTrimDurationVal.setText(totalStr);
    }

    private double getPixelMicrosecond(long duration) {
        int width = ScreenUtils.getScreenWidth(TrimActivity.this);
        int leftPadding = DensityUtil.dip2px(this, 13);
        int sequenceWidth = width - 2 * leftPadding;
        double pixelMicrosecond = sequenceWidth / (double) duration;
        return pixelMicrosecond;
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
        mClipFragment.setIsTrimActivity(true);
        Bundle bundle = new Bundle();
        bundle.putInt("titleHeight", mTitleBar.getLayoutParams().height);
        bundle.putInt("bottomHeight", mBottomLayout.getLayoutParams().height);
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        mClipFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.spaceLayout, mClipFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mClipFragment);
    }
}
