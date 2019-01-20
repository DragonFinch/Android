package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.contract.UserAndSecurityContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class UserAndSecurityPresenter extends BasePresenter<UserAndSecurityContract.View> implements UserAndSecurityContract.Presenter {
    public UserAndSecurityPresenter(Context context, UserAndSecurityContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getBindInfo(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
        .getBindInfo(context,user_id,user_token)
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

    @Override
    public void updateBind(Context context,String user_id, String user_token, int type, String openid, String nickname, String logo) {
        DataManager.getFromRemote()
                .update_bind(context,user_id, user_token, type, openid, nickname, logo)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {

                            mView.updateBindSuccess(baseBean);

                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })    ;
    }

    @Override
    public void userBind(String user_id, String user_token, int type, String openid) {
        DataManager.getFromRemote().getUserBind(user_id, user_token,type,openid)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {


                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.userBindSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
