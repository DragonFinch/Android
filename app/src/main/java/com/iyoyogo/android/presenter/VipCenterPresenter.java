package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.contract.VipCenterContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class VipCenterPresenter extends BasePresenter<VipCenterContract.View> implements VipCenterContract.Presenter {

    public VipCenterPresenter(VipCenterContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserInfo(String user_id, String user_token) {
        DataManager.getFromRemote()
                .getPersonInfo(user_id, user_token)
                .subscribe(new ApiObserver<MineMessageBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MineMessageBean mineMessageBean) {
                        MineMessageBean.DataBean data = mineMessageBean.getData();
                        if (data != null) {
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

    @Override
    public void getVipCenter(String user_id, String user_token) {
        DataManager.getFromRemote().setVipCenter(user_id, user_token)
                .subscribe(new ApiObserver<VipCenterBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(VipCenterBean vipCenterBean) {
                        mView.getVipCenterSuccess(vipCenterBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
