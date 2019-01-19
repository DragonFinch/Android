package com.iyoyogo.android.ui.home;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.VideoFilterListAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.utils.MediaScannerUtil;
import com.iyoyogo.android.camera.utils.ParameterSettingValues;
import com.iyoyogo.android.camera.utils.PathUtils;
import com.iyoyogo.android.camera.utils.ScreenUtils;
import com.iyoyogo.android.camera.utils.TimeFormatUtil;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.camera.utils.dataInfo.VideoClipFxInfo;
import com.iyoyogo.android.camera.utils.permission.PermissionsActivity;
import com.iyoyogo.android.camera.utils.permission.PermissionsChecker;
import com.iyoyogo.android.utils.AssetFxUtil;
import com.iyoyogo.android.utils.util.UiUtils;
import com.meicam.sdk.NvsCaptureVideoFx;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsSize;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsVideoFrameRetriever;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoTakePhotoActivity extends BaseActivity implements NvsStreamingContext.CaptureDeviceCallback, NvsStreamingContext.CaptureRecordingDurationCallback, NvsStreamingContext.CaptureRecordingStartedCallback, BaseQuickAdapter.OnItemClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "GoTakePhotoActivity";

    @BindView(R.id.liveWindow)
    NvsLiveWindow  mLiveWindow;
    @BindView(R.id.view_optiong_bg)
    View           mViewOptiongBg;
    @BindView(R.id.iv_photo_album)
    ImageView      mIvPhotoAlbum;
    @BindView(R.id.iv_take)
    ImageView      mIvTake;
    @BindView(R.id.iv_filter)
    ImageView      mIvFilter;
    @BindView(R.id.tv_filter)
    TextView       mTvFilter;
    @BindView(R.id.ll_filter)
    LinearLayout   mLlFilter;
    @BindView(R.id.view_left)
    View           mViewLeft;
    @BindView(R.id.tv_type_picture)
    TextView       mTvTypePicture;
    @BindView(R.id.tv_type_video)
    TextView       mTvTypeVideo;
    @BindView(R.id.ll_record_type_layout)
    LinearLayout   mLlRecordTypeLayout;
    @BindView(R.id.status_bar)
    View           mStatusBar;
    @BindView(R.id.iv_back)
    ImageView      mIvBack;
    @BindView(R.id.iv_window_size)
    ImageView      mIvWindowSize;
    @BindView(R.id.iv_lights)
    ImageView      mIvLights;
    @BindView(R.id.iv_change_camera)
    ImageView      mIvChangeCamera;
    @BindView(R.id.iv_go_list)
    ImageView      mIvGoList;
    @BindView(R.id.rl_option)
    RelativeLayout mRlOption;
    @BindView(R.id.recyclerView)
    RecyclerView   mRecyclerView;
    @BindView(R.id.iv_photo_album_sub)
    ImageView      mIvPhotoAlbumSub;
    @BindView(R.id.iv_take_sub)
    ImageView      mIvTakeSub;
    @BindView(R.id.iv_filter_sub)
    ImageView      mIvFilterSub;
    @BindView(R.id.tv_filter_sub)
    TextView       mTvFilterSub;
    @BindView(R.id.ll_filter_sub)
    LinearLayout   mLlFilterSub;
    @BindView(R.id.ll_filter_option)
    LinearLayout   mLlFilterOption;
    @BindView(R.id.iv_AutoFocusRect)
    ImageView      mIvAutoFocusRect;
    @BindView(R.id.seekbar)
    SeekBar        mSeekbar;
    @BindView(R.id.ll_seek)
    LinearLayout   mLlSeek;
    @BindView(R.id.view_select_type)
    View           mViewSelectType;
    @BindView(R.id.tv_video_time)
    TextView       mTvVideoTime;

    private static final int REQUEST_CAMERA_PERMISSION_CODE                 = 0;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE           = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;


    private NvsStreamingContext mStreamingContext;

    private AlphaAnimation mFocusAnimation;

    private boolean m_supportAutoFocus = true; // 是否支持自动聚焦

    private int mRecordType = Constants.RECORD_TYPE_PICTURE;

    private PermissionsChecker mPermissionsChecker;
    private List<String>       mAllRequestPermission = new ArrayList<>();

    private int     mCurrentDeviceIndex;
    private boolean mPermissionGranted;

    private Dialog         mBuilderPermission;
    private RelativeLayout mPermissionDialog;
    private TextView       mOk;
    private NvAssetManager mAssetManager;

    private VideoFilterListAdapter mAdapter;

    private ArrayList<String> mRecordFileList = new ArrayList<String>();

    private ArrayList<FilterItem> mFilterDataArrayList = new ArrayList<>();
    private VideoClipFxInfo       mVideoClipFxInfo     = new VideoClipFxInfo();
    private NvsCaptureVideoFx     mCurCaptureVideoFx;

    private int captureDeviceIndex;

    private boolean mIsSwitchingCamera = false;

    private long mEachRecodingVideoTime = 0;

    private NvsStreamingContext.CaptureDeviceCapability mCapability = null;

    private       String mCurRecordVideoPath;
    private final int    MIN_RECORD_DURATION = 3000000;

    private Bitmap mPictureBitmap;

    private int windowType = 1;// 1，默认    2,full    3,1：1

    @Override
    protected int getLayoutId() {
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_go_take_photo;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
//        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        statusbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getStatusHeight(this)));
        }

        mSeekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        initCapture();

        mAssetManager = NvAssetManager.sharedInstance();
        mAssetManager.searchLocalAssets(NvAsset.ASSET_FILTER);
        String bundlePath = "filter";
        mAssetManager.searchReservedAssets(NvAsset.ASSET_FILTER, bundlePath);

        mFocusAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFocusAnimation.setDuration(1000);
        mFocusAnimation.setFillAfter(true);

        //滤镜初始化
        initFilterList();
        initFilter();

        initListener();
    }

    private void initFilter() {
        mAdapter = new VideoFilterListAdapter(mFilterDataArrayList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    //滤镜数据初始化
    private void initFilterList() {
        mFilterDataArrayList.clear();
        mFilterDataArrayList = AssetFxUtil.getFilterData(this,
                getLocalData(),
                mStreamingContext.getAllBuiltinCaptureVideoFxNames(),
                true,
                true);
    }

    private ArrayList<NvAsset> getLocalData() {
        return mAssetManager.getUsableAssets(NvAsset.ASSET_FILTER, NvAsset.AspectRatio_All, 0);
    }

    private void initCapture() {
        mLiveWindow.setBackgroundColor(222, 222, 222);
//        mLiveWindow.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        if (null == mStreamingContext) {
            return;
        }
        //给Streaming Context设置采集设备回调接口
        mStreamingContext.setCaptureDeviceCallback(this);
        mStreamingContext.setCaptureRecordingDurationCallback(this);
        mStreamingContext.setCaptureRecordingStartedCallback(this);
        if (mStreamingContext.getCaptureDeviceCount() == 0) {
            return;
        }

        // 将采集预览输出连接到LiveWindow控件
        if (!mStreamingContext.connectCapturePreviewWithLiveWindow(mLiveWindow)) {
            Log.e("GoTakePhotoActivity", "Failed to connect capture preview with livewindow!");
            return;
        }

        mCurrentDeviceIndex = 0;

        //采集设备数量判定
        if (mStreamingContext.getCaptureDeviceCount() > 1) {
            mIvChangeCamera.setEnabled(true);
        } else {
            mIvChangeCamera.setEnabled(false);
        }

        mPermissionsChecker = new PermissionsChecker(this);
        mAllRequestPermission.add(Manifest.permission.CAMERA);
        mAllRequestPermission.add(Manifest.permission.RECORD_AUDIO);
        mAllRequestPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mAllRequestPermission = mPermissionsChecker.checkPermission(mAllRequestPermission);


        if (mAllRequestPermission.isEmpty() || mAllRequestPermission.size() == 0) {
            mPermissionGranted = true;
            startCapturePreview(false);
        } else {
            int code = getCodeInPermission(mAllRequestPermission.get(0));
            startPermissionsActivity(code, mAllRequestPermission.get(0));
        }
        mStreamingContext.removeAllCaptureVideoFx();
    }

    private void startPermissionsActivity(int code, String... permission) {
        PermissionsActivity.startActivityForResult(this, code, permission);
    }

    private int getCodeInPermission(String permission) {
        int code = 0;
        if (permission.equals(Manifest.permission.CAMERA)) {
            code = REQUEST_CAMERA_PERMISSION_CODE;
        } else if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
            code = REQUEST_RECORD_AUDIO_PERMISSION_CODE;
        } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            code = REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE;
        }
        return code;
    }

    private boolean startCapturePreview(boolean deviceChanged) {
        // 判断当前引擎状态是否为采集预览状态
//        int captureResolutionGrade = ParameterSettingValues.instance().getCaptureResolutionGrade();
        if (mPermissionGranted && (deviceChanged || getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_CAPTUREPREVIEW)) {
            m_supportAutoFocus = false;
            NvsRational nvsRational = null;
            if (windowType == 1) {
                nvsRational = new NvsRational(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this) - UiUtils.dip2px(168));
            } else if (windowType == 2) {
                nvsRational = new NvsRational(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
            } else if (windowType == 3) {
                nvsRational = new NvsRational(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenWidth(this));
            }
            if (!mStreamingContext.startCapturePreview(mCurrentDeviceIndex,
                    NvsStreamingContext.COMPILE_VIDEO_RESOLUTION_GRADE_1080,
                    NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                            NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME
                            | NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, nvsRational)) {
                Log.e("GoTakePhotoActivity", "Failed to start capture preview!");
                return false;
            }
            mStreamingContext.setZoom(0);
        }
        return true;
    }

    // 获取当前引擎状态
    private int getCurrentEngineState() {
        return mStreamingContext.getStreamingEngineState();
    }

    private void initListener() {
        mLiveWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float rectHalfWidth = mIvAutoFocusRect.getWidth() / 2;
                if (event.getX() - rectHalfWidth >= 0 && event.getX() + rectHalfWidth <= mLiveWindow.getWidth()
                        && event.getY() - rectHalfWidth >= 0 && event.getY() + rectHalfWidth <= mLiveWindow.getHeight()) {
                    mIvAutoFocusRect.setX(event.getX() - rectHalfWidth);
                    mIvAutoFocusRect.setY(event.getY() - rectHalfWidth);
                    RectF rectFrame = new RectF();
                    rectFrame.set(mIvAutoFocusRect.getX(), mIvAutoFocusRect.getY(),
                            mIvAutoFocusRect.getX() + mIvAutoFocusRect.getWidth(),
                            mIvAutoFocusRect.getY() + mIvAutoFocusRect.getHeight());
                    //启动自动聚焦
                    mIvAutoFocusRect.startAnimation(mFocusAnimation);
                    if (m_supportAutoFocus)
                        mStreamingContext.startAutoFocus(new RectF(rectFrame));
                }
                return false;
            }
        });
    }


    @OnClick({R.id.iv_photo_album, R.id.iv_photo_album_sub, R.id.iv_take_sub, R.id.ll_filter_sub, R.id.iv_take, R.id.ll_filter, R.id.tv_type_picture, R.id.view_left, R.id.tv_type_video, R.id.iv_back, R.id.iv_window_size, R.id.iv_lights, R.id.iv_change_camera, R.id.iv_go_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo_album_sub:
            case R.id.iv_photo_album:
                startActivity(new Intent(this, GoSelectImageActivity.class));
                break;
            case R.id.iv_take_sub:
            case R.id.iv_take:
                if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
                    stopRecording();
                } else {
                    mCurRecordVideoPath = PathUtils.getRecordVideoPath();
                    if (mCurRecordVideoPath == null)
                        return;
                    mIvTake.setEnabled(false);
                    mIvTakeSub.setEnabled(false);
                    mIvTake.setImageAlpha(128);
                    mIvTakeSub.setImageAlpha(128);

                    // 拍视频or拍照片
                    if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
//                        mLlRecordTypeLayout.clearAnimation();
                        mLlRecordTypeLayout.setVisibility(View.GONE);
                        mViewSelectType.setVisibility(View.INVISIBLE);
                        mTvVideoTime.setVisibility(View.VISIBLE);
                        mEachRecodingVideoTime = 0;
                        //当前未在视频录制状态，则启动视频录制。此处使用带特效的录制方式
                        if (!mStreamingContext.startRecording(mCurRecordVideoPath))
                            return;
                        mRecordFileList.add(mCurRecordVideoPath);
                    } else if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
                        mStreamingContext.startRecording(mCurRecordVideoPath);
                    }
                }
                break;
            case R.id.ll_filter_sub:
                mLlFilterOption.setVisibility(View.GONE);
                mRlOption.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_filter:
                mLlFilterOption.setVisibility(View.VISIBLE);
                mRlOption.setVisibility(View.GONE);
                break;
            case R.id.tv_type_video:
                if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
                    return;
                }
                selectRecordType(false);
                break;
            case R.id.tv_type_picture:
                if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
                    return;
                }
                selectRecordType(true);
                break;
            case R.id.view_left:

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_window_size:
                windowType = windowType == 1 ? 2 : windowType == 2 ? 3 : 1;
                mIvWindowSize.setImageResource(windowType == 2 ? R.drawable.full : windowType == 1 ? R.drawable.three : R.drawable.one_one);
                mViewOptiongBg.setVisibility(windowType == 2 ? View.GONE : View.VISIBLE);
                mIvPhotoAlbum.setImageResource(windowType == 2 ? R.mipmap.zhaopianku_bai : R.mipmap.zhaopianku_hei);
                mIvPhotoAlbumSub.setImageResource(windowType == 2 ? R.mipmap.zhaopianku_bai : R.mipmap.zhaopianku_hei);
                mIvFilter.setImageResource(windowType == 2 ? R.mipmap.lvjing_bai : R.mipmap.lvjing);
                mIvFilterSub.setImageResource(windowType == 2 ? R.mipmap.lvjing_bai : R.mipmap.lvjing);
                mTvFilter.setTextColor(windowType == 2 ? Color.WHITE : Color.parseColor("#333333"));
                mTvFilterSub.setTextColor(windowType == 2 ? Color.WHITE : Color.parseColor("#333333"));
                mTvTypePicture.setTextColor(mRecordType == Constants.RECORD_TYPE_PICTURE ? (windowType == 2 ? Color.WHITE : Color.parseColor("#333333")) : Color.parseColor(windowType == 2 ? "#99ffffff" : "#888888"));
                mTvTypeVideo.setTextColor(mRecordType == Constants.RECORD_TYPE_VIDEO ? (windowType == 2 ? Color.WHITE : Color.parseColor("#333333")) : Color.parseColor(windowType == 2 ? "#99ffffff" : "#888888"));
                mTvVideoTime.setTextColor(windowType == 2 ? Color.WHITE : Color.parseColor("#888888"));

                mIvBack.setImageResource(windowType == 3 ? R.mipmap.back_black : R.mipmap.back_icon);
                mIvChangeCamera.setImageResource(windowType == 3 ? R.mipmap.fanzhuan_hei : R.mipmap.fanzhuan);
                mIvGoList.setImageResource(windowType == 3 ? R.drawable.tongkuan_b : R.mipmap.tongkuan);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLiveWindow.getLayoutParams();
                if (windowType == 3) {
                    int windowWidth  = ScreenUtils.getScreenWidth(this);
                    int windowHeight = ScreenUtils.getScreenHeight(this);
                    layoutParams.topMargin = windowHeight - windowWidth - UiUtils.dip2px(167);
                    mIvLights.setImageResource(mStreamingContext.isFlashOn() ? R.mipmap.shanguang_b : R.mipmap.shanhuangoff_b);
                } else {
                    layoutParams.topMargin = 0;
                    mIvLights.setImageResource(mStreamingContext.isFlashOn() ? R.mipmap.shanguang : R.mipmap.shanhuang_off_w);
                }
                mLiveWindow.setLayoutParams(layoutParams);
                startCapturePreview(true);
                break;
            case R.id.iv_lights:
                if (mStreamingContext.isFlashOn()) {
                    mStreamingContext.toggleFlash(false);
                    mIvLights.setImageResource(windowType == 3 ? R.mipmap.shanhuangoff_b : R.mipmap.shanhuang_off_w);
                } else {
                    mStreamingContext.toggleFlash(true);
                    mIvLights.setImageResource(windowType == 3 ? R.mipmap.shanguang_b : R.mipmap.shanguang);
                }
                break;
            case R.id.iv_change_camera:
                if (mIsSwitchingCamera) {
                    return;
                }
                if (mCurrentDeviceIndex == 0) {
                    mCurrentDeviceIndex = 1;
                    mIvLights.setEnabled(false);
                    mIvLights.setImageResource(windowType == 3 ? R.mipmap.shanhuangoff_b : R.mipmap.shanhuang_off_w);
                } else {
                    mCurrentDeviceIndex = 0;
                    mIvLights.setEnabled(true);
                    mIvLights.setImageResource(windowType == 3 ? R.mipmap.shanhuangoff_b : R.mipmap.shanhuang_off_w);
                }

                mIsSwitchingCamera = true;
                startCapturePreview(true);
                break;
            case R.id.iv_go_list:
                startActivity(new Intent(this, SameActivity.class));
                break;
        }
    }


    private void stopRecording() {
        mStreamingContext.stopRecording();
        mIvTake.setImageAlpha(255);
        mIvTakeSub.setImageAlpha(255);
    }

    private void selectRecordType(boolean left_to_right) {
//        TranslateAnimation ani;
        ObjectAnimator animator;
        if (left_to_right) {
            animator = ObjectAnimator.ofFloat(mLlRecordTypeLayout, "translationX", -mTvTypePicture.getX(), mViewLeft.getX(), mViewLeft.getX());
//            ani = new TranslateAnimation(-mTvTypePicture.getX(), mViewLeft.getX(), 0, 0);
        } else {
            animator = ObjectAnimator.ofFloat(mLlRecordTypeLayout, "translationX", mViewLeft.getX(), -mTvTypePicture.getX(), -mTvTypePicture.getX());
//            ani = new TranslateAnimation(mViewLeft.getX(), -mTvTypePicture.getX(), 0, 0);
        }
        mRecordType = mRecordType == Constants.RECORD_TYPE_PICTURE ? Constants.RECORD_TYPE_VIDEO : Constants.RECORD_TYPE_PICTURE;
        mTvTypePicture.setTextColor(mRecordType == Constants.RECORD_TYPE_PICTURE ? (windowType == 2 ? Color.WHITE : Color.parseColor("#333333")) : Color.parseColor(windowType == 2 ? "#99ffffff" : "#888888"));
        mTvTypeVideo.setTextColor(mRecordType == Constants.RECORD_TYPE_VIDEO ? (windowType == 2 ? Color.WHITE : Color.parseColor("#333333")) : Color.parseColor(windowType == 2 ? "#99ffffff" : "#888888"));

        animator.setDuration(300);
        animator.start();
//        mLlRecordTypeLayout.startAnimation(ani);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PermissionsActivity.PERMISSIONS_DENIED:
                mBuilderPermission = new Dialog(this, R.style.dialogTransparent);
                mPermissionDialog = (RelativeLayout) getLayoutInflater().inflate(R.layout.capture_permission_dialog, null);
                mOk = mPermissionDialog.findViewById(R.id.ok);
                mBuilderPermission.setContentView(mPermissionDialog);
                mOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBuilderPermission.dismiss();
                        finish();
                    }
                });

                mBuilderPermission.show();
                break;
            case PermissionsActivity.PERMISSIONS_GRANTED:
                if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
                    if (mAllRequestPermission.size() == 1) {
                        mPermissionGranted = true;
                        startCapturePreview(false);
                    } else {
                        int code = getCodeInPermission(mAllRequestPermission.get(mAllRequestPermission.size() - 1));
                        startPermissionsActivity(code, mAllRequestPermission.get(mAllRequestPermission.size() - 1));
                    }
                }
                if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION_CODE || requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE) {
                    mPermissionGranted = true;
                    startCapturePreview(false);
                }
                break;

            case 200:
                finish();
                break;
        }
    }


    private void updateSettingsWithCapability(int deviceIndex) {
        //获取采集设备能力描述对象，设置自动聚焦，曝光补偿，缩放
        mCapability = mStreamingContext.getCaptureDeviceCapability(deviceIndex);
        if (null == mCapability) {
            return;
        }

        //是否支持闪光灯
        if (mCapability.supportFlash) {
            mIvLights.setEnabled(true);
        }

        m_supportAutoFocus = mCapability.supportAutoFocus;

        // 是否支持缩放
//        if (mCapability.supportZoom) {
//            mZoomValue = mCapability.maxZoom;
//            mZoomSeekbar.setMax(mZoomValue);
//            mZoomSeekbar.setProgress(mStreamingContext.getZoom());
//            mZoomSeekbar.setEnabled(true);
//        } else {
//            Log.e(TAG, "该设备不支持缩放");
//        }
//
//        // 是否支持曝光补偿
//        if (mCapability.supportExposureCompensation) {
//            mMinExpose = mCapability.minExposureCompensation;
//            mExposeSeekbar.setMax(mCapability.maxExposureCompensation - mMinExpose);
//            mExposeSeekbar.setProgress(mStreamingContext.getExposureCompensation() - mMinExpose);
//            mExposeSeekbar.setEnabled(true);
//        }
    }

    @Override
    public void onCaptureDeviceCapsReady(int i) {
        if (captureDeviceIndex != mCurrentDeviceIndex) {
            return;
        }
        updateSettingsWithCapability(captureDeviceIndex);
    }

    @Override
    public void onCaptureDevicePreviewResolutionReady(int i) {

    }

    @Override
    public void onCaptureDevicePreviewStarted(int i) {
        mIsSwitchingCamera = false;
    }

    @Override
    public void onCaptureDeviceError(int i, int i1) {

    }

    @Override
    public void onCaptureDeviceStopped(int i) {

    }

    @Override
    public void onCaptureDeviceAutoFocusComplete(int i, boolean b) {

    }

    @Override
    public void onCaptureRecordingFinished(int i) {
        if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
            mIvTakeSub.setEnabled(true);
            mIvTake.setEnabled(true);
            mIvTakeSub.setImageAlpha(255);
            mIvTake.setImageAlpha(255);
//            mLlRecordTypeLayout.clearAnimation();
            mLlRecordTypeLayout.setVisibility(View.VISIBLE);
            mViewSelectType.setVisibility(View.VISIBLE);
            mTvVideoTime.setVisibility(View.GONE);
            startActivity(new Intent(GoTakePhotoActivity.this, PreviewGoTakePhotoActivity.class).putExtra("path", mCurRecordVideoPath));
        }
    }

    private void takeImage(long l) {
        mIvTakeSub.setEnabled(true);
        mIvTake.setEnabled(true);
        mIvTakeSub.setImageAlpha(255);
        mIvTake.setImageAlpha(255);
        if (mCurRecordVideoPath != null) {
            NvsVideoFrameRetriever videoFrameRetriever = mStreamingContext.createVideoFrameRetriever(mCurRecordVideoPath);
            if (videoFrameRetriever != null) {
                mPictureBitmap = videoFrameRetriever.getFrameAtTimeWithCustomVideoFrameHeight(l, ScreenUtils.getScreenHeight(this));
                Log.e("===>", "screen: " + ScreenUtils.getScreenWidth(this) + " " + ScreenUtils.getScreenHeight(this));
                Log.e("===>", "picture: " + mPictureBitmap.getWidth() + " " + mPictureBitmap.getHeight());
                deleteFile();
                String  jpgPath  = PathUtils.getRecordPicturePath();
                boolean save_ret = Util.saveBitmapToSD(mPictureBitmap, jpgPath);
                if (save_ret) {
                    startActivity(new Intent(GoTakePhotoActivity.this, PreviewGoTakePhotoActivity.class)
                            .putExtra("data_url", getIntent().getStringExtra("data_url"))
                            .putExtra("path", jpgPath));
                } else {
                    Toast.makeText(this, "图片出错", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void deleteFile() {
        if (mCurRecordVideoPath != null) {
            File file = new File(mCurRecordVideoPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void onCaptureRecordingError(int i) {

    }

    @Override
    public void onCaptureRecordingDuration(int i, long l) {
        // 拍视频or拍照片
        if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
            if (l >= MIN_RECORD_DURATION) {
                mIvTakeSub.setEnabled(true);
                mIvTake.setEnabled(true);
                mIvTakeSub.setImageAlpha(255);
                mIvTake.setImageAlpha(255);
            }
            mEachRecodingVideoTime = l;
            mTvVideoTime.setVisibility(View.VISIBLE);
            mTvVideoTime.setText(TimeFormatUtil.formatUsToString2(mEachRecodingVideoTime));
        } else if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
            if (l > 40000) {
                stopRecording();
                takeImage(l);
            }
        }
    }

    @Override
    public void onCaptureRecordingStarted(int i) {

    }

    private void removeAllFilterFx() {
        List<Integer> remove_list = new ArrayList<>();
        for (int i = 0; i < mStreamingContext.getCaptureVideoFxCount(); i++) {
            NvsCaptureVideoFx fx = mStreamingContext.getCaptureVideoFxByIndex(i);
            if (fx == null)
                continue;

            String name = fx.getBuiltinCaptureVideoFxName();
            if (name != null && !name.equals("Beauty") && !name.equals("Face Effect")) {
                remove_list.add(i);
                Log.e("===>", "fx name: " + name);
            }
        }
        if (!remove_list.isEmpty()) {
            for (int i = 0; i < remove_list.size(); i++) {
                mStreamingContext.removeCaptureVideoFx(remove_list.get(i));
            }
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setSelectPosition(position);
        int count = mFilterDataArrayList.size();
        if (position < 0 || position >= count)
            return;
        removeAllFilterFx();
        mSeekbar.setProgress(100);
        if (position == 0) {
            mLlSeek.setVisibility(View.INVISIBLE);
            mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_BUILTIN);
            mVideoClipFxInfo.setFxId(null);
            return;
        }
        mLlSeek.setVisibility(View.VISIBLE);
        FilterItem filterItem = mFilterDataArrayList.get(position);
        int        filterMode = filterItem.getFilterMode();
        if (filterMode == FilterItem.FILTERMODE_BUILTIN) {
            String filterName = filterItem.getFilterId();

            if (!TextUtils.isEmpty(filterName) && filterItem.getIsCartoon()) {
                mCurCaptureVideoFx = mStreamingContext.appendBuiltinCaptureVideoFx("Cartoon");
                mCurCaptureVideoFx.setBooleanVal("Stroke Only", filterItem.getStrokenOnly());
                mCurCaptureVideoFx.setBooleanVal("Grayscale", filterItem.getGrayScale());

            } else if (!TextUtils.isEmpty(filterName)) {
                mCurCaptureVideoFx = mStreamingContext.appendBuiltinCaptureVideoFx(filterName);
            }
            mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_BUILTIN);
            mVideoClipFxInfo.setFxId(filterName);
        } else {
            String filterPackageId = filterItem.getPackageId();
            if (!TextUtils.isEmpty(filterPackageId)) {
                mCurCaptureVideoFx = mStreamingContext.appendPackagedCaptureVideoFx(filterPackageId);
            }
            mVideoClipFxInfo.setFxMode(VideoClipFxInfo.FXMODE_PACKAGE);
            mVideoClipFxInfo.setFxId(filterPackageId);
        }

        mCurCaptureVideoFx.setFilterIntensity(1.0f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mStreamingContext != null) {
            mStreamingContext.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionsChecker == null) {
            mPermissionsChecker = new PermissionsChecker(this);
        }
//        mNext.setClickable(true);
//        mFilterView.setMoreFilterClickable(true);
//        mFaceUPropView.setMoreFaceUPropClickable(true);
        startCapturePreview(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
            stopRecording();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mStreamingContext != null) {
            mStreamingContext.stop();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mCurCaptureVideoFx != null) {
            mCurCaptureVideoFx.setFilterIntensity((float) progress / 100.0f);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Subscribe(threadMode =ThreadMode.MAIN) //在ui线程执行
    public void onEventBusMessage(String event) {
        if (event.equals("publish_success")){
            finish();
        }
    }
}
