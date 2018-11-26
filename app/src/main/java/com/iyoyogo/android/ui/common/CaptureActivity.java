package com.iyoyogo.android.ui.common;


import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.media.ImgFolderBean;
import com.iyoyogo.android.bean.media.Video;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.edit.clipEdit.EditActivity;
import com.iyoyogo.android.camera.edit.filter.FilterAdapter;
import com.iyoyogo.android.camera.edit.filter.FilterDownloadActivity;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.MediaScannerUtil;
import com.iyoyogo.android.camera.utils.ParameterSettingValues;
import com.iyoyogo.android.camera.utils.PathUtils;
import com.iyoyogo.android.camera.utils.ScreenUtils;
import com.iyoyogo.android.camera.utils.SpUtil;
import com.iyoyogo.android.camera.utils.TimeFormatUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.camera.utils.authpack;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.permission.PermissionsActivity;
import com.iyoyogo.android.camera.utils.permission.PermissionsChecker;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.FileManager;
import com.iyoyogo.android.view.CircleProgressBar;
import com.meicam.sdk.NvsAVFileInfo;
import com.meicam.sdk.NvsCaptureVideoFx;
import com.meicam.sdk.NvsFxDescription;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsSize;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsVideoFrameRetriever;
import com.meicam.sdk.NvsVideoStreamInfo;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CaptureActivity extends BaseActivity implements NvsStreamingContext.CaptureDeviceCallback,
        NvsStreamingContext.CaptureRecordingDurationCallback,
        View.OnClickListener,
        NvsStreamingContext.CaptureRecordingStartedCallback, NvAssetManager.NvAssetManagerListener {
    private static final String TAG = "Capture";



    private RelativeLayout filterLayout;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 0;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;
    private ImageView bili_one,bili_four,bili_sixteen,bili_three,bili_full;
    public static final int CAPTURE_TYPE_ZOOM = 2;
    public static final int CAPTURE_TYPE_EXPOSE = 3;
    private static final int FILTERREQUESTLIST = 110;
    private static final int ARFACEERREQUESTLIST = 111;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE = 203;
    private final int MIN_RECORD_DURATION = 1000000;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private NvsLiveWindow mLiveWindow;
    private NvsStreamingContext mStreamingContext;
private boolean isTrue=false;
    private LinearLayout mFunctionButtonLayout;
    private ImageView btnCameraSwitch;
    private ImageView flash;
    private ImageView mFlashButton;
    private LinearLayout mZoomLayout;
    private LinearLayout mExposureLayout;
    private LinearLayout mBeautyLayout;
    private ImageView samePara;
    private LinearLayout mFuLayout;
    private TextView mOk;
    private RelativeLayout mMainLayout;
    private RelativeLayout mStartLayout;
    private CircleProgressBar mCapture;
    //    private TextView mStartText;
    private ImageView album;
    private ImageView filter;
    private TextView mRecordTime;
    private TextView mSeekTitle;
    private TextView mSeekProgress;
    private ImageView mImageAutoFocusRect;
    private ExecutorService executorService;
    /*拍照or视频*/
    private RelativeLayout mSelectLayout, mPictureLayout;
    private LinearLayout mRecordTypeLayout;
    private View mTypeRightView;
    private Button mTypePictureBtn, mTypeVideoBtn, mPictureCancel, mPictureOk;
    private int mRecordType = Constants.RECORD_TYPE_VIDEO;
    private ImageView mPictureImage;
    private Bitmap mPictureBitmap;

    /*录制*/
    private ArrayList<Long> mRecordTimeList;
    private ArrayList<String> mRecordFileList = new ArrayList<String>();
    private long mEachRecodingVideoTime = 0, mEachRecodingImageTime = 4000000;
    private long mAllRecordingTime = 0;
    private String mCurRecordVideoPath;
    private ImageView captures;

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
    private static final int maxTime = 15000;//最长录制20s
    /*滤镜*/
    private AlertDialog mCaptureFilterDialog;
    private View mFilterView;
    private RecyclerView mFilterRecyclerView;
    private FilterAdapter mFilterAdapter;
//    private RelativeLayout mMoreFilterLayout;
    private ArrayList<FilterItem> mFilterItemArrayList = new ArrayList<>();
//    private ImageButton mMoreFilterButton;
    private RelativeLayout intensity_layout;
    private SeekBar mFilterIntensitySeekBar;
    private View mFilterIntensitySeekBarClickView;
    private NvsCaptureVideoFx mCurCaptureVideoFx;
    private ValueAnimator animator;
    private NvAssetManager mAssetManager;
    private RelativeLayout photoLayout;
    private boolean m_canUseARFace = false; // 人脸特效是否可用的标识
    private boolean m_supportAutoFocus = false; // 是否支持自动聚焦
    private SpUtil mSp;
    private ContentResolver mContentResolver;
    private int captureResolutionGrade;
    //





    @Override
    protected int getLayoutId() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return R.layout.activity_capture;
        }
        //初始化
        String licensePath = "assets:/meishesdk.lic";

        NvsStreamingContext.init(this, licensePath, NvsStreamingContext.STREAMING_CONTEXT_FLAG_SUPPORT_4K_EDIT);
        NvAssetManager.init(CaptureActivity.this);//素材管理器初始化
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_capture;
    }
    //生成圆角图片
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = 14;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        }






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initView() {
        mContentResolver = getContentResolver();
        animator = ValueAnimator.ofInt(0, 100);
        checkAllPermission();
        captureResolutionGrade = ParameterSettingValues.instance().getCaptureResolutionGrade();
        List<String> mPhoto=new ArrayList<>();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        List<Video> videos = FileManager.getInstance(this).getVideos();
        for (int i = 0; i < videos.size(); i++) {
            String path = videos.get(i).getPath();
            Log.d("TAG", path);
        }
        List<ImgFolderBean> imageFolders = FileManager.getInstance(this).getImageFolders();
        for (int i = 0; i < imageFolders.size(); i++) {
            String fistImgPath = imageFolders.get(i).getFistImgPath();
            Log.d("TAG", fistImgPath);
        }


        samePara=findViewById(R.id.same_para);
        intensity_layout=findViewById(R.id.intensity_layout);
        /*滤镜dialog*/
        bili_one=findViewById(R.id.bili_one);
        bili_four=findViewById(R.id.bili_four);
        bili_sixteen=findViewById(R.id.bili_sixteen);
        bili_three=findViewById(R.id.bili_three);
        bili_full=findViewById(R.id.bili_full);
        album=findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CaptureActivity.this,PictureEditActivity.class));
            }
        });
        photoLayout = findViewById(R.id.photo_layout);
        filterLayout=findViewById(R.id.filter_layout);
