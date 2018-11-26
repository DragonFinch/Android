package com.iyoyogo.android.net;


import android.support.annotation.NonNull;

import com.iyoyogo.android.app.Constant;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zyt on 2017/12/22.
 * Description : 统一处理网络请求返回数据
 */
public abstract class ApiObserver<T extends BaseBean> implements Observer<T> {

    public static final int SHOW_NONE = 0;//不现实任何加载状态视图
    public static final int SHOW_ONLY_LOADING = 1;//只显示loading视图
    public static final int SHOW_ONLY_EMPTY = 2;//只显示空视图
    public static final int SHOW_ALL = 3;//显示所有加载状态视图
    private IBaseView mView;
    private BasePresenter mPresenter;
    private boolean isShowLoadingView = false;//接口是否有加载中视图
    private boolean isShowLoadFailedView = false;//接口是否有加载失败页面


    public ApiObserver(IBaseView mView, BasePresenter mPresenter) {
        this.mView = mView;
        this.mPresenter = mPresenter;
        setShowLoadStatusViewPolicy(SHOW_ONLY_LOADING);//默认只显示loading视图
    }

    /**
     * @param showLoadStatusViewPolicy {@link #SHOW_NONE},{@link #SHOW_ONLY_LOADING},{@link #SHOW_ONLY_EMPTY},{@link #SHOW_ALL}
     */
    public ApiObserver(IBaseView mView, BasePresenter mPresenter, int showLoadStatusViewPolicy) {
        this.mView = mView;
        this.mPresenter = mPresenter;
        setShowLoadStatusViewPolicy(showLoadStatusViewPolicy);
    }

    /**
     * 设置视图显示策略
     */
    private void setShowLoadStatusViewPolicy(int showLoadStatusViewPolicy) {
        switch (showLoadStatusViewPolicy) {
            case SHOW_NONE:
                isShowLoadingView = false;
                isShowLoadFailedView = false;
                break;
            case SHOW_ONLY_LOADING:
                isShowLoadingView = true;
                isShowLoadFailedView = false;
                break;
            case SHOW_ONLY_EMPTY:
                isShowLoadingView = false;
                isShowLoadFailedView = true;
                break;
            case SHOW_ALL:
                isShowLoadingView = true;
                isShowLoadFailedView = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (mView != null) {
            if (isShowLoadingView) {
                mView.setLoadingView(true);
            }
        }


    }

    @Override
    public void onNext(@NonNull T t) {
        if (mView != null) {
            if (isShowLoadingView) {
                mView.setLoadingView(false);
            }
            if (isShowLoadFailedView) {
                mView.setLoadFailedView(false);
            }
        }
        if (Constant.RESULT_OK==t.getCode()) {
            doOnSuccess(t);
        } else {
//            handleFailureResult(t.getSuccess(), t.getErrormsg());
        }

    }

    protected abstract void doOnSuccess(T t);

    /**
     * 根据状态码分别处理各自逻辑
     */
//    private void handleFailureResult(String code, String message) {
//        //处理统一状态码逻辑 如：用户已在其他客户端登录
//        if (mView == null) return;
//        switch (code) {
//            case Constant.USER_TOKEN_ERROR:
//                mView.onUserTokenError();
//                break;
//
//
//            default:
//                if (!doOnFailure(code, message)) {
//                    if (isShowLoadFailedView) {
//
//                        mView.setLoadFailedView(true);
//                    }
//
//                }
//        }
//    }

    /**
     * 处理对应业务状态码
     *
     * @return 返回值代表是否交由此方法处理
     */
    protected boolean doOnFailure(int code, String message) {
        return false;
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        if (mView != null) {
            if (isShowLoadingView) {
                mView.setLoadingView(false);
            }
            if (isShowLoadFailedView) {
                mView.setLoadFailedView(true);
            } else {
                mView.onConnectServerError();
            }
        }
    }

    @Override
    public void onComplete() {

    }

}
