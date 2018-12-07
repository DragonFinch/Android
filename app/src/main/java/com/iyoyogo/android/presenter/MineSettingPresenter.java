package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.MineSettingContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MineSettingPresenter extends BasePresenter<MineSettingContract.View> implements MineSettingContract.Presenter {
    public MineSettingPresenter(MineSettingContract.View mView) {
        super(mView);
    }

    @Override
    public void logout(String user_id, String user_token, String addr, String phone_info, String app_version) {
        DataManager.getFromRemote()
       .logout(user_id,user_token,addr,phone_info,app_version)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.logoutSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
