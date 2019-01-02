package com.iyoyogo.android.ui.home.recommend;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.HomeRecyclerViewAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.home.VersionBean;
import com.iyoyogo.android.contract.HomeContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.HomePresenter;
import com.iyoyogo.android.ui.receive.UpdateService;
import com.iyoyogo.android.utils.AppUtils;
import com.iyoyogo.android.utils.SpUtils;
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
import io.reactivex.functions.Consumer;

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

    @Override
    protected void initView() {
        super.initView();
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
                mPresenter.banner(user_id, user_token, "commend");
            }
        });
    }

    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);

        mPresenter.banner(user_id, user_token, "commend");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    private void downloadApk() {
        boolean isWifi = AppUtils.isWifi(getContext()); //是否处于WiFi状态
        if (isWifi) {
            getActivity().startService(new Intent(getContext(), UpdateService.class));
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
                    getContext().startService(new Intent(getContext(), UpdateService.class));
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

    private void showHintDialog() {
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
                mPresenter.banner(user_id, user_token, "commend");
            }
        });
        String registrationID = JPushInterface.getRegistrationID(getContext());
        Log.d("龙雀", registrationID);
        DataManager.getFromRemote().push(user_id, user_token, "and", registrationID)
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {
//                        Toast.makeText(mActivity, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                if (local_version < net_version) {
                    showHintDialog();
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

}