//        LayoutInflater filterInflater = LayoutInflater.from(this);
//        mFilterView = filterInflater.inflate(R.layout.filter_view, null);
        mFilterRecyclerView = (RecyclerView) findViewById(R.id.filterBar);
//        mMoreFilterLayout = (RelativeLayout) findViewById(R.id.more_filter_layout);
//        mMoreFilterButton = (ImageButton) mFilterView.findViewById(R.id.download_more_btn);
        mFilterIntensitySeekBar = (SeekBar) findViewById(R.id.intensitySeekBar);
        mFilterIntensitySeekBarClickView =findViewById(R.id.seekbar_enable_click_view);
        mFilterIntensitySeekBar.setEnabled(false);
        mFilterIntensitySeekBar.setMax(100);
        mFilterIntensitySeekBar.setProgress(100);
        filter = findViewById(R.id.filter);
        captures = findViewById(R.id.captures);
        /*道具dialog*/
        LayoutInflater propInflater = LayoutInflater.from(this);
//        mPropView = propInflater.inflate(R.layout.prop_view, null);
//        mPropRecyclerView = (RecyclerView) mPropView.findViewById(R.id.propBar);
//        mMoreProplLayout = (RelativeLayout) mPropView.findViewById(R.id.more_prop_layout);
//mPropView.setVisibility(View.GONE);
        mRecordTime = (TextView) findViewById(R.id.recordTime);
        mImageAutoFocusRect = (ImageView) findViewById(R.id.imageAutoFocusRect);

        mStartLayout = (RelativeLayout) findViewById(R.id.startLayout);
        mCapture = (CircleProgressBar) findViewById(R.id.capture);
//        mStartText = (TextView) findViewById(R.id.startText);
        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mLiveWindow = (NvsLiveWindow) findViewById(R.id.liveWindow);
//        mCloseButton = (Button) findViewById(R.id.closeButton);
        mFunctionButtonLayout = (LinearLayout) findViewById(R.id.functionLayout);
        btnCameraSwitch = (ImageView) findViewById(R.id.btn_camera_switch);
        flash = (ImageView) findViewById(R.id.flash);
//        mFlashButton = (ImageView) findViewById(R.id.flashButton);
//        mZoomLayout = (LinearLayout) findViewById(R.id.zoomLayout);
//        mExposureLayout = (LinearLayout) findViewById(R.id.exposureLayout);
//        mBeautyLayout = (LinearLayout) findViewById(R.id.beautyLayout);
//        filter = (ImageView) findViewById(R.id.filterLayout);
//        mFuLayout = (LinearLayout) findViewById(R.id.fuLayout);

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

        mCaptureFilterDialog = new AlertDialog.Builder(this).create();
        mCaptureFilterDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                closeCaptureDialogView(mCaptureFilterDialog);
            }
        });
       /* mCapturePropDialog = new AlertDialog.Builder(this).create();
        mCapturePropDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                closeCaptureDialogView(mCapturePropDialog);
            }
        });*/
        mCapture.setMax(100);
