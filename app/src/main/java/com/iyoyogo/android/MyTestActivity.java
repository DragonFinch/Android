package com.iyoyogo.android;


import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.edit.VideoEditActivity;
import com.iyoyogo.android.camera.edit.clipEdit.trim.TrimActivity;
import com.iyoyogo.android.camera.edit.filter.FilterAdapter;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.MediaScannerUtil;
import com.iyoyogo.android.camera.utils.ParameterSettingValues;
import com.iyoyogo.android.camera.utils.PathUtils;
import com.iyoyogo.android.camera.utils.ScreenUtils;
import com.iyoyogo.android.camera.utils.SpUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.camera.utils.authpack;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.permission.PermissionsActivity;
import com.iyoyogo.android.camera.utils.permission.PermissionsChecker;

import com.iyoyogo.android.interfaces.CaptureListener;

import com.iyoyogo.android.ui.common.MainActivity;

import com.iyoyogo.android.view.CircleProgressBar;
import com.meicam.sdk.NvsAVFileInfo;
import com.meicam.sdk.NvsCaptureVideoFx;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsSize;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsVideoFrameRetriever;
import com.meicam.sdk.NvsVideoStreamInfo;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class MyTestActivity extends BaseActivity implements NvsStreamingContext.CaptureDeviceCallback,
        NvsStreamingContext.CaptureRecordingDurationCallback,
        View.OnClickListener,
        NvsStreamingContext.CaptureRecordingStartedCallback, NvAssetManager.NvAssetManagerListener {
    private static final String TAG = "Capture";
    private CaptureListener captureListener;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 0;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;

    public static final int CAPTURE_TYPE_ZOOM = 2;
    public static final int CAPTURE_TYPE_EXPOSE = 3;
    private static final int FILTERREQUESTLIST = 110;
    private static final int ARFACEERREQUESTLIST = 111;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE = 203;
    private final int MIN_RECORD_DURATION = 1000000;
    private boolean isTrue;
    private boolean isLong;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private NvsLiveWindow mLiveWindow;
    private NvsStreamingContext mStreamingContext;

    private LinearLayout mFunctionButtonLayout;
    private ImageView mSwitchFacingLayout;
    private ImageView mFlashLayout;
    private ImageView mFlashButton;
    private LinearLayout mZoomLayout;
    private LinearLayout mExposureLayout;
    private LinearLayout mBeautyLayout;
    private ImageView mFilterLayout;
    private LinearLayout mFuLayout;
    private TextView mOk;
    private RelativeLayout mMainLayout;
    private RelativeLayout mStartLayout;
    private CircleProgressBar mStartRecordingImage;
    //    private TextView mStartText;
//    private ImageView mDelete;
//    private ImageView mNext;
//    private TextView mRecordTime;
    private TextView mSeekTitle;
    private TextView mSeekProgress;
    private ImageView mImageAutoFocusRect;

    /*拍照or视频*/
    private RelativeLayout mSelectLayout, mPictureLayout;
    private LinearLayout mRecordTypeLayout;
    private View mTypeRightView;
    private Button mTypePictureBtn, mTypeVideoBtn, mPictureCancel, mPictureOk;
    private int mRecordType = Constants.RECORD_TYPE_VIDEO;
    private ImageView mPictureImage;
    private Bitmap mPictureBitmap;

    /*录制*/
    private ArrayList<Long> mRecordTimeList = new ArrayList<Long>();
    private ArrayList<String> mRecordFileList = new ArrayList<String>();
    private long mEachRecodingVideoTime = 0, mEachRecodingImageTime = 40000;
    private long mAllRecordingTime = 0;
    private String mCurRecordVideoPath;


    private boolean mPermissionGranted;
    private int mCurrentDeviceIndex;
    List<String> mAllRequestPermission = new ArrayList<>();
    private Dialog mBuilderPermission;
    private RelativeLayout mPermissionDialog;
    private boolean mIsSwitchingCamera = false;
    NvsStreamingContext.CaptureDeviceCapability mCapability = null;
    private int mCenterX;
    private int mCenterY;
    private int mDepthZ = 400;
    private int mDuration = 400;
    private AlphaAnimation mFocusAnimation;

    /*滤镜*/
    private AlertDialog mCaptureFilterDialog;
    private View mFilterView;
    private RecyclerView mFilterRecyclerView;
    private FilterAdapter mFilterAdapter;
    private RelativeLayout mMoreFilterLayout;
    private ArrayList<FilterItem> mFilterItemArrayList = new ArrayList<>();
    private ImageButton mMoreFilterButton;
    private SeekBar mFilterIntensitySeekBar;
    private View mFilterIntensitySeekBarClickView;
    private NvsCaptureVideoFx mCurCaptureVideoFx;



    private NvAssetManager mAssetManager;

    private boolean m_canUseARFace = false; // 人脸特效是否可用的标识
    private boolean m_supportAutoFocus = false; // 是否支持自动聚焦
    private SpUtil mSp;
    private ValueAnimator animator;
    //

    private GestureDetector mGestureDetector;

    private void simulateProgress() {

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mStartRecordingImage.setProgress(progress);
                if (mStartRecordingImage.getProgress() == 100) {
                  /*  stopRecording();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishVideo();
                        }
                    },500);*/



                }
            }
        });
        animator.setDuration(10000);
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initView() {
        checkAllPermission();
        animator = ValueAnimator.ofInt(0, 100);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        /*滤镜dialog*/
        LayoutInflater filterInflater = LayoutInflater.from(this);
        mFilterView = filterInflater.inflate(R.layout.filter_view, null);
        mFilterRecyclerView = (RecyclerView) mFilterView.findViewById(R.id.filterBar);
//        mMoreFilterLayout = (RelativeLayout) mFilterView.findViewById(R.id.more_filter_layout);
//        mMoreFilterButton = (ImageButton) mFilterView.findViewById(R.id.download_more_btn);
        mFilterIntensitySeekBar = (SeekBar) mFilterView.findViewById(R.id.intensitySeekBar);
        mFilterIntensitySeekBarClickView = mFilterView.findViewById(R.id.seekbar_enable_click_view);
        mFilterIntensitySeekBar.setEnabled(false);
        mFilterIntensitySeekBar.setMax(100);
        mFilterIntensitySeekBar.setProgress(100);


        mImageAutoFocusRect = (ImageView) findViewById(R.id.imageAutoFocusRect);
        mStartLayout = (RelativeLayout) findViewById(R.id.startLayout);
        mStartRecordingImage = (CircleProgressBar) findViewById(R.id.startRecordingImage);
        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mLiveWindow = (NvsLiveWindow) findViewById(R.id.liveWindow);

        mFunctionButtonLayout = (LinearLayout) findViewById(R.id.functionButtonLayout);
        mSwitchFacingLayout = (ImageView) findViewById(R.id.switchFacingLayout);
        mFlashLayout = (ImageView) findViewById(R.id.flashLayout);
        mFilterLayout = (ImageView) findViewById(R.id.filterLayout);

        /*拍照or视频*/
        mRecordTypeLayout = (LinearLayout) findViewById(R.id.record_type_layout);
        mTypeRightView = findViewById(R.id.rightView);
        mTypePictureBtn = (Button) findViewById(R.id.type_picture_btn);
        mTypeVideoBtn = (Button) findViewById(R.id.type_video_btn);
        mSelectLayout = (RelativeLayout) findViewById(R.id.select_layout);
        mPictureLayout = (RelativeLayout) findViewById(R.id.picture_layout);
        mPictureCancel = (Button) findViewById(R.id.picture_cancel);
        mPictureOk = (Button) findViewById(R.id.picture_ok);
        mPictureImage = (ImageView) findViewById(R.id.picture_image);
mFilterLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showCaptureDialogView(mCaptureFilterDialog, mFilterView);
    }
});
        mCaptureFilterDialog = new AlertDialog.Builder(this).create();
        mCaptureFilterDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                closeCaptureDialogView(mCaptureFilterDialog);
            }
        });
        mStartRecordingImage.setMax(100);

    }

    private void initFilterList() {
        int[] resImags = {
                R.mipmap.sage, R.mipmap.maid, R.mipmap.mace,
                R.mipmap.lace, R.mipmap.mall, R.mipmap.sap, R.mipmap.sara,
                R.mipmap.pinky, R.mipmap.sweet, R.mipmap.fresh, R.mipmap.package1
        };


        ArrayList<NvAsset> filterList = getLocalData(NvAsset.ASSET_FILTER);
        String bundlePath = "filter/info.txt";

        Util.getBundleFilterInfo(this, filterList, bundlePath);
        for (NvAsset asset : filterList) {
            FilterItem newFilterItem = new FilterItem();
            if (asset.isReserved()) {
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUNDLE);
            } else {
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_PACKAGE);
            }
            newFilterItem.setFilterName(asset.name);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
            mFilterItemArrayList.add(newFilterItem);

        }

        List<String> builtinFilterList = mStreamingContext.getAllBuiltinCaptureVideoFxNames();

        for (int i = 0; i < builtinFilterList.size(); i++) {
            String filterName = builtinFilterList.get(i);
            Log.d("filterName", "   filterName==" + filterName);
            FilterItem newFilterItem = new FilterItem();
            newFilterItem.setFilterName(filterName);
            if (i < resImags.length) {
                int imageId = resImags[i];
                newFilterItem.setImageId(imageId);
            }
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
            mFilterItemArrayList.add(newFilterItem);
        }
    }

    private void initFilterRecyclerView() {
        mFilterAdapter = new FilterAdapter(this);
        mFilterAdapter.setFilterDataList(mFilterItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFilterRecyclerView.setLayoutManager(linearLayoutManager);
        mFilterRecyclerView.setAdapter(mFilterAdapter);

        mFilterAdapter.setOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mFilterItemArrayList.size()) {
                    removeAllFilterFx();
                    if (position == 0) {
                        mFilterIntensitySeekBar.setEnabled(false);
                        mFilterIntensitySeekBarClickView.setVisibility(View.VISIBLE);
                        return;
                    }
                    mFilterIntensitySeekBar.setEnabled(true);
                    mFilterIntensitySeekBarClickView.setVisibility(View.GONE);

                    FilterItem filterItem = mFilterItemArrayList.get(position);
                    int filterMode = filterItem.getFilterMode();
                    if (filterMode == FilterItem.FILTERMODE_BUILTIN) {
                        String filterName = filterItem.getFilterName();
                        if (!TextUtils.isEmpty(filterName)) {
                            mCurCaptureVideoFx = mStreamingContext.appendBuiltinCaptureVideoFx(filterName);
                        }
                    } else {
                        String filterPackageId = filterItem.getPackageId();
                        if (!TextUtils.isEmpty(filterPackageId)) {
                            mCurCaptureVideoFx = mStreamingContext.appendPackagedCaptureVideoFx(filterPackageId);
                        }
                    }
                    mCurCaptureVideoFx.setFilterIntensity(1.0f);
                    mFilterIntensitySeekBar.setProgress(100);
                }
            }

            @Override
            public void onSameItemClick() {

            }
        });
    }


    private void initPropList() {
    ArrayList<NvAsset> filterList = getLocalData(NvAsset.ASSET_FACE1_STICKER);
        for (NvAsset asset : filterList) {
            FilterItem newFilterItem = new FilterItem();
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_PACKAGE);
            newFilterItem.setFilterName(asset.localDirPath);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
        }

        filterList = getLocalData(NvAsset.ASSET_FACE_BUNDLE_STICKER);
        for (NvAsset asset : filterList) {
            FilterItem newFilterItem = new FilterItem();
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUNDLE);
            newFilterItem.setFilterName(asset.bundledLocalDirPath);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            return;

        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION_CODE:
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    } else
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
                } else {
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION_CODE);
                }
                break;
            case REQUEST_RECORD_AUDIO_PERMISSION_CODE:
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE:
                break;
            case REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE:

                break;
        }
    }








    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected int getLayoutId() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return R.layout.activity_test;
        }
        //初始化
        String licensePath = "assets:/meishesdk.lic";

        NvsStreamingContext.init(this, licensePath, NvsStreamingContext.STREAMING_CONTEXT_FLAG_SUPPORT_4K_EDIT);
        NvAssetManager.init(MyTestActivity.this);//素材管理器初始化
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_test;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    public void initTitle() {

    }
    private void startRecoding() {
        // 当前在录制状态，可停止视频录制
        mCurRecordVideoPath = PathUtils.getRecordVideoPath();
        if (mCurRecordVideoPath == null)
            return;
        mStartRecordingImage.setEnabled(false);

        // 拍视频or拍照片
        if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
            mEachRecodingVideoTime = 0;
            //当前未在视频录制状态，则启动视频录制。此处使用带特效的录制方式
            if (!mStreamingContext.startRecording(mCurRecordVideoPath))
                return;
            isInRecording(false);
            mRecordFileList.add(mCurRecordVideoPath);
        } else if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
            mStreamingContext.startRecording(mCurRecordVideoPath);
            isInRecording(false);
        }

    }

    //TODO 自定义停止录制方法
    private void stopRecodAndSaveStart() {
        if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
            stopRecording();
        }
        /*将拍摄的视频传到下一个页面mRecordFileList*/
        ArrayList<ClipInfo> pathList = new ArrayList<>();
        for (int i = 0; i < mRecordFileList.size(); i++) {
            ClipInfo clipInfo = new ClipInfo();
            clipInfo.setFilePath(mRecordFileList.get(i));
            pathList.add(clipInfo);
        }
        NvsAVFileInfo avFileInfo = mStreamingContext.getAVFileInfo(pathList.get(0).getFilePath());
        if (avFileInfo == null)
            return;
        TimelineData.instance().clear();//数据清空
        NvsSize size = avFileInfo.getVideoStreamDimension(0);
        int rotation = avFileInfo.getVideoStreamRotation(0);
        if (rotation == NvsVideoStreamInfo.VIDEO_ROTATION_90
                || rotation == NvsVideoStreamInfo.VIDEO_ROTATION_270) {
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;
        }
        int makeRatio = size.width > size.height ? NvAsset.AspectRatio_16v9 : NvAsset.AspectRatio_9v16;
        TimelineData.instance().setVideoResolution(Util.getVideoEditResolution(makeRatio));
        TimelineData.instance().setMakeRatio(makeRatio);
        TimelineData.instance().setClipInfoData(pathList);

        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.START_ACTIVITY_FROM_CAPTURE, true);
        AppManager.getInstance().jumpActivity(MyTestActivity.this, VideoEditActivity.class, bundle);


    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mStartRecordingImage.setClickable(true);
        mStartRecordingImage.setLongClickable(true);
        mStartRecordingImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(MyTestActivity.this, "开始录制", Toast.LENGTH_SHORT).show();
                        startRecoding();
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(MyTestActivity.this, "停止录制", Toast.LENGTH_SHORT).show();
                        stopRecodAndSaveStart();
                        break;
                }

                return false;
            }
        });
        mSp = SpUtil.getInstance(getApplicationContext());
        ParameterSettingValues parameterValues = (ParameterSettingValues) mSp.getObjectFromShare(getApplicationContext(), Constants.KEY_PARAMTER);
        if (parameterValues != null) {  // 本地没有存储设置的参数，设置默认值
            ParameterSettingValues.setParameterValues(parameterValues);
        }


        // 探测人脸特效是否可用
        try {
            Class.forName("com.meicam.sdk.NvsFaceEffectV1Detector");
            m_canUseARFace = true;
        } catch (ClassNotFoundException e) {
            m_canUseARFace = false;
            e.printStackTrace();
        }
        // 初始化AR Effect，全局只需一次
        if (m_canUseARFace) {
            com.meicam.sdk.NvsFaceEffectV1.setup("assets:/NvFaceData.asset", authpack.A());
            com.meicam.sdk.NvsFaceEffectV1.setMaxFaces(2);
        }
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                m_canUseARFace = bundle.getBoolean(Constants.CAN_USE_ARFACE_FROM_MAIN, true);
            }
        }

        initCapture();
        mAssetManager = NvAssetManager.sharedInstance();
        mAssetManager.searchLocalAssets(NvAsset.ASSET_FILTER);
        String bundlePath = "filter";
        mAssetManager.searchReservedAssets(NvAsset.ASSET_FILTER, bundlePath);

        bundlePath = "arface";
        mAssetManager.searchReservedAssets(NvAsset.ASSET_FACE_BUNDLE_STICKER, bundlePath);

        mAssetManager.searchLocalAssets(NvAsset.ASSET_FACE1_STICKER);

        mFocusAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFocusAnimation.setDuration(1000);
        mFocusAnimation.setFillAfter(true);


        initFilterList();
        initFilterRecyclerView();
        initPropList();
        mLiveWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float rectHalfWidth = mImageAutoFocusRect.getWidth() / 2;
                if (event.getX() - rectHalfWidth >= 0 && event.getX() + rectHalfWidth <= mLiveWindow.getWidth()
                        && event.getY() - rectHalfWidth >= 0 && event.getY() + rectHalfWidth <= mLiveWindow.getHeight()) {
                    mImageAutoFocusRect.setX(event.getX() - rectHalfWidth);
                    mImageAutoFocusRect.setY(event.getY() - rectHalfWidth);
                    RectF rectFrame = new RectF();
                    rectFrame.set(mImageAutoFocusRect.getX(), mImageAutoFocusRect.getY(),
                            mImageAutoFocusRect.getX() + mImageAutoFocusRect.getWidth(),
                            mImageAutoFocusRect.getY() + mImageAutoFocusRect.getHeight());
                    //启动自动聚焦
                    mImageAutoFocusRect.startAnimation(mFocusAnimation);
                    if (m_supportAutoFocus)
                        mStreamingContext.startAutoFocus(new RectF(rectFrame));
                }
                return false;
            }
        });
