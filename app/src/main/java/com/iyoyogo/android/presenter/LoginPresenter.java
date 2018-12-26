package com.iyoyogo.android.presenter;

import android.util.Log;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.contract.LoginContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @Override
    public void sendMessage(String phone, String yzm, String datetime, String sign) {
        DataManager.getFromRemote()
                .getSendMessage(phone, yzm, datetime, sign)
                .subscribe(new ApiObserver<SendMessageBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(SendMessageBean sendMessageBean) {
                        SendMessageBean.DataBean data = sendMessageBean.getData();
                        if (data != null) {
                            mView.sendMessageSuccess(data);
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
    public void login(String login_addr, String phone_info, String app_version, int login_type,String phone, String yzm, String openid, String nickname, String logo) {
    DataManager.getFromRemote().login(login_addr,phone_info,app_version,login_type,phone,yzm,openid,nickname,logo)
            .subscribe(new ApiObserver<LoginBean>(mView,this) {
                @Override
                protected void doOnSuccess(LoginBean loginBean) {
                    LoginBean.DataBean data = loginBean.getData();
                    if (data!=null){
                        mView.loginSuccess(data);
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
    public void market() {
        DataManager.getFromRemote()
                .market()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ApiObserver<MarketBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MarketBean marketBean) {
                        MarketBean.DataBean data = marketBean.getData();
                        if (data!=null){
                            mView.marketSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Log.d("LoginPresenter", message);
                        return true;
                    }
                });
    }

    @Override
    public void push(String user_id, String user_token, String device, String jpush_rid) {
        DataManager.getFromRemote().push(user_id, user_token, device, jpush_rid)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.pushSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
