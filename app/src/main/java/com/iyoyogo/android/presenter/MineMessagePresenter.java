package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.contract.MineContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MineMessagePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    public MineMessagePresenter(MineContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserInfo(String user_id, String user_token) {
        DataManager.getFromRemote()
        .getUserInfo(user_id,user_token)
        .subscribe(new ApiObserver<MineMessageBean>(mView,this) {
            @Override
            protected void doOnSuccess(MineMessageBean mineMessageBean) {
                MineMessageBean.DataBean data = mineMessageBean.getData();
                if (data!=null){
                    mView.getUserInfoSuccess(data);
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
