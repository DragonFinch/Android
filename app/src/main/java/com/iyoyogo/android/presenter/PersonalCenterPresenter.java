package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.contract.PersonalCenterContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class PersonalCenterPresenter extends BasePresenter<PersonalCenterContract.View> implements PersonalCenterContract.Presenter {
    public PersonalCenterPresenter(PersonalCenterContract.View mView) {
        super(mView);
    }

    @Override
    public void getPersonalCenter(String user_id, String user_token, String his_id) {
        DataManager.getFromRemote()
                .getUserCenter(user_id, user_token, his_id)
                .subscribe(new ApiObserver<UserCenterBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(UserCenterBean userCenterBean) {
                        UserCenterBean.DataBean data = userCenterBean.getData();
                        if (data != null) {
                            mView.getPersonalCenterSuccess(data);
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
    public void addAttention1(String user_id, String user_token, String target_id) {
        DataManager.getFromRemote()
                .addAttention1(user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        AttentionBean.DataBean data = attentionBean.getData();
                        if (data != null) {
                            mView.addAttention1(data);
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
