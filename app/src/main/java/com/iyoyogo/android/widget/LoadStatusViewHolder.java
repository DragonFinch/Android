package com.iyoyogo.android.widget;


import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.iyoyogo.android.R;


/**
 * Created by wgheng on 2018/7/4.
 * Description : 用于控制加载视图的显示及隐藏
 */
public class LoadStatusViewHolder {

    private FrameLayout mHolder;
    private IStatusView mStatusView;
    private ViewStub mLoadingViewStub;
    private ViewStub mEmptyPlaceViewStub;
    private View mLoadingView;
    private View mEmptyPlaceView;

    public LoadStatusViewHolder(Context context) {
        mHolder = (FrameLayout) View.inflate(context, R.layout.view_palce_holder, null);
        mLoadingViewStub = mHolder.findViewById(R.id.vs_loading_view);
        mEmptyPlaceViewStub = mHolder.findViewById(R.id.vs_empty_place_view);
    }

    public View get() {
        return mHolder;
    }

    public void setCustomStatusView(IStatusView statusView) {
        this.mStatusView = statusView;
    }

    public void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingViewStub.setLayoutResource(getLoadingLayoutId());
            mLoadingView = mLoadingViewStub.inflate();
        }
        mLoadingView.setVisibility(View.VISIBLE);

        if (mEmptyPlaceView != null) {
            mEmptyPlaceView.setVisibility(View.GONE);
        }
    }

    public void hideLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    public View showEmptyPlaceView() {
        if (mEmptyPlaceView == null) {
            mEmptyPlaceViewStub.setLayoutResource(getLoadFailedLayoutId());
            mEmptyPlaceView = mEmptyPlaceViewStub.inflate();
        }
        mEmptyPlaceView.setVisibility(View.VISIBLE);

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        return mEmptyPlaceView;
    }

    private int getLoadingLayoutId() {
        if (mStatusView == null) {
            mStatusView = new StatusView();
        }
        return mStatusView.getLoadingLayout();
    }

    private int getLoadFailedLayoutId() {
        if (mStatusView == null) {
            mStatusView = new StatusView();
        }
        return mStatusView.getLoadFailedLayout();
    }

    public void hideEmptyPlaceView() {
        if (mEmptyPlaceView != null) {
            mEmptyPlaceView.setVisibility(View.GONE);
        }
    }

    public void gone() {
        hideLoadingView();
        hideEmptyPlaceView();
    }

    private static class StatusView implements IStatusView {

        @Override
        public int getLoadingLayout() {
            return R.layout.common_loading_view;
        }

        @Override
        public int getLoadFailedLayout() {
            return R.layout.common_load_failed_view;
        }

    }


}
