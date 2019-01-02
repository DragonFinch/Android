package com.iyoyogo.android.presenter;

import com.iyoyogo.android.base.BasePresenter;

public class YoJiListPresenter extends BasePresenter<YoJiListContract.View> implements YoJiListContract.Presenter {
    public YoJiListPresenter(YoJiListContract.View mView) {
        super(mView);
    }

    @Override
    public void getYoJiList(String user_id, String user_token, int page, int page_size) {
    }
}