//        Bitmap bitmap1 = setImgSize(BitmapFactory.decodeFile(lastPhotoByPath), DensityUtil.dp2px(CaptureActivity.this, 44), DensityUtil.dp2px(CaptureActivity.this, 44));
//        Glide.with(this).load(bitmap1).into(album);
    }
    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    private void simulateProgress() {

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mCapture.setProgress(progress);
                if (mCapture.getProgress() == 100) {
                    stopRecording();
                    complete();


                }
            }
        });
        animator.setDuration(10000);
        animator.start();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    private void initFilterList() {
        int[] resImags = {
                R.mipmap.sage, R.mipmap.maid, R.mipmap.mace,
                R.mipmap.lace, R.mipmap.mall, R.mipmap.sap, R.mipmap.sara,
                R.mipmap.pinky, R.mipmap.sweet, R.mipmap.fresh, R.mipmap.package1
        };

        mFilterItemArrayList.clear();
        FilterItem filterItem = new FilterItem();
        filterItem.setFilterName("原图");
        filterItem.setImageId(R.mipmap.no);
        mFilterItemArrayList.add(filterItem);

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
//        builtinFilterList.clear();
//        builtinFilterList.add("圣人");
//        builtinFilterList.add("少女");
//        builtinFilterList.add("豆蔻");
//        builtinFilterList.add("花边");
//        builtinFilterList.add("商业");
//        builtinFilterList.add("元气");
//        builtinFilterList.add("萨拉");
//        builtinFilterList.add("粉红");
//        builtinFilterList.add("糖系");
//        builtinFilterList.add("新鲜");
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
//        mPropItemArrayList.clear();
        FilterItem filterItem = new FilterItem();
        filterItem.setFilterName("");
        filterItem.setImageId(R.mipmap.no);
//        mPropItemArrayList.add(filterItem);

        ArrayList<NvAsset> filterList = getLocalData(NvAsset.ASSET_FACE1_STICKER);
        for (NvAsset asset : filterList) {
            FilterItem newFilterItem = new FilterItem();
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_PACKAGE);
            newFilterItem.setFilterName(asset.localDirPath);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
//            mPropItemArrayList.add(newFilterItem);
        }

        filterList = getLocalData(NvAsset.ASSET_FACE_BUNDLE_STICKER);
        for (NvAsset asset : filterList) {
            FilterItem newFilterItem = new FilterItem();
            newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUNDLE);
            newFilterItem.setFilterName(asset.bundledLocalDirPath);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
//            mPropItemArrayList.add(newFilterItem);
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

    private void initPropRecyclerView() {
//        mPropAdapter = new FilterAdapter(this);
//        mPropAdapter.isArface(true);
//        mPropAdapter.setFilterDataList(mPropItemArrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mPropRecyclerView.setLayoutManager(linearLayoutManager);
//        mPropRecyclerView.setAdapter(mPropAdapter);

/*        mPropAdapter.setOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mPropItemArrayList.size()) {
                    if (position == 0) {
                        mARFace.setStringVal("Face Ornament", "");
                    } else {
                        FilterItem item = mPropItemArrayList.get(position);
                        mARFace.setStringVal("Face Ornament", item.getFilterName());
                    }
                }
            }

            @Override
            public void onSameItemClick() {

            }
        });*/
    }

    private void beautySeekEnabled(Boolean isEnabled) {
//        mStrength.setEnabled(isEnabled);
//        mStrength.setClickable(isEnabled);
//        mWhitening.setEnabled(isEnabled);
//        mWhitening.setClickable(isEnabled);
//        mReddening.setEnabled(isEnabled);
//        mReddening.setClickable(isEnabled);
    }

    private void beautyShapeSeekEnabled(Boolean isEnabled) {
//        mLevel.setEnabled(isEnabled);
//        mLevel.setClickable(isEnabled);
//        mBigeye.setEnabled(isEnabled);
//        mBigeye.setClickable(isEnabled);
//        mShape_face.setEnabled(isEnabled);
//        mShape_face.setClickable(isEnabled);
    }

    private void initBeautyData() {
//        mStrengthValue = 0;
//        mWhiteningValue = 0;
//        mReddeningValue = 0;
        NvsFxDescription fxDescription = mStreamingContext.getVideoFxDescription("Beauty");
        List<NvsFxDescription.ParamInfoObject> paramInfo = fxDescription.getAllParamsInfo();
        for (NvsFxDescription.ParamInfoObject param : paramInfo) {
            String paramName = param.getString("paramName");
            if (paramName.equals("Strength")) {
                double maxValue = param.getFloat("floatMaxVal");
//                mStrengthValue = param.getFloat("floatDefVal");
//                Log.e("mStrengthValue=", mStrengthValue + "");
//                mStrength.setMax((int) (maxValue * 100));
//                mStrength.setProgress((int) (mStrengthValue * 100));
//                mStrength_text.setText(String.format(Locale.getDefault(), "%.2f", mStrengthValue));
            } else if (paramName.equals("Whitening")) {
                double maxValue = param.getFloat("floatMaxVal");
//                mWhiteningValue = param.getFloat("floatDefVal");
//                Log.e("mWhiteningValue=", mWhiteningValue + "");
//                mWhitening.setMax((int) (maxValue * 100));
//                mWhitening.setProgress((int) (mWhiteningValue * 100));
//                mWhitening_text.setText(String.format(Locale.getDefault(), "%.2f", mWhiteningValue));
            } else if (paramName.equals("Reddening")) {
                double maxValue = param.getFloat("floatMaxVal");
//                mReddeningValue = param.getFloat("floatDefVal");
//                Log.e("mReddeningValue=", mReddeningValue + "");
//                mReddening.setMax((int) (maxValue * 100));
//                mReddening.setProgress((int) (mReddeningValue * 100));
//                mReddening_text.setText(String.format(Locale.getDefault(), "%.2f", mReddeningValue));
            }
        }

        // 美型参数
//        mBigeye.setMax(100);
//        mShape_face.setMax(100);
//        mLevel.setMax(100);
//        mBigeye.setProgress(0);
//        mShape_face.setProgress(0);
//        mLevel.setProgress(0);
//        mLevel_text.setText(String.format(Locale.getDefault(), "%.2f", mLevelValue));
//        mBigeye_text.setText(String.format(Locale.getDefault(), "%.2f", mBigeyeValue));
//        mShape_face_text.setText(String.format(Locale.getDefault(), "%.2f", mShapeFaceValue));

        //  人脸特效是否可用
        if (m_canUseARFace) {
//            mARFace = mStreamingContext.appendBuiltinCaptureVideoFx("Face Effect");
//            mARFace.setStringVal("Beautification Package", "assets:/NvBeautification.asset");
//            if (mARFace != null) {
//                mARFace.setMenuVal("Face Type", "");
//                mARFace.setFloatVal("Face Shape Level", 0);
//                mARFace.setFloatVal("Eye Enlarging", 0);
//                mARFace.setFloatVal("Cheek Thinning", 0);
//            }
        }
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mSp = SpUtil.getInstance(getApplicationContext());
        samePara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaptureActivity.this, SameParaActivity.class);
                startActivity(intent);
            }
        });
        bili_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODo
                int screenWidth = ScreenUtils.getScreenWidth(CaptureActivity.this);


                RelativeLayout.LayoutParams layoutParams =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,screenWidth);
                layoutParams.height = screenWidth;
              layoutParams.setMargins(0,100,0,100);
