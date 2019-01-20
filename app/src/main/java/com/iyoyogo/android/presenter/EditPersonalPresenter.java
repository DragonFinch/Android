package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.contract.EditPersonalContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class EditPersonalPresenter extends BasePresenter<EditPersonalContract.View> implements EditPersonalContract.Presenter {
    public EditPersonalPresenter(Context context,EditPersonalContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getUserInfo(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getUserInfo(context,user_id, user_token)
                .subscribe(new ApiObserver<GetUserInfoBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(GetUserInfoBean getUserInfoBean) {
                        GetUserInfoBean.DataBean data = getUserInfoBean.getData();
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
    public void setUserInfo(Context context,String user_id, String user_token, String user_nickname, String user_logo, String user_sex, String user_birthday, String user_city) {
        DataManager.getFromRemote()
                .setUserInfo(context,user_id, user_token, user_nickname, user_logo, user_sex, user_birthday, user_city)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        if(mView!=null)
                        mView.setUserInfoSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

    }
}
