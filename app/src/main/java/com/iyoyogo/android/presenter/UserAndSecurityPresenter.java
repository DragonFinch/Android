package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.contract.UserAndSecurityContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class UserAndSecurityPresenter extends BasePresenter<UserAndSecurityContract.View> implements UserAndSecurityContract.Presenter {
    public UserAndSecurityPresenter(UserAndSecurityContract.View mView) {
        super(mView);
    }

    @Override
    public void getBindInfo(String user_id, String user_token) {
        DataManager.getFromRemote()
        .getBindInfo(user_id,user_token)
        .subscribe(new ApiObserver<GetBindInfoBean>(mView,this) {
            @Override
            protected void doOnSuccess(GetBindInfoBean getBindInfoBean) {
                GetBindInfoBean.DataBean data = getBindInfoBean.getData();
                if (data!=null){
                    mView.getBindInfoSuccess(data);
                }
            }

            @Override
            protected boolean doOnFailure(int code, String message) {
                Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        })    ;
    }
}