//                layoutParams.width = width;
                mLiveWindow.setLayoutParams(layoutParams);

                bili_full.setVisibility(View.GONE);
                bili_one.setVisibility(View.VISIBLE);
                mStreamingContext.startCapturePreview(mCurrentDeviceIndex,
                        captureResolutionGrade,
                        NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                                NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME
                                | NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, new NvsRational(1,1));
                bili_three.setVisibility(View.GONE);
                bili_four.setVisibility(View.GONE);
                bili_sixteen.setVisibility(View.GONE);
            }
        });
        bili_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int screenWidth = ScreenUtils.getScreenWidth(CaptureActivity.this);
                int screenHeight=screenWidth*4/3;

                RelativeLayout.LayoutParams layoutParams =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,screenHeight);
                layoutParams.height = screenHeight;
//                layoutParams.width = width;
                layoutParams.setMargins(0,100,0,0);
                mLiveWindow.setLayoutParams(layoutParams);

                bili_full.setVisibility(View.GONE);
                bili_one.setVisibility(View.GONE);
                bili_three.setVisibility(View.GONE);
                mStreamingContext.startCapturePreview(mCurrentDeviceIndex,
                        captureResolutionGrade,
                        NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                                NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME
                                | NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, new NvsRational(4,3));
                bili_four.setVisibility(View.VISIBLE);
                bili_sixteen.setVisibility(View.GONE);
            }
        });
        bili_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = CaptureActivity.this.getResources().getDisplayMetrics().widthPixels / 4;
                int height =  CaptureActivity.this.getResources().getDisplayMetrics().heightPixels / 3;
                ViewGroup.LayoutParams layoutParams = mLiveWindow.getLayoutParams();
                layoutParams.height = height;
//                layoutParams.width = width;
                mLiveWindow.setLayoutParams(layoutParams);

                bili_full.setVisibility(View.GONE);
                bili_one.setVisibility(View.GONE);
                bili_three.setVisibility(View.GONE);
                bili_four.setVisibility(View.GONE);
                bili_sixteen.setVisibility(View.GONE);
            }
        });
        bili_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams layoutParams =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
//                layoutParams.width = width;
                mLiveWindow.setLayoutParams(layoutParams);
                mStreamingContext.startCapturePreview(mCurrentDeviceIndex,
                        captureResolutionGrade,
                        NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                                NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME
                                | NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, new NvsRational(9,16));
                bili_full.setVisibility(View.VISIBLE);
                bili_one.setVisibility(View.GONE);
                bili_three.setVisibility(View.GONE);
                bili_four.setVisibility(View.GONE);
                bili_sixteen.setVisibility(View.GONE);
            }
        });
        bili_sixteen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = mLiveWindow.getLayoutParams();
                layoutParams.height=ViewGroup.LayoutParams.MATCH_PARENT;

                mLiveWindow.setLayoutParams(layoutParams);
                bili_full.setVisibility(View.GONE);
                bili_one.setVisibility(View.GONE);
                bili_three.setVisibility(View.GONE);
                bili_four.setVisibility(View.GONE);
                bili_sixteen.setVisibility(View.GONE);
            }
        });

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

        initBeautyData();
        initFilterList();
        initFilterRecyclerView();
        initPropList();
        initPropRecyclerView();
        mLiveWindow.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceType")
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
        mLiveWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = filterLayout.getVisibility();
                if (visibility==View.VISIBLE){
                    startAnimation(photoLayout);
                    endAnimation(filterLayout);

                    mStartLayout.setBackgroundResource(R.color.transparent);
                    filterLayout.setVisibility(View.GONE);
                    intensity_layout.setVisibility(View.GONE);
                }


            }
        });
        /*变焦调节*/
