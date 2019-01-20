package com.iyoyogo.android.presenter;

import android.content.Context;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.contract.YoJiListContract;

public class YoJiListPresenter extends BasePresenter<YoJiListContract.View> implements YoJiListContract.Presenter {
    public YoJiListPresenter(Context context, YoJiListContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getYoJiList(Context context,String user_id, String user_token, int page, int page_size) {
    }
}
