package com.iyoyogo.android.ui.common;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.ui.home.HomeFragment;
import com.iyoyogo.android.ui.home.yoji.PublishYoJiActivity;
import com.iyoyogo.android.ui.home.yoxiu.SourceChooseActivity;
import com.iyoyogo.android.ui.mine.MineFragment;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.meicam.sdk.NvsStreamingContext;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private PopupWindow popup;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.home)
    RadioButton home;
    @BindView(R.id.camera)
    RadioButton camera;
    @BindView(R.id.me)
    RadioButton me;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.publish_home)
    ImageView publishHome;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private NvsStreamingContext mStreamingContext;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 0;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;

    public static final int CAPTURE_TYPE_ZOOM = 2;
    public static final int CAPTURE_TYPE_EXPOSE = 3;
    private static final int FILTERREQUESTLIST = 110;
    private static final int ARFACEERREQUESTLIST = 111;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE = 203;
    private final int MIN_RECORD_DURATION = 1000000;
    private LinearLayout publish_yoxiu;
    private LinearLayout publish_yoji;
    private ImageView publish_close;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initView() {
        super.initView();
        checkAllPermission();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
        switchFragment(homeFragment);
        String user_id = SpUtils.getString(MainActivity.this, "user_id", null);

        String user_token = SpUtils.getString(MainActivity.this, "user_token", null);
        Log.d("MainActivity", user_id);
        Log.d("MainActivity", user_token);
        test.setText(user_id+user_token);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.camera:
                        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                        break;
                    case R.id.home:
                        switchFragment(homeFragment);
                        break;
                    case R.id.me:
                        switchFragment(mineFragment);
                        break;
                }
                ;
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.orgeen_color);
        initPopup();


    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment).commitAllowingStateLoss();

    }

    @Override
    protected int getLayoutId() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return R.layout.activity_main;
        }
        //初始化
        String licensePath = "assets:/meishesdk.lic";

        NvsStreamingContext.init(this, licensePath, NvsStreamingContext.STREAMING_CONTEXT_FLAG_SUPPORT_4K_EDIT);
        NvAssetManager.init(MainActivity.this);//素材管理器初始化
        mStreamingContext = NvsStreamingContext.getInstance();
        return R.layout.activity_main;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    private void checkAllPermission() {
        if (Build.VERSION.SDK_INT >= 23) {





            String[] mPermissionList = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (PackageManager.PERMISSION_GRANTED==ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
                                if (PackageManager.PERMISSION_GRANTED==ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)){

                                }else {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
                                }
                            }else {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},123);
                            }
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


    @OnClick(R.id.publish_home)
    public void onViewClicked() {
        backgroundAlpha(0.4f);
        popup.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM, 0, 0);
    }

    private void initPopup() {
        View view = getLayoutInflater().inflate(R.layout.publish, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(MainActivity.this, 375), DensityUtil.dp2px(MainActivity.this, 225), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        publish_yoxiu = view.findViewById(R.id.publish_yoxiu);
        publish_yoji = view.findViewById(R.id.publish_yoji);
        publish_close = view.findViewById(R.id.publish_close);
        //发布yo.秀
        publish_yoxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "发布yo.秀", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,SourceChooseActivity.class));
                popup.dismiss();
            }
        });
        //发布yo.记
        publish_yoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "发布yo.记", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,PublishYoJiActivity.class));
                popup.dismiss();
            }
        });
        //关闭发布页
        publish_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                backgroundAlpha(1f);
            }
        });
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1f);

        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());

    }





}