/*        mZoomSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private boolean startTracking = false;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (startTracking) {
                    if (mCaptureType == CAPTURE_TYPE_ZOOM)
                        mStreamingContext.setZoom(progress);//设置缩放比例
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startTracking = false;
            }
        });*/
        /*曝光补偿调节*/
       /* mExposeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mCaptureType == CAPTURE_TYPE_EXPOSE) {
                    mStreamingContext.setExposureCompensation(progress + mMinExpose);//设置曝光补偿
                    mSeekProgress.setText(progress + mMinExpose + "");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
//        mCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        /*切换摄像头*/
        btnCameraSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSwitchingCamera) {
                    return;
                }
                if (mCurrentDeviceIndex == 0) {
                    mCurrentDeviceIndex = 1;
                    flash.setEnabled(false);
                    flash.setImageResource(R.mipmap.icon_flash_off);
                    flash.setImageAlpha(128);
                } else {
                    mCurrentDeviceIndex = 0;
                    flash.setEnabled(true);
                    flash.setImageResource(R.mipmap.icon_flash_off);
                    flash.setImageAlpha(255);
                }
                mCenterX = ScreenUtils.getScreenWidth(CaptureActivity.this) / 2;
                mCenterY = ScreenUtils.getScreenHeight(CaptureActivity.this) / 2;

                mIsSwitchingCamera = true;
                startCapturePreview(true);
            }
        });
        /*闪光灯*/
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStreamingContext.isFlashOn()) {
                    mStreamingContext.toggleFlash(false);
                    flash.setImageResource(R.mipmap.icon_flash_off);
                    flash.setImageAlpha(255);
                } else {
                    mStreamingContext.toggleFlash(true);
                    flash.setImageResource(R.mipmap.icon_flash_on);
                    flash.setImageAlpha(255);
                }
            }
        });
        /*变焦*/
  /*      mZoomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaptureType = CAPTURE_TYPE_ZOOM;
                mSeekTitle.setText("画面变焦");
                mSeekProgress.setVisibility(View.INVISIBLE);
                mZoomSeekbar.setVisibility(View.VISIBLE);_
                mExposeSeekbar.setVisibility(View.INVISIBLE);
                showCaptureDialogView(mCaptureZoomAndExposeDialog, mZoomView);
            }
        });*/
        /*曝光补偿*/
       /* mExposureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaptureType = CAPTURE_TYPE_EXPOSE;
                mSeekTitle.setText("曝光补偿");
                mSeekProgress.setVisibility(View.VISIBLE);
                mSeekProgress.setText(mExposeSeekbar.getProgress() + mMinExpose + "");
                mZoomSeekbar.setVisibility(View.INVISIBLE);
                mExposeSeekbar.setVisibility(View.VISIBLE);
                showCaptureDialogView(mCaptureZoomAndExposeDialog, mZoomView);
            }
        });*/
        /*美颜*/
        /*mBeautyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCaptureDialogView(mCaptureBeautyDialog, mBeautyView);
            }
        });*/
        /*滤镜*/

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupWindow popupWindow = new PopupWindow(mFilterView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
//                popupWindow. showAtLocation(mStartLayout, Gravity.BOTTOM, 0, 0);
////                        .showAsDropDown(mImageAutoFocusRect,0,0);
//
//                popupWindow.setAnimationStyle(R.style.popwin_anim_style);





//                mStartLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dp2px(CaptureActivity.this,200)));
               /* AnimationSet aset_3 = new AnimationSet(true);
                ScaleAnimation aa_3 = new ScaleAnimation(1, 1f, 1, 0.8f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
                aa_3.setDuration(2000);
                aset_3.addAnimation(aa_3);
                photoLayout.startAnimation(aset_3);*/
                int visibility = filterLayout.getVisibility();
                    if (visibility==View.VISIBLE){
                        startAnimation(photoLayout);
                        endAnimation(filterLayout);

                        mStartLayout.setBackgroundResource(R.color.transparent);
                        filterLayout.setVisibility(View.GONE);
                        intensity_layout.setVisibility(View.GONE);
                    }else {
                    startAnimation(filterLayout);
                    endAnimation(photoLayout);
                    mStartLayout.setBackgroundResource(R.color.white);
                    filterLayout.setVisibility(View.VISIBLE);
                    intensity_layout.setVisibility(View.VISIBLE);

                }

//                showCaptureDialogView(mCaptureFilterDialog, mFilterView);
            }
        });

        /*道具*/
      /*  mFuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!m_canUseARFace) {
                    Util.showDialog(CaptureActivity.this, "提示", "请移步官网，联系商务人员索要有人脸识别授权的版本");
                } else {
                    showCaptureDialogView(mCapturePropDialog, mPropView);
                }
            }
        });*/
        /*开始录制*/
        captures.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                startRecording();
                mStartLayout.setBackgroundResource(Color.TRANSPARENT);
                filterLayout.setVisibility(View.GONE);
                intensity_layout.setVisibility(View.GONE);
                mCapture.setVisibility(View.VISIBLE);
                captures.setVisibility(View.GONE);
            }
        });
        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                complete();
                captures.setVisibility(View.VISIBLE);
                mCapture.setVisibility(View.GONE);

                // 当前在录制状态，可停止视频录制
            }
        });
        /*删除视频*/
