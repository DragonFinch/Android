package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.contract.UserBindContract;
import com.iyoyogo.android.contract.VipCenterContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class UserBindPresenter extends BasePresenter<UserBindContract.View> implements UserBindContract.Presenter {

    public UserBindPresenter(UserBindContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserBind(String user_id, String user_token, int type, String openid) {
        DataManager.getFromRemote().getUserBind(user_id, user_token,type,openid)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {

                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.getUserBindSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
