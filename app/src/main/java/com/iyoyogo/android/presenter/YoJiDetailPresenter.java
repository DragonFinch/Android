package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.contract.YoJiDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoJiDetailPresenter extends BasePresenter<YoJiDetailContract.View> implements YoJiDetailContract.Presenter {
    public YoJiDetailPresenter(YoJiDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void getYoJiDetail(String user_id, String user_token, int yo_id) {
        DataManager.getFromRemote().getYoJiDetail(user_id, user_token, yo_id)
                .subscribe(new ApiObserver<YoJiDetailBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(YoJiDetailBean yoJiDetailBean) {
                        YoJiDetailBean.DataBean data = yoJiDetailBean.getData();
                        if (data != null) {
                            mView.getYoJiDetailSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