//        mDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mRecordTimeList.size() != 0 && mRecordFileList.size() != 0) {
//                    mAllRecordingTime -= mRecordTimeList.get(mRecordTimeList.size() - 1);
//                    mRecordTimeList.remove(mRecordTimeList.size() - 1);
//                    PathUtils.deleteFile(mRecordFileList.get(mRecordFileList.size() - 1));
//                    mRecordFileList.remove(mRecordFileList.size() - 1);
//                    mRecordTime.setText(TimeFormatUtil.formatUsToString2(mAllRecordingTime));
//
//                    if (mRecordTimeList.size() == 0) {
//                        mStartText.setVisibility(View.GONE);
//                        mDelete.setVisibility(View.GONE);
//                        mNext.setVisibility(View.GONE);
//                        mRecordTime.setVisibility(View.INVISIBLE);
//                    } else {
//                        mStartText.setText(mRecordTimeList.size() + "");
//                        mRecordTime.setVisibility(View.VISIBLE);
//                    }
//                }
//
//            }
//        });
        /*下一步，进入编辑*/
//        mNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*将拍摄的视频传到下一个页面mRecordFileList*/
//
//            }
//        });

//        mMoreFilterLayout.setOnClickListener(this);
//        mMoreFilterButton.setOnClickListener(this);

       /* mMoreProplLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreProplLayout.setClickable(false);
                //TODO
                AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), EmptyActivity.class, null, ARFACEERREQUESTLIST);
            }
        });*/

        mTypePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecordType(true);
            }
        });

//        mTypeVideoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectRecordType(false);
//            }
//        });

        mTypeRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRecordType(false);
            }
        });

//        mPictureCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurRecordVideoPath != null) {
//                    File file = new File(mCurRecordVideoPath);
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                }
//                showPictureLayout(false);
//                isInRecording(true);
//                if (mRecordTimeList.isEmpty()) {
////                    mDelete.setVisibility(View.INVISIBLE);
////                    mNext.setVisibility(View.INVISIBLE);
////                    mStartText.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

