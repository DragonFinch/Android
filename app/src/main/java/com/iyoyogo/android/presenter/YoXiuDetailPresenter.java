package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoXiuDetailPresenter extends BasePresenter<YoXiuDetailContract.View> implements YoXiuDetailContract.Presenter {
    public YoXiuDetailPresenter(YoXiuDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void getDetail(String user_id, String user_token, int id) {
        DataManager.getFromRemote()
        .getDetail(user_id,user_token,id)
        .subscribe(new ApiObserver<YoXiuDetailBean>(mView,this) {
            @Override
            protected void doOnSuccess(YoXiuDetailBean yoXiuDetailBean) {
                YoXiuDetailBean.DataBean data = yoXiuDetailBean.getData();
                if (data!=null){
                    mView.getDetailSuccess(data);
                }
            }

            @Override
            protected boolean doOnFailure(int code, String message) {
                Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        })      ;
    }
}
