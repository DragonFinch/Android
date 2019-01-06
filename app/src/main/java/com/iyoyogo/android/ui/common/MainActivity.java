package com.iyoyogo.android.ui.common;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.home.VersionBean;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.contract.MainContract;
import com.iyoyogo.android.presenter.MainPresenter;
import com.iyoyogo.android.ui.home.HomeFragment;
import com.iyoyogo.android.ui.mine.MineFragment;
import com.iyoyogo.android.ui.receive.UpdateService;
import com.iyoyogo.android.utils.AppUtils;
import com.iyoyogo.android.utils.ExampleUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.meicam.sdk.NvsStreamingContext;

import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * 首页
 */

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {


    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.ll_tab1)
    LinearLayout llTab1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.ll_tab2)
    LinearLayout llTab2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.ll_tab3)
    LinearLayout llTab3;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.today_tab)
    RadioButton todayTab;
    @BindView(R.id.settings_tab)
    RadioButton settingsTab;
    @BindView(R.id.tabs_rg)
    RadioGroup tabsRg;
    @BindView(R.id.sign_iv)
    ImageView signIv;
    private Fragment fragment_now = null;
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
    private List<ImageView> iv_list;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private SparseArray<Fragment> mFragmentSparseArray;
    private Fragment currentFragment = new Fragment();
    private FragmentTransaction transaction;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public static boolean isForeground = false;



    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Log.d("MessageReceiver", showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void inint() {

        initViews();
    }


    /**
     * 选中的tab 和 没有选中的tab 的图标和字体颜色
     *
     * @param index
     */
    public void changePageSelect(int index) {
        for (int i = 0; i < iv_list.size(); i++) {
            if (index == i) {
                iv_list.get(i).setEnabled(true);
            } else {
                iv_list.get(i).setEnabled(false);
            }
        }
    }

    /**
     * 当点击导航栏时改变 fragment
     *
     * @param id
     */


    /**
     * 隐藏显示fragment
     *
     * @param from 需要隐藏的fragment
     * @param to   需要显示的fragment
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from == null) {
                transaction.add(R.id.frame_container, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，add下一个fragment到Activity中并显示
                transaction.hide(from).add(R.id.frame_container, to).show(to).commitAllowingStateLoss();
            }
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        fragment_now = to;

    }
    public static MainActivity instance;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initView() {
        super.initView();
        instance=this;
        checkAllPermission();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        inint();
      /*  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_container, mFragmentSparseArray.get(R.id.today_tab))
                .commitAllowingStateLoss();
*/
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        String user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
//
        if (user_id == null || user_token == null) {
            SpUtils.remove(MainActivity.this, "user_id");
            SpUtils.remove(MainActivity.this, "user_token");
            SpUtils.remove(MainActivity.this, "isLogin");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    @Override
    public void getVersionSuccess(VersionBean.DataBean data) {
        String version = data.getVersion();
        String s = version.replaceAll(".", "");
        Log.d("MainActivity", "version" + version);
        Log.d("8889898989", s);
        int currVersionCode = AppUtils.getPackageVersionCode(MainActivity.this);
          /*  int newVersionCode = Integer.parseInt(s);


            //如果当前版本小于新版本，则提示更新
            if (currVersionCode < newVersionCode) {
                Log.i("tag", "有新版本需要更新");
                showHintDialog();
            }*/
    }

    private void initViews() {

//        mFragmentSparseArray = new SparseArray<>();
        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
        switchContent(mineFragment,homeFragment);
//
//        mFragmentSparseArray.append(R.id.today_tab, homeFragment);
//
//        mFragmentSparseArray.append(R.id.settings_tab, mineFragment);
        tabsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
               switch (checkedId){
                   case R.id.today_tab:
                     switchContent(mineFragment,homeFragment);
                       break;
                   case R.id.settings_tab:
                       switchContent(homeFragment,mineFragment);
                       break;
               }
            }
        });
        // 默认显示第一个
      /*  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_container, mFragmentSparseArray.get(R.id.today_tab))
                .commitAllowingStateLoss();*/


        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });
    }
    /**
     * 是否第一次
     */
    private boolean mIsFirstIn = true;

    /**
     * Fragment 切换
     *
     * @param from
     *            隐藏的fragment
     * @param to
     *            显示的fragment
     * @param
     *            {@code R.id.main} 加载到的布局
     */
    private void switchContent(Fragment from, Fragment to) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            // 先判断是否被add过
            if (mIsFirstIn) {
                mIsFirstIn = false;
                ft.add(R.id.frame_container, to).commit();
            } else {
                ft.hide(from).add(R.id.frame_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }
        } else {
            ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    private void showHintDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher)
                .setMessage("检测到当前有新版本，是否更新?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消更新，则跳转到旧版本的APP的页面
                        Toast.makeText(MainActivity.this, "暂时不更新app", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //6.0以下系统，不需要请求权限,直接下载新版本的app
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            downloadApk();
                        } else {
                            //6.0以上,先检查，申请权限，再下载
                            checkAllPermission();
                        }

                    }
                }).create().show();
    }

    private void downloadApk() {
        boolean isWifi = AppUtils.isWifi(this); //是否处于WiFi状态
        if (isWifi) {
            startService(new Intent(MainActivity.this, UpdateService.class));
            Toast.makeText(MainActivity.this, "开始下载。", Toast.LENGTH_LONG).show();
        } else {
            //弹出对话框，提示是否用流量下载
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("是否要用流量进行下载更新");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Toast.makeText(MainActivity.this, "取消更新。", Toast.LENGTH_LONG).show();
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startService(new Intent(MainActivity.this, UpdateService.class));
                    Toast.makeText(MainActivity.this, "开始下载。", Toast.LENGTH_LONG).show();
                }
            });
            builder.setCancelable(false);

            AlertDialog dialog = builder.create();
            //设置不可取消对话框
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

    }

    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
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
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
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
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
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
                            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                                } else {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                                }
                            } else {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
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
    protected void onResume() {
        super.onResume();
        registerMessageReceiver();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            if (bundle != null) {
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.d("大碗", title + content);
//                changePageFragment(R.id.ll_tab3);
            }

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


    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出yoyogo", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}
