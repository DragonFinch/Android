package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.ReplacePhoneContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class ReplacePhonePresenter extends BasePresenter<ReplacePhoneContract.View>implements ReplacePhoneContract.Presenter {
    public ReplacePhonePresenter(Context context,ReplacePhoneContract.View mView) {
        super(context,mView);
    }

    @Override
    public void replacePhone(Context context,String user_id, String user_token, String phone, String yzm) {
        DataManager.getFromRemote()
        .replacePhone(context,user_id,user_token,phone,yzm)
        .subscribe(new ApiObserver<BaseBean>(mView,this) {
            @Override
            protected void doOnSuccess(BaseBean baseBean) {
                mView.replacePhoneSuccess(baseBean);
            }

            @Override
            protected boolean doOnFailure(int code, String message) {
                Toast.makeText(App.getmContext(), message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void sendMessage(Context context,String phone, String yzm, String datetime, String sign) {
        DataManager.getFromRemote()
                .getSendMessage(phone,yzm,datetime,sign)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.sendMessageSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
