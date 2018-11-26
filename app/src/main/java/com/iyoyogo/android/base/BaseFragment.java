package com.iyoyogo.android.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.iyoyogo.android.R;
import com.iyoyogo.android.widget.IStatusView;
import com.iyoyogo.android.widget.LoadStatusViewHolder;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Stephen on 2018/9/10 17:58
 * Email: 895745843@qq.com
 */
public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {
    protected Activity mActivity;
    private Unbinder unbinder;
    private LoadStatusViewHolder mHolder;
    protected T mPresenter;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout rootView = new FrameLayout(mActivity);
        View view = inflater.inflate(getLayoutId(), rootView, false);
        rootView.addView(view);
        addStatusView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 设置视图id
     */
    protected abstract int getLayoutId();
    /**
     * 添加loading视图
     */
    private void addStatusView(ViewGroup container) {
        mHolder = new LoadStatusViewHolder(mActivity);
        IStatusView iPlaceView = getCustomLoadStatusView();
        if (iPlaceView != null) {
            mHolder.setCustomStatusView(iPlaceView);
        }
        container.addView(mHolder.get());
    }

    /**
     * 设置自定义加载状态视图
     */
    protected IStatusView getCustomLoadStatusView() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
        initData();

    }
    protected void initView() {

    }

    /**
     * 设置view监听
     */
    protected void setListener() {

    }
    protected void initData() {
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.start();//初始化数据
    }
    /**
     * 创建模块对应的Presenter
     */
    protected abstract T createPresenter();

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
     * 点击加载失败页面处理
     */
    protected void onClickLoadFailedView() {

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


    @Override
    public void longToast(String content) {
        Toast.makeText(mActivity, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void shortToast(String content) {
        Toast.makeText(mActivity, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserTokenError() {
        ((BaseActivity) mActivity).onUserTokenError();
    }
    @Override
    public void onConnectServerError() {
        shortToast(getString(R.string.connect_error));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
    }

}
