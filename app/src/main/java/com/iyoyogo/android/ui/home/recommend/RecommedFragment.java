package com.iyoyogo.android.ui.home.recommend;


import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.HomeRecyclerViewAdapter;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.home.VersionBean;
import com.iyoyogo.android.contract.HomeContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.HomePresenter;
import com.iyoyogo.android.ui.common.WelcomeActivity;
import com.iyoyogo.android.ui.receive.UpdateService;
import com.iyoyogo.android.utils.AppConfig;
import com.iyoyogo.android.utils.AppUtils;
import com.iyoyogo.android.utils.NetUtils;
import com.iyoyogo.android.utils.ResUtils;
import com.iyoyogo.android.utils.SpUtils;


import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.iyoyogo.android.widget.AppVersionDialog;
import com.iyoyogo.android.widget.DownLoadDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import util.UpdateAppUtils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * 推荐Fragment
 */
public class RecommedFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View, AppVersionDialog.DialogItemClickListener {
    @BindView(R.id.recycler_recommend)
    RecyclerView recyclerHome;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private String user_id;
    private String user_token;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 0;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;
    private String url = "http://p.gdown.baidu.com/2c94946f0e26ae462034226fde89692a269f6bdf2aa6e9c8767d772124c9f7c7d260e6931fef78f570c881e16b38ef5c2078652c803b022fe844fb87d68c894ddbef8a9694a05f1e0ff373231ca712808c4440578d04145cdba6bc15f63a58840b47586cd429087098129921afed1197f1295b0f2cf49ff9250a04c0d4ef1ae92fdb5712c98e32f6be819bd5d37ee48e968923ef76a8cc7fb9742033e72b07c84dd4e14dc8d7931011e1242b1a867296948cf07eaea2a39a3dfd57541fe83eade2e5b448b9ba397e0a84fc90bc2405b969c9634da0803afc6b0464e4a7e333bb0c7072bd418e99312b5457043c33f156a13660aba0be6f15";//较小的apk文件
    private String url2 = "http://mhhy.dl.gxpan.cn/apk/ml/MBGYD092101/Gardenscapes-ledou-MBGYD092101.apk";//梦幻花园游戏包体较大，可以很好的查看下载效果

    private String title = "应用宝";//这里可以写应用名
    private String desc = "大量优质应用，尽在应用宝。";//这里填写一些对应用的介绍
    long downloadId = 0;

    private boolean isRegisterReceiver;
    public static final int CAPTURE_TYPE_ZOOM = 2;
    public static final int CAPTURE_TYPE_EXPOSE = 3;
    private static final int FILTERREQUESTLIST = 110;
    private static final int ARFACEERREQUESTLIST = 111;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE = 203;
    private final int MIN_RECORD_DURATION = 1000000;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private String city;
    private DownLoadDialog lodingDialog;
    private AppVersionDialog dialog;
    private String dowloadPath;


    @Override
    protected void initView() {
        super.initView();

    }

    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void initData() {
        super.initData();
     /*   DownloadApk.getInstance().registerBroadcast(getContext());
        //2.删除已存在的Apk
        DownloadApk.getInstance().removeFile(getContext());*/
        dialog = new AppVersionDialog(getContext(), R.style.AppVerDialog);
        dialog.setCancelable(false);
        dialog.setAppClickLister(this);
        lodingDialog = new DownLoadDialog(getContext(), R.style.AppVerDialog);

        city = SpUtils.getString(getContext(), "city", null);
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        if (city != null) {
            MyRefreshAnimHeader mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
            setHeader(mRefreshAnimHeader);
            refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
            //下拉刷新
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setFooterHeight(1.0f);
            refreshLayout.autoRefresh();
            refreshLayout.finishRefresh(1050);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.finishRefresh(1050);
                    mPresenter.banner(user_id, user_token, "commend", city);
                }
            });
        } else {
            MyRefreshAnimHeader mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
            setHeader(mRefreshAnimHeader);
            refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
            //下拉刷新
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setFooterHeight(1.0f);
            refreshLayout.autoRefresh();
            refreshLayout.finishRefresh(1050);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.finishRefresh(1050);
                    mPresenter.banner(user_id, user_token, "commend", "");
                }
            });
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    private void downloadApk(String url) {
        boolean isWifi = AppUtils.isWifi(getContext()); //是否处于WiFi状态
        if (isWifi) {
            Intent intent = new Intent(getContext(), UpdateService.class);
            intent.putExtra("url", url);
            getActivity().startService(intent);
            Toast.makeText(getContext(), "开始下载。", Toast.LENGTH_LONG).show();
        } else {
            //弹出对话框，提示是否用流量下载
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("提示");
            builder.setMessage("是否要用流量进行下载更新");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Toast.makeText(getContext(), "取消更新。", Toast.LENGTH_LONG).show();
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), UpdateService.class);
                    intent.putExtra("url", url);
                    getContext().startService(intent);
                    Toast.makeText(getContext(), "开始下载。", Toast.LENGTH_LONG).show();
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

    @Override
    protected HomeContract.Presenter createPresenter() {
        return new HomePresenter(this);
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
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {

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
    public void bannerSuccess(HomeBean.DataBean data) {
        List<HomeBean.DataBean> mList = new ArrayList<>();
        mList.add(data);
        Log.d("HomeFragment", "mList.size():" + mList.size());
        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), mList);
        Log.d("HomeFragment", "mList.size():" + mList.size());
        recyclerHome.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerHome.setAdapter(homeRecyclerViewAdapter);
        homeRecyclerViewAdapter.onItemRetryOnClickListener(new HomeRecyclerViewAdapter.OnRetryClickListener() {
            @Override
            public void onretry() {
                mPresenter.banner(user_id, user_token, "commend", city);
            }
        });
        String registrationID = JPushInterface.getRegistrationID(getContext());
        Log.d("龙雀", registrationID);
        if (!NetUtils.isInternetConnection(App.context)) {
            shortToast("网络异常表");
            return;
        } else {

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }







    /**
     * /**
     * Handler实时更新进度
     */
    private Handler handler;

    @Override
    public void sureDown() {

    }

    @Override
    public void cancleDown() {

    }


    /**
     * 创建进度弹框
     */
    private ProgressDialog progressDialog;
    private static final int MAX_PROGRESS = 100;
    private static final int MIN_PROGRESS = 0;


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }

    private String updateMessage() {

        StringBuilder sb = new StringBuilder();

        sb.append("1. 优化秒机装置，提高使用体验；");
        sb.append("\n");
        sb.append("2. 耗电进行优化，使用电量降低；");
        sb.append("\n");
        sb.append("3. 解决用户反馈，优化性能及界面细节；");

        return sb.toString();
    }

    private String writeResponseBodyToDisk(String url, InputStream inputStream) {
        try {
            int pos = url.lastIndexOf("/");
            String name = url.substring(pos, url.length());
            String filePath = AppConfig.VOICE + name;
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                if (filePath.endsWith(".apk")) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("filePath", filePath);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                return filePath;
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }

    }

}
