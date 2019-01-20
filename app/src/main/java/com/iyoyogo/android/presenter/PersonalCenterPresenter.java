package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.contract.PersonalCenterContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class PersonalCenterPresenter extends BasePresenter<PersonalCenterContract.View> implements PersonalCenterContract.Presenter {
    public PersonalCenterPresenter(Context context,PersonalCenterContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getPersonalCenter(Context context,String user_id, String user_token, String his_id) {
        DataManager.getFromRemote()
                .getUserCenter(context,user_id, user_token, his_id)
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
    public void addAttention1(Context context,String user_id, String user_token, String target_id) {
        DataManager.getFromRemote()
                .addAttention1(context,user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        AttentionBean.DataBean data = attentionBean.getData();
                        if (data != null) {
                            mView.addAttention1(attentionBean);
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
    public void deleteAttention(Context context,String user_id, String user_token, String target_id) {
        DataManager.getFromRemote()
                .addAttention1(context,user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        AttentionBean.DataBean data = attentionBean.getData();
                        if (data != null) {
                            mView.addAttention1(attentionBean);
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
