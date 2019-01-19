package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.home.VersionBean;
import com.iyoyogo.android.contract.MainContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public MainPresenter(Context context,MainContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getVersion(String user_id, String user_token, String type) {
        DataManager.getFromRemote().getVersionMessage(user_id, user_token, type)
                .subscribe(new ApiObserver<VersionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(VersionBean versionBean) {
                        VersionBean.DataBean data = versionBean.getData();
                        if(data!=null){
                            mView.getVersionSuccess(data);
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
