package com.iyoyogo.android.ui.home.recommend;



import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.iyoyogo.android.utils.AppUtils;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.download.dialog.CommonDialog;
import com.iyoyogo.android.utils.download.utils.UpdateService;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import util.UpdateAppUtils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * 推荐Fragment
 */
public class RecommedFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
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

    private void showHintDialog(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.mipmap.ic_launcher)
                .setMessage("检测到当前有新版本，是否更新?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消更新，则跳转到旧版本的APP的页面
                        Toast.makeText(getContext(), "暂时不更新app", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //3.如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                        getVerCode(getContext());//TODO 获取版本号 用于对比服务器版本
                        getVerName(getContext());//TODO 获取版本名字 用于对比服务器版本
                        //在此之前需要访问服务器判断服务器版本号，在这里就不写了
                        showUpdateDialog(url);//TODO 弹出自动更新dialog
                    }


                }).create().show();
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
        DataManager.getFromRemote().getVersionMessage(user_id, user_token, "and").subscribe(new Observer<VersionBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(VersionBean versionBean) {
                VersionBean.DataBean data1 = versionBean.getData();
                String version = data1.getVersion();
                String netVersion = version.replace(".", "");
                Log.d("RecommedFragment", netVersion);
                String s = packageName(getContext());
                String localVersion = s.replace(".", "");
                int local_version = Integer.parseInt(localVersion);
                int net_version = Integer.parseInt(netVersion);
                if (local_version<net_version){
                   /* UpdateAppUtils.from(getActivity())
                            .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME)
                            .serverVersionName(netVersion)
                            .serverVersionCode(2)
                            .updateInfo(data1.getContent())
                            .apkPath(data1.getUrl())
                            .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                            .isForce(true)
                            .update();*/
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    private void checkVersion(String url){

        //这里不发送检测新版本网络请求，直接进入下载新版本安装
        CommonDialog.Builder builder = new CommonDialog.Builder(getContext());
        builder.setTitle("升级提示");
        builder.setMessage("发现新版本，请及时更新");
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getContext(), UpdateService.class);
                intent.putExtra("apkUrl", url);
                getContext().startService(intent);
            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.create().show();
    }
    public String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * 版本更新Dialog
     * */
    private void showUpdateDialog(final String downPath) {
        new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("发现新版本，是否更新？")
                .setCancelable(false)
                .setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        //使用系统下载类
                        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(downPath);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle("货柜快车APP下载");
                        request.setDescription("当前版本号为1.0.14");
                        // 设置自定义下载路径和文件名
//                                    String apkName =  "yourName" + DateUtils.getCurrentMillis() + ".apk";
//                                    request.setDestinationInExternalPublicDir(yourPath, apkName);
//                                    MyApplication.getInstance().setApkName(apkName);
                        //设置允许使用的网络类型，这里是移动网络和wifi都可以
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

                        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
                        //request.setShowRunningNotification(false);

                        //不显示下载界面
                        request.setVisibleInDownloadsUi(false);
                        // 设置为可被媒体扫描器找到
                        request.allowScanningByMediaScanner();
                        // 设置为可见和可管理
                        request.setVisibleInDownloadsUi(true);
                        request.setMimeType("application/cn.trinea.download.file");
        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件
        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/
                        //request.setDestinationInExternalFilesDir(this, null, "tar.apk");
                        long id = downloadManager.enqueue(request);//TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
                        //发现部分手机有缓存，如果下载了该文件，那么就不会再下载，就不触发下载完成的广播接收器
                        String apkId = App.getInstance().getApkId();
                        if (apkId == null || id != Long.parseLong(apkId)) {
                            App.getInstance().setApkId(Long.toString(id));//TODO 把id存储在Preferences里面
                            handler = new MyHandler();
                            getActivity().getContentResolver().registerContentObserver(Uri.parse("content://downloads/")
                                    , true, new DownloadObserver(handler, getContext(), id));
                            showProgressDialog();
                        } else {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.DOWNLOAD_COMPLETE");
                            intent.putExtra(DownloadManager.EXTRA_DOWNLOAD_ID, id);
                           getContext().sendBroadcast(intent);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }

                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        switch (keyCode){
                            case KeyEvent.KEYCODE_SEARCH:
                                return true;
                        }
                        return false;
                    }
                })
                .show();
    }

    /**
     * Handler实时更新进度
     */
    private Handler handler;
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int mProgress = (int)msg.obj;
            if(mProgress < 0){
                return;
            }else if(mProgress >= MAX_PROGRESS - 1){
                progressDialog.setProgress(MIN_PROGRESS);
                progressDialog.dismiss();
            }else{
                progressDialog.setProgress(mProgress);
            }
        }
    }

    /**
     * 创建进度弹框
     */
    private ProgressDialog progressDialog;
    private static final int MAX_PROGRESS = 100;
    private static final int MIN_PROGRESS = 0;
    private void showProgressDialog(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("下载进度：");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        //进度最大值
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.setProgress(MIN_PROGRESS);
        progressDialog.show();
    }

    public class DownloadObserver extends ContentObserver {
        private long downid;
        private Handler handler;
        private Context context;

        public DownloadObserver(Handler handler, Context context, long downid) {
            super(handler);
            this.handler = handler;
            this.downid = downid;
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            //每当/data/data/com.android.providers.download/database/database.db变化后，触发onCHANGE，开始具体查询
//            Log.w("onChangeID", String.valueOf(downid));
            super.onChange(selfChange);
            //实例化查询类，这里需要一个刚刚的downid
            DownloadManager.Query query = new DownloadManager.Query().setFilterById(downid);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            //这个就是数据库查询啦
            Cursor cursor = downloadManager.query(query);
            while (cursor.moveToNext()) {
                int mDownload_so_far = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int mDownload_all = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                int mProgress = (mDownload_so_far * 99) / mDownload_all;
                Log.w(getClass().getSimpleName(), String.valueOf(mProgress));
                //TODO：handler.sendMessage(xxxx),这样就可以更新UI了

                Message msg = handler.obtainMessage();
                msg.obj = mProgress;
                handler.sendMessage(msg);
            }
        }
    }

    /**
     * 获取软件版本号
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
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verName;
    }
    private String updateMessage(){

        StringBuilder sb = new StringBuilder();

        sb.append("1. 优化秒机装置，提高使用体验；");
        sb.append("\n");
        sb.append("2. 耗电进行优化，使用电量降低；");
        sb.append("\n");
        sb.append("3. 解决用户反馈，优化性能及界面细节；");

        return sb.toString();
    }

}
