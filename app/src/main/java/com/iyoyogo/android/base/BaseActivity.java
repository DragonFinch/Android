package com.iyoyogo.android.base;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.ui.common.MainActivity;
import com.iyoyogo.android.ui.home.recommend.YoXiuDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.DisplayAdapter;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.widget.IStatusView;
import com.iyoyogo.android.widget.LoadStatusViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Stephen on 2018/9/10 16:50
 * Email: 895745843@qq.com
 */
public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected T mPresenter;
    private LoadStatusViewHolder mHolder;
    private Unbinder unbinder;

    /***
     * 授权接口
     */
    private PermissionListener mPermissionListener;
    public static final int REQUEST_PERMISSION_CODE = 101;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenOrientation();
        setDefaultDisplayAdaptOrientation();
        setStatusBar();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);//绑定ButterKnife
        initPermission();
        addStatusView();
        initBeforeView();

        initView();
        setListener();
        initData(savedInstanceState);
        initTitle();
    }

    /***
     * 在 initView 之前执行的方法，比如获取 intent 里面的数据
     */
    protected void initBeforeView() {
    }

    @Override
    public void onConnectServerError() {
        shortToast(getResources().getString(R.string.connect_error));
    }

    public void initTitle() {

    }

    /***
     * 初始化权限
     */
    protected void initPermission() {
    }

    /**
     * 设置状态栏（默认暗色主题）
     */
    private void setStatusBar() {
        StatusBarUtils.setStatusBarLightMode(this);
        StatusBarUtils.setWindowStatusBarColor(this, getStatusColor());
    }

    protected int getStatusColor() {
        return R.color.theme_white;
    }

    /**
     * 设置默认屏幕适配方向（默认宽度适配，xml文件直接写设计尺寸dp为单位）
     * 可重写此方法修改页面适配方向
     */
    protected void setDefaultDisplayAdaptOrientation() {
        DisplayAdapter.setDefault(this);
    }

    /**
     * 设置屏幕方向（默认竖屏）
     */
    protected void setScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置视图id
     */
    protected abstract int getLayoutId();

    /**
     * 添加loading视图
     */
    private void addStatusView() {
        FrameLayout contentRoot = findViewById(android.R.id.content);
        mHolder = new LoadStatusViewHolder(this);
        IStatusView iPlaceView = getCustomLoadStatusView();
        if (iPlaceView != null) {
            mHolder.setCustomStatusView(iPlaceView);
        }
        View statusView = mHolder.get();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = (int) getTitleBarHeight();
        statusView.setLayoutParams(params);
        contentRoot.addView(statusView);
    }

    /**
     * 获取标题栏高度（用于设置加载状态视图topMargin）
     */
    protected float getTitleBarHeight() {
        return getResources().getDimension(R.dimen.title_bar_height);
    }

    /**
     * 设置自定义加载状态视图
     */
    protected IStatusView getCustomLoadStatusView() {
        return null;
    }

    /**
     * 初始化视图
     */
    protected void initView() {

    }

    /**
     * 设置view监听
     */
    protected void setListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.start();
    }

    /**
     * 创建业务逻辑对应的presenter
     */
    protected abstract T createPresenter();

    /**
     * 设置页面标题
     */


    /**
     * 显示/隐藏加载视图
     */
    @Override
    public void setLoadingView(boolean isShow) {
        if (isShow) {
            mHolder.showLoadingView();
        } else {
            mHolder.hideLoadingView();
        }
    }

    /**
     * 显示/隐藏加载失败视图
     */
    @Override
    public void setLoadFailedView(boolean isShow) {
        if (isShow) {
            View emptyView = mHolder.showEmptyPlaceView();
            ImageView ivLoadFailedIcon = emptyView.findViewById(R.id.iv_empty_icon);
            TextView tvLoadFailedTips = emptyView.findViewById(R.id.tv_empty_tips);
            setLoadFailedViewRes(ivLoadFailedIcon, tvLoadFailedTips);
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLoadFailedView();
                }
            });
        } else {
            mHolder.hideEmptyPlaceView();
        }
    }

    /**
     * 重写此方法加载失败的图标和文本
     */
    protected void setLoadFailedViewRes(ImageView ivLoadFailedIcon, TextView tvLoadFailedTips) {
        ivLoadFailedIcon.setImageResource(R.drawable.empty_pic_fail);
        tvLoadFailedTips.setText(getString(R.string.load_error_tips));
    }

    /**
     * 显示/隐藏空数据视图
     */
    @Override
    public void setNoDataView(boolean isShow) {
        if (isShow) {
            View emptyView = mHolder.showEmptyPlaceView();
            ImageView ivEmptyIcon = emptyView.findViewById(R.id.iv_empty_icon);
            TextView tvEmptyTips = emptyView.findViewById(R.id.tv_empty_tips);
            setNoDataViewRes(ivEmptyIcon, tvEmptyTips);
        } else {
            mHolder.hideEmptyPlaceView();
        }
    }

    /**
     * 重写此方法设置空数据时的图标和文本
     */
    protected void setNoDataViewRes(ImageView ivEmptyIcon, TextView tvEmptyTips) {

    }

    /**
     * 点击加载失败页面处理
     */
    protected void onClickLoadFailedView() {

    }

    @Override
    public void longToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void shortToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        setFinishTransaction();
    }

    /**
     * 设置关闭页面转场动画
     */
    protected void setFinishTransaction() {
        overridePendingTransition(R.anim.from_left_in, R.anim.to_right_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setStartTransaction();
    }

    /**
     * 开启页面转场动画
     */
    protected void setStartTransaction() {
        overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
    }

    /**
     * 当userToken错误时的处理
     * （此处做登出操作以及开启登录页面）
     */
    @Override
    public void onUserTokenError() {
    }

    /**
     * 此方法用于处理网络连接错误时的处理
     * （当页面接口设置加载失败页面时，此方法不会调用）
     */


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
    }

    /***
     * 动态权限相关-----------------------------------------------------------------------------------
     */

    public interface PermissionListener {
        /***
         * 所有的授权成功时回调
         */
        void onGranted();

        /***
         * 返回已经授权成功的权限
         * @param grantedPermissions
         */
        void onGranted(List<String> grantedPermissions);

        /***
         * 返回用户拒绝的权限
         */
        void onDenied(List<String> deniedPermissions);


    }

    /***
     * 请求授权
     * @param permissions
     * @param listener
     */
    public void requestPermission(String[] permissions, PermissionListener listener) {
        this.mPermissionListener = listener;
        //存储需要申请的权限
        List<String> permissionList = new ArrayList<>();
        //遍历权限集合,获取需要授权的权限
        for (String permission : permissions) {
            //判断是否授权，没有则加入需要授权集合
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        //如果集合为空，则表示都已授权，反之则申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size() - 1]), REQUEST_PERMISSION_CODE);
        } else {
            listener.onGranted();
        }
    }

    /***
     * 申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                int grantResultsLength = grantResults.length;
                if (grantResultsLength > 0) {
                    //拒绝的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    //通过的权限
                    List<String> grantedPermissions = new ArrayList<>();

                    //将各个权限的授权结果分类处理
                    for (int i = 0; i < grantResultsLength; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        //分类处理后
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        } else {
                            grantedPermissions.add(permission);
                        }
                    }

                    //根据集合的状态，执行相应的回调
                    if (deniedPermissions.isEmpty()) {
                        //拒绝的权限集合为空，说明所有的权限都通过了
                        mPermissionListener.onGranted();
                    } else {
                        //返回成功的权限
                        mPermissionListener.onGranted(grantedPermissions);
                        //返回失败的权限
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }

                break;
            default:
                break;
        }
    }


}