//        mStartRecordingImage.setCaptureLisenter(new CaptureListener() {
//            @Override
//            public void takePictures() {
//                startRecording();
//            }
//
//            @Override
//            public void recordShort(long time) {
//                startRecording();
//            }
//
//            @Override
//            public void recordStart() {
//                startRecording();
//            }
//
//            @Override
//            public void recordEnd(long time) {
//                stopRecording();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finishVideo();
//                    }
//                },500);
//            }
//
//            @Override
//            public void recordZoom(float zoom) {
//
//            }
//
//            @Override
//            public void recordError() {
//
//            }
//        });


        /*切换摄像头*/
        mSwitchFacingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSwitchingCamera) {
                    return;
                }
                if (mCurrentDeviceIndex == 0) {
                    mCurrentDeviceIndex = 1;
                    mFlashLayout.setEnabled(false);
                    mFlashLayout.setImageResource(R.mipmap.icon_flash_off);
                    mFlashLayout.setImageAlpha(128);
                } else {
                    mCurrentDeviceIndex = 0;
                    mFlashLayout.setEnabled(true);
                    mFlashLayout.setImageResource(R.mipmap.icon_flash_off);
                    mFlashLayout.setImageAlpha(255);
                }
                mCenterX = ScreenUtils.getScreenWidth(MyTestActivity.this) / 2;
                mCenterY = ScreenUtils.getScreenHeight(MyTestActivity.this) / 2;

                mIsSwitchingCamera = true;
                startCapturePreview(true);
            }
        });
        /*闪光灯*/
        mFlashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStreamingContext.isFlashOn()) {
                    mStreamingContext.toggleFlash(false);
                    mFlashLayout.setImageResource(R.mipmap.icon_flash_off);
                    mFlashLayout.setImageAlpha(255);
                } else {
                    mStreamingContext.toggleFlash(true);
                    mFlashLayout.setImageResource(R.mipmap.icon_flash_on);
                    mFlashLayout.setImageAlpha(255);
                }
            }
        });


        mMoreFilterLayout.setOnClickListener(this);
        mMoreFilterButton.setOnClickListener(this);



        mTypePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecordType(true);
            }
        });

        mTypeRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecordType(false);
            }
        });

        mPictureCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurRecordVideoPath != null) {
                    File file = new File(mCurRecordVideoPath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                showPictureLayout(false);
                isInRecording(true);
            }
        });


        // 滤镜程度调节
        mFilterIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    if (mCurCaptureVideoFx != null) {
                        float value = (float) i / 100;
                        mCurCaptureVideoFx.setFilterIntensity(value);
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

        mFilterIntensitySeekBarClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mFilterIntensitySeekBar.isEnabled()) {
                    Toast.makeText(MyTestActivity.this, "请选择一个滤镜", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//录制完成
    private void finishVideo() {
        ArrayList<ClipInfo> pathList = new ArrayList<>();
        for (int i = 0; i < mRecordFileList.size(); i++) {
            ClipInfo clipInfo = new ClipInfo();
            clipInfo.setFilePath(mRecordFileList.get(i));
            pathList.add(clipInfo);
        }
        NvsAVFileInfo avFileInfo = mStreamingContext.getAVFileInfo(pathList.get(0).getFilePath());
        if (avFileInfo == null)
            return;
        TimelineData.instance().clear();//数据清空
        NvsSize size = avFileInfo.getVideoStreamDimension(0);
        int rotation = avFileInfo.getVideoStreamRotation(0);
        if (rotation == NvsVideoStreamInfo.VIDEO_ROTATION_90
                || rotation == NvsVideoStreamInfo.VIDEO_ROTATION_270) {
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;
        }
        int makeRatio = size.width > size.height ? NvAsset.AspectRatio_16v9 : NvAsset.AspectRatio_9v16;
        TimelineData.instance().setVideoResolution(Util.getVideoEditResolution(makeRatio));
        TimelineData.instance().setMakeRatio(makeRatio);
        TimelineData.instance().setClipInfoData(pathList);
//        mNext.setClickable(false);

        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.START_ACTIVITY_FROM_CAPTURE, true);
        AppManager.getInstance().jumpActivity(MyTestActivity.this, TrimActivity.class, bundle);
        mStartRecordingImage.setProgress(0);

    }
//开始录制
    private boolean startRecording() {
        Toast.makeText(MyTestActivity.this, "手指按下", Toast.LENGTH_SHORT).show();
        // 当前在录制状态，可停止视频录制

        if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
            stopRecording();
        } else {
            mCurRecordVideoPath = PathUtils.getRecordVideoPath();
            if (mCurRecordVideoPath == null)
                return true;
//            mStartRecordingImage.setEnabled(false);

// 拍视频or拍照片
            if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
//                                mStartRecordingImage.setBackgroundResource(R.drawable.icon_pause);
                simulateProgress();
                mEachRecodingVideoTime = 0;
                //当前未在视频录制状态，则启动视频录制。此处使用带特效的录制方式
                if (!mStreamingContext.startRecording(mCurRecordVideoPath))
                    return true;
                isInRecording(false);
                mRecordFileList.add(mCurRecordVideoPath);
            } else if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
                mStreamingContext.startRecording(mCurRecordVideoPath);
                isInRecording(false);
            }
        }
        return false;
    }


    private void stopRecording() {
        mStreamingContext.stopRecording();
        mStartRecordingImage.setBackgroundResource(R.drawable.xiangji);

        // 拍视频
        if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
            mAllRecordingTime += mEachRecodingVideoTime;
            mRecordTimeList.add(mEachRecodingVideoTime);
//            mStartText.setText(mRecordTimeList.size() + "");
            isInRecording(true);
        }
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

    private void removeFilterFxByName(String name) {
        for (int i = 0; i < mStreamingContext.getCaptureVideoFxCount(); i++) {
            NvsCaptureVideoFx fx = mStreamingContext.getCaptureVideoFxByIndex(i);
            if (fx.getDescription().getName().equals(name)) {
                mStreamingContext.removeCaptureVideoFx(i);
            }
        }
    }

    /**
     * 显示窗口
     */
    private void showCaptureDialogView(AlertDialog dialog, View view) {
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        translate.setDuration(200);//动画时间500毫秒
        translate.setFillAfter(false);//动画出来控件可以点击
        mStartLayout.startAnimation(translate);
        dialog.show();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        params.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.colorTranslucent));
        dialog.getWindow().setWindowAnimations(R.style.fx_dlg_style);
        isShowCaptureButton(false);

    }

    /**
     * 关闭窗口
     */
    private void closeCaptureDialogView(AlertDialog dialog) {
        dialog.dismiss();
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translate.setDuration(300);//动画时间300毫秒
        translate.setFillAfter(false);//动画出来控件可以点击
        mStartLayout.setAnimation(translate);
        isShowCaptureButton(true);
        mSelectLayout.setVisibility(View.VISIBLE);
    }

    private void initCapture() {
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
            Log.e(TAG, "Failed to connect capture preview with livewindow!");
            return;
        }

        mCurrentDeviceIndex = 0;

        //采集设备数量判定
        if (mStreamingContext.getCaptureDeviceCount() > 1) {
            mSwitchFacingLayout.setEnabled(true);
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
        int captureResolutionGrade = ParameterSettingValues.instance().getCaptureResolutionGrade();
        if (mPermissionGranted && (deviceChanged || getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_CAPTUREPREVIEW)) {
            m_supportAutoFocus = false;
            if (!mStreamingContext.startCapturePreview(mCurrentDeviceIndex,
                    captureResolutionGrade,
                    NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                            NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME
                            | NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, null)) {
                Log.e(TAG, "Failed to start capture preview!");
                return false;
            }
        }
        return true;
    }


    // 获取当前引擎状态
    private int getCurrentEngineState() {
        return mStreamingContext.getStreamingEngineState();
    }

    private void updateSettingsWithCapability(int deviceIndex) {
        //获取采集设备能力描述对象，设置自动聚焦，曝光补偿，缩放
        mCapability = mStreamingContext.getCaptureDeviceCapability(deviceIndex);
        if (null == mCapability) {
            return;
        }

        //是否支持闪光灯
        if (mCapability.supportFlash) {
            mFlashLayout.setEnabled(true);
        }

        m_supportAutoFocus = mCapability.supportAutoFocus;

        // 是否支持缩放
       /* if (mCapability.supportZoom) {
            mZoomValue = mCapability.maxZoom;
            mZoomSeekbar.setMax(mZoomValue);
            mZoomSeekbar.setProgress(mStreamingContext.getZoom());
            mZoomSeekbar.setEnabled(true);
        } else {
            Log.e(TAG, "该设备不支持缩放");
        }*/

        // 是否支持曝光补偿
      /*  if (mCapability.supportExposureCompensation) {
            mMinExpose = mCapability.minExposureCompensation;
            mExposeSeekbar.setMax(mCapability.maxExposureCompensation - mMinExpose);
            mExposeSeekbar.setProgress(mStreamingContext.getExposureCompensation() - mMinExpose);
            mExposeSeekbar.setEnabled(true);
        }*/
    }

    private void isInRecording(boolean isInRecording) {
        int show;
        if (isInRecording) {
            show = View.VISIBLE;
//            mRecordTime.setTextColor(0xffffffff);
        } else {
//            mRecordTime.setTextColor(0xffD0021B);
            show = View.INVISIBLE;
        }
//        mCloseButton.setVisibility(show);
        mFunctionButtonLayout.setVisibility(show);
//        mDelete.setVisibility(show);
//        mNext.setVisibility(show);
//        mStartText.setVisibility(show);
        mSelectLayout.setVisibility(show);
        if (mRecordTimeList.isEmpty()) {
//            mRecordTime.setVisibility(View.INVISIBLE);
        } else {
//            mRecordTime.setVisibility(View.VISIBLE);
        }
    }

    private void isShowCaptureButton(boolean isShow) {
        int show;
        if (isShow) {
            show = View.VISIBLE;
        } else {
            show = View.GONE;
        }
//        mCloseButton.setVisibility(show);
        mFunctionButtonLayout.setVisibility(show);
        mStartLayout.setVisibility(show);
//        mRecordTime.setVisibility(show);
    }

    private ArrayList<NvAsset> getLocalData(int assetType) {
        return mAssetManager.getUsableAssets(assetType, NvAsset.AspectRatio_All, 0);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
       /* switch (id) {
            case R.id.more_filter_layout:
            case R.id.download_more_btn:
                //TODO
                AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), FilterDownloadActivity.class, null, FILTERREQUESTLIST);
                break;
        }*/
    }

    @Override
    public void onCaptureDeviceCapsReady(int captureDeviceIndex) {
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
        // 保存到媒体库
        if (mRecordFileList != null && !mRecordFileList.isEmpty()) {
            for (String path : mRecordFileList) {
                if (path == null) {
                    continue;
                }
                if (path.endsWith(".mp4")) {
                    MediaScannerUtil.scanFile(path, "video/mp4");
                } else if (path.endsWith(".jpg")) {
                    MediaScannerUtil.scanFile(path, "image/jpg");
                }
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
            }
            mEachRecodingVideoTime = l;
//            mRecordTime.setVisibility(View.VISIBLE);
//            mRecordTime.setText(TimeFormatUtil.formatUsToString2(mAllRecordingTime + mEachRecodingVideoTime));
        } else if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
            if (l > 40000) {
                stopRecording();
                takePhoto(l);
            }
        }
    }

    @Override
    public void onCaptureRecordingStarted(int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PermissionsActivity.PERMISSIONS_DENIED:
                mBuilderPermission = new Dialog(MyTestActivity.this, R.style.dialogTransparent);
                mPermissionDialog = (RelativeLayout) getLayoutInflater().inflate(R.layout.capture_permission_dialog, null);
                mOk = (TextView) mPermissionDialog.findViewById(R.id.ok);
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
            case RESULT_OK:
                if (requestCode == FILTERREQUESTLIST) {
                    initFilterList();
                    mFilterAdapter.setFilterDataList(mFilterItemArrayList);
                    mFilterAdapter.notifyDataSetChanged();
                }
                if (requestCode == ARFACEERREQUESTLIST) {
                    initPropList();
                 /*   mPropAdapter.setFilterDataList(mPropItemArrayList);
                    mPropAdapter.notifyDataSetChanged();*/
                }
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
    protected void onResume() {
        super.onResume();

        mStartRecordingImage.setVisibility(View.VISIBLE);
        mStartRecordingImage.setProgress(0);
        mStartRecordingImage.setClickable(true);
//        mStreamingContext.stopRecording();
//        mStreamingContext.resumeRecording();
        mSelectLayout.setVisibility(View.VISIBLE);
        if (mPermissionsChecker == null) {
            mPermissionsChecker = new PermissionsChecker(this);
        }
//        mNext.setClickable(true);
        mMoreFilterButton.setClickable(true);
        mMoreFilterLayout.setClickable(true);
        /*        mMoreProplLayout.setClickable(true);*//**/
        startCapturePreview(true);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mStartRecordingImage.setClickable(false);
        if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
            stopRecording();
        }
     animator.removeAllUpdateListeners();
        animator.cancel();
        /*   mLiveWindow.clearVideoFrame();
        mStreamingContext.pauseRecording();
        mStreamingContext.stopRecording();
        mStartRecordingImage.setVisibility(View.INVISIBLE);*/


    }

    @Override
    public void onRemoteAssetsChanged(boolean hasNext) {
        Log.e(TAG, "onRemoteAssetsChanged!");
        ArrayList<NvAsset> assets = mAssetManager.getRemoteAssets(NvAsset.ASSET_FILTER, NvAsset.AspectRatio_All, 1);
        ArrayList<NvAsset> arfaceassets = mAssetManager.getRemoteAssets(NvAsset.ASSET_FACE1_STICKER, NvAsset.AspectRatio_All, 1);
    }

    @Override
    public void onGetRemoteAssetsFailed() {

    }

    @Override
    public void onDownloadAssetProgress(String uuid, int progress) {

    }

    @Override
    public void onDonwloadAssetFailed(String uuid) {

    }

    @Override
    public void onDonwloadAssetSuccess(String uuid) {
        Log.e(TAG, "onDonwloadAssetSuccess!");

    }

    @Override
    public void onFinishAssetPackageInstallation(String uuid) {

    }

    @Override
    public void onFinishAssetPackageUpgrading(String uuid) {

    }

    private void takePhoto(long time) {
        if (mCurRecordVideoPath != null) {

            NvsVideoFrameRetriever videoFrameRetriever = mStreamingContext.createVideoFrameRetriever(mCurRecordVideoPath);
            if (videoFrameRetriever != null) {
                mPictureBitmap = videoFrameRetriever.getFrameAtTimeWithCustomVideoFrameHeight(time, ScreenUtils.getScreenHeight(this));
                Log.e("===>", "screen: " + ScreenUtils.getScreenWidth(this) + " " + ScreenUtils.getScreenHeight(this));
                Log.e("===>", "picture: " + mPictureBitmap.getWidth() + " " + mPictureBitmap.getHeight());
                if (mPictureBitmap != null) {
//                    mPictureImage.setImageBitmap(mPictureBitmap);
                    if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
                        mAllRecordingTime += mEachRecodingImageTime;
                        mRecordTimeList.add(mEachRecodingImageTime);
//                    mRecordTime.setText(TimeFormatUtil.formatUsToString2(mAllRecordingTime));
//                    mStartText.setText(mRecordTimeList.size() + "");
                        isInRecording(true);
                    }
                    String jpgPath = PathUtils.getRecordPicturePath();
                    Log.d("pic", jpgPath);
                    boolean save_ret = Util.saveBitmapToSD(mPictureBitmap, jpgPath);
                    if (save_ret) {
                        mRecordFileList.add(jpgPath);
                    }
                    if (mCurRecordVideoPath != null) {
                        File file = new File(mCurRecordVideoPath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    Intent intent = new Intent(MyTestActivity.this, MainActivity.class);
                    intent.putExtra("path", jpgPath);
                    startActivity(intent);
//                    showPictureLayout(true);
                }
            }
        }
    }

    private void takePhoto2(long time) {
        RequestOptions requestOptions = RequestOptions.frameOf(time);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                Log.e("===>", "screen: " + ScreenUtils.getScreenWidth(MyTestActivity.this) + " " + ScreenUtils.getScreenHeight(MyTestActivity.this));
                Log.e("===>", "glide: " + outWidth + " " + outHeight + " " + toTransform.getWidth() + " " + toTransform.getHeight());
                mPictureBitmap = toTransform;
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(this).load(mCurRecordVideoPath).apply(requestOptions).into(mPictureImage);
        showPictureLayout(true);
    }

    private void selectRecordType(boolean left_to_right) {
        TranslateAnimation ani;
        if (left_to_right) {
            if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
                return;
            }
            ani = new TranslateAnimation(mTypePictureBtn.getX(), mTypeVideoBtn.getX(), 0, 0);
            mTypePictureBtn.setTextColor(ContextCompat.getColor(MyTestActivity.this, R.color.color_orange));
            mTypeVideoBtn.setTextColor(ContextCompat.getColor(MyTestActivity.this, R.color.white));
            mRecordType = Constants.RECORD_TYPE_PICTURE;
        } else {
            ani = new TranslateAnimation(mTypeVideoBtn.getX(), mTypePictureBtn.getX(), 0, 0);
            mTypePictureBtn.setTextColor(ContextCompat.getColor(MyTestActivity.this, R.color.white));
            mTypeVideoBtn.setTextColor(ContextCompat.getColor(MyTestActivity.this, R.color.color_orange));
            mRecordType = Constants.RECORD_TYPE_VIDEO;
        }
        ani.setDuration(300);
        ani.setFillAfter(true);
        mRecordTypeLayout.startAnimation(ani);
    }

    private void showPictureLayout(boolean show) {
        TranslateAnimation topTranslate;
        if (show) {
            mPictureLayout.setVisibility(View.INVISIBLE);
            topTranslate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            topTranslate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPictureLayout.clearAnimation();
//                    mCloseButton.setVisibility(View.GONE);
                    mPictureLayout.setVisibility(View.VISIBLE);
                    mPictureLayout.setClickable(true);
                    mPictureLayout.setFocusable(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {
//            mCloseButton.setVisibility(View.VISIBLE);
            topTranslate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

            topTranslate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPictureLayout.clearAnimation();
                    mPictureLayout.setVisibility(View.GONE);
                    mPictureLayout.setClickable(false);
                    mPictureLayout.setFocusable(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        topTranslate.setDuration(300);
        topTranslate.setFillAfter(true);
        mPictureLayout.setAnimation(topTranslate);
    }

    private void checkAllPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        } else {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE);
                        }
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION_CODE);
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            }
        } else {

        }
    }

}  