//        mPictureOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 拍照片
//
//                showPictureLayout(false);
//            }
//        });

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
                    Toast.makeText(CaptureActivity.this, "请选择一个滤镜", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startRecording() {
        if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
            stopRecording();
        } else {
            mCurRecordVideoPath = PathUtils.getRecordVideoPath();
            if (mCurRecordVideoPath == null)
                return;
            mCapture.setEnabled(false);

            // 拍视频or拍照片
            if (mRecordType == Constants.RECORD_TYPE_VIDEO) {
                mEachRecodingVideoTime = 0;
                simulateProgress();
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
    }

    /* 美颜dialog 动作监听*/
    private void beautyClickListener() {
        /*美颜*/
    /*    mBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsBeautyType) {
                    mIsBeautyType = true;
                    mBeauty.setSelected(true);
                    mBeauty_shape.setSelected(false);
                    mBeautySelect.setVisibility(View.VISIBLE);
                    mBeautyShapeSelect.setVisibility(View.GONE);
                }
            }
        });*/
        /*美型*/
       /* mBeauty_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsBeautyType) {
                    mIsBeautyType = false;
                    mBeauty.setSelected(false);
                    mBeauty_shape.setSelected(true);
                    mBeautySelect.setVisibility(View.GONE);
                    mBeautyShapeSelect.setVisibility(View.VISIBLE);
                }
            }
        });*/

        /*美颜*/
       /* mBeauty_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NvsCaptureVideoFx fx = mStreamingContext.appendBeautyCaptureVideoFx();   //添加美颜采集特效
                    fx.setFloatVal("Strength", mStrengthValue);//设置美颜强度值
                    fx.setFloatVal("Whitening", mWhiteningValue);
                    fx.setFloatVal("Reddening", mReddeningValue);
                    mBeauty_switch_text.setText("关闭美颜");
                } else {
                    mBeauty_switch_text.setText("开启美颜");
                    removeFilterFxByName("Beauty");
                }
                mBeauty_switch.setChecked(isChecked);
                beautySeekEnabled(isChecked);
            }
        });*/
        /*磨皮*/
     /*   mStrength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mStrengthValue = progress * 0.01;
                mStrength_text.setText(String.format(Locale.getDefault(), "%.2f", mStrengthValue));

                for (int i = 0; i < mStreamingContext.getCaptureVideoFxCount(); i++) {
                    NvsCaptureVideoFx fx = mStreamingContext.getCaptureVideoFxByIndex(i);
                    String name = fx.getBuiltinCaptureVideoFxName();
                    if (name.equals("Beauty")) {
                        //设置美颜强度值
                        fx.setFloatVal("Strength", mStrengthValue);
                        break;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        /*美白*/
      /*  mWhitening.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWhiteningValue = progress * 0.01;
                mWhitening_text.setText(String.format(Locale.getDefault(), "%.2f", mWhiteningValue));

                for (int i = 0; i < mStreamingContext.getCaptureVideoFxCount(); i++) {
                    NvsCaptureVideoFx fx = mStreamingContext.getCaptureVideoFxByIndex(i);
                    String name = fx.getBuiltinCaptureVideoFxName();
                    if (name.equals("Beauty")) {
                        //设置美颜强度值
                        fx.setFloatVal("Whitening", mWhiteningValue);
                        break;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        /*红润*/
       /* mReddening.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mReddeningValue = progress * 0.01;
                mReddening_text.setText(String.format(Locale.getDefault(), "%.2f", mReddeningValue));

                for (int i = 0; i < mStreamingContext.getCaptureVideoFxCount(); i++) {
                    NvsCaptureVideoFx fx = mStreamingContext.getCaptureVideoFxByIndex(i);
                    String name = fx.getBuiltinCaptureVideoFxName();
                    if (name.equals("Beauty")) {
                        //设置美颜强度值
                        fx.setFloatVal("Reddening", mReddeningValue);
                        break;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

        // 美型开关
     /*   mBeauty_shape_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!m_canUseARFace) {
                    mBeauty_shape_switch.setChecked(false);
                    Util.showDialog(CaptureActivity.this, "提示", "请移步官网，联系商务人员索要有人脸识别授权的版本");
                } else {
                    if (isChecked) {
                        mARFace.setMenuVal("Face Type", "Default");
                        mARFace.setFloatVal("Face Shape Level", mLevelValue);
                        mARFace.setFloatVal("Eye Enlarging", mBigeyeValue);
                        mARFace.setFloatVal("Cheek Thinning", mShapeFaceValue);
                        mBeauty_shape_switch_text.setText("关闭美型");
                    } else {
                        mARFace.setMenuVal("Face Type", "");
                        mARFace.setFloatVal("Face Shape Level", 0);
                        mARFace.setFloatVal("Eye Enlarging", 0);
                        mARFace.setFloatVal("Cheek Thinning", 0);
                        mBeauty_shape_switch_text.setText("开启美型");
                    }
                    mBeauty_shape_switch.setChecked(isChecked);
                    beautyShapeSeekEnabled(isChecked);
                }
            }
        });*/

        /*程度*/
        /*mLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mARFace == null) {
                    return;
                }
                mLevelValue = ((float) progress) / ((float) 100);
                Log.e("===>", "level: " + mLevelValue);
                mARFace.setFloatVal("Face Shape Level", mLevelValue);
                mLevel_text.setText(String.format(Locale.getDefault(), "%.2f", mLevelValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        /*大眼*/
        /*mBigeye.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mARFace == null) {
                    return;
                }
                mBigeyeValue = (float) progress / 100;
                Log.e("===>", "da yan: " + mBigeyeValue);
                mARFace.setFloatVal("Eye Enlarging", mBigeyeValue);
                mBigeye_text.setText(String.format(Locale.getDefault(), "%.2f", mBigeyeValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        /*瘦脸*/
      /*  mShape_face.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (mARFace == null) {
                        return;
                    }
                    mShapeFaceValue = ((float) progress) / ((float) 100);
                    mARFace.setFloatVal("Cheek Thinning", mShapeFaceValue);
                    mShape_face_text.setText(String.format(Locale.getDefault(), "%.2f", mShapeFaceValue));
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
    }

    private void stopRecording() {
        mStreamingContext.stopRecording();
        mCapture.setBackgroundResource(R.drawable.xiangji_xiao);

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
            btnCameraSwitch.setEnabled(true);
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

        if (mPermissionGranted && (deviceChanged || getCurrentEngineState() != NvsStreamingContext.STREAMING_ENGINE_STATE_CAPTUREPREVIEW)) {
            m_supportAutoFocus = false;
            if (!mStreamingContext.startCapturePreview(mCurrentDeviceIndex,
                    captureResolutionGrade,
                    NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                            NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME
                            | NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, new NvsRational(6,19))) {
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
            flash.setEnabled(true);
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
            mRecordTime.setTextColor(0xffffffff);
        } else {
            mRecordTime.setTextColor(0xffD0021B);
            show = View.INVISIBLE;
        }
//        mCloseButton.setVisibility(show);
        mFunctionButtonLayout.setVisibility(show);
//        mDelete.setVisibility(show);
//        mNext.setVisibility(show);
//        mStartText.setVisibility(show);
        mSelectLayout.setVisibility(show);
        if (mRecordTimeList.isEmpty()) {
            mRecordTime.setVisibility(View.INVISIBLE);
        } else {
            mRecordTime.setVisibility(View.VISIBLE);
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
        mRecordTime.setVisibility(show);
    }

    private ArrayList<NvAsset> getLocalData(int assetType) {
        return mAssetManager.getUsableAssets(assetType, NvAsset.AspectRatio_All, 0);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.more_filter_layout:
            case R.id.download_more_btn:
                //TODO
                AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), FilterDownloadActivity.class, null, FILTERREQUESTLIST);
                break;
        }
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
                mCapture.setEnabled(true);

            }
            mEachRecodingVideoTime = l;
            mRecordTime.setVisibility(View.VISIBLE);
            mRecordTime.setText(TimeFormatUtil.formatUsToString2(mAllRecordingTime + mEachRecodingVideoTime));

        } else if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
            if (l > 40000) {
                stopRecording();
                takePhoto(l);

            }
        }
    }

    private void complete() {
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
        AppManager.getInstance().jumpActivity(CaptureActivity.this, EditActivity.class, bundle);

    }

    @Override
    public void onCaptureRecordingStarted(int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PermissionsActivity.PERMISSIONS_DENIED:
                mBuilderPermission = new Dialog(CaptureActivity.this, R.style.dialogTransparent);
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
        if (mStreamingContext != null) {
            mStreamingContext.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecordTimeList = new ArrayList<Long>();
        mStreamingContext = NvsStreamingContext.getInstance();
        captures.setVisibility(View.VISIBLE);
        mCapture.setVisibility(View.GONE);
        mSelectLayout.setVisibility(View.VISIBLE);
        if (mPermissionsChecker == null) {
            mPermissionsChecker = new PermissionsChecker(this);
        }
//        mNext.setClickable(true);
//        mMoreFilterButton.setClickable(true);
//        mMoreFilterLayout.setClickable(true);
        /*        mMoreProplLayout.setClickable(true);*//**/
        startCapturePreview(true);

        if (mRecordTimeList != null && mRecordTimeList.size() > 0) {
            mRecordTimeList.clear();
            mCapture.setProgress(0);
//            if (mRecordTimeList.size() != 0 && mRecordFileList.size() != 0) {
//                mAllRecordingTime -= mRecordTimeList.get(mRecordTimeList.size() - 1);
//                mRecordTimeList.remove(mRecordTimeList.size() - 1);
//                PathUtils.deleteFile(mRecordFileList.get(mRecordFileList.size() - 1));
//                mRecordFileList.remove(mRecordFileList.size() - 1);
//                mRecordTime.setText(TimeFormatUtil.formatUsToString2(mAllRecordingTime));
//
//                if (mRecordTimeList.size() == 0) {
////                    mStartText.setVisibility(View.GONE);
//                    mRecordTime.setVisibility(View.INVISIBLE);
//                } else {
//                    mRecordTime.setVisibility(View.VISIBLE);
//                }
//            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecordTimeList.clear();
        mRecordTimeList=null;
        animator.removeAllUpdateListeners();
        if (getCurrentEngineState() == mStreamingContext.STREAMING_ENGINE_STATE_CAPTURERECORDING) {
            stopRecording();
        }

        mStreamingContext.stop();
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
                    Bitmap bitmap1 = setImgSize(mPictureBitmap, DensityUtil.dp2px(CaptureActivity.this, 44), DensityUtil.dp2px(CaptureActivity.this, 44));
                    Glide.with(this).load(bitmap1).into(album);
                    if (mRecordType == Constants.RECORD_TYPE_PICTURE) {
                        mAllRecordingTime += mEachRecodingImageTime;
                        mRecordTimeList.add(mEachRecodingImageTime);
                        mRecordTime.setText(TimeFormatUtil.formatUsToString2(mAllRecordingTime));
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
                    Intent intent = new Intent(CaptureActivity.this, PictureEditActivity.class);
                    intent.putExtra("path", jpgPath);
                    startActivity(intent);
//                    showPictureLayout(true);
                }
            }
        }
    }

    private void takePhoto2(long time) {
        RequestOptions requestOptions = RequestOptions.frameOf(time);
        requestOptions.set(VideoBitmapDecoder.FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                Log.e("===>", "screen: " + ScreenUtils.getScreenWidth(CaptureActivity.this) + " " + ScreenUtils.getScreenHeight(CaptureActivity.this));
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
            mTypePictureBtn.setTextColor(ContextCompat.getColor(CaptureActivity.this, R.color.ms_red));
            mTypeVideoBtn.setTextColor(ContextCompat.getColor(CaptureActivity.this, R.color.white));
            mRecordType = Constants.RECORD_TYPE_PICTURE;
        } else {
            ani = new TranslateAnimation(mTypeVideoBtn.getX(), mTypePictureBtn.getX(), 0, 0);
            mTypePictureBtn.setTextColor(ContextCompat.getColor(CaptureActivity.this, R.color.white));
            mTypeVideoBtn.setTextColor(ContextCompat.getColor(CaptureActivity.this, R.color.ms_red));
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
            mCapture.setEnabled(true);
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
    private void startAnimation (RelativeLayout relativeLayout){
        Animation animation = AnimationUtils.loadAnimation(CaptureActivity.this, R.anim.pop_enter_anim);
        relativeLayout.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void endAnimation (RelativeLayout relativeLayout){
        Animation animation = AnimationUtils.loadAnimation(CaptureActivity.this, R.anim.pop_exit_anim);
        relativeLayout.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
