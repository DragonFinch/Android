package com.iyoyogo.android.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Stephen on 2018/9/10 16:41
 * Email: 895745843@qq.com
 */
public class BasePresenter<T extends  IBaseView> implements IBasePresenter{

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(T mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

    }


    /**
     * 添加订阅
     */
    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
    /**
     * 解除订阅
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }



    /**
     * 取消View与Presenter关联
     */
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}