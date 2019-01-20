package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.contract.CommendAttentionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class CommendAttentionPresenter extends BasePresenter<CommendAttentionContract.View> implements CommendAttentionContract.Presenter {

    public CommendAttentionPresenter(Context context,CommendAttentionContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getCommendAttention(Context context,String user_id, String user_token) {
        DataManager.getFromRemote().setCommendAttention(context,user_id, user_token)
                .subscribe(new ApiObserver<CommendAttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CommendAttentionBean commendAttentionBean) {
                        CommendAttentionBean.DataBean data = commendAttentionBean.getData();
                        if (data != null) {
                            mView.getCommendAttentionSuccess(commendAttentionBean);
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
        DataManager.getFromRemote().addAttention1(context,user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        AttentionBean.DataBean data = attentionBean.getData();
                        if (data != null) {
                            mView.addAttentionSuccess(attentionBean);
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
        DataManager.getFromRemote().addAttention1(context,user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        mView.addAttentionSuccess(attentionBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
