package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AttentionsBean;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.contract.AttentionsContract;
import com.iyoyogo.android.contract.CommendAttentionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class AttentionsPresenter extends BasePresenter<AttentionsContract.View> implements AttentionsContract.Presenter {

    public AttentionsPresenter(Context context,AttentionsContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getAttentions(Context context,String user_id, String user_token, String his_id, String page, String page_size) {
        DataManager.getFromRemote().setAttentions(context,user_id, user_token, his_id, page, page_size)
                .subscribe(new ApiObserver<AttentionsBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionsBean attentionsBean) {
                        AttentionsBean.DataBean data = attentionsBean.getData();
                        if (data != null) {
                            mView.getAttentionsSuccess(attentionsBean);
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
                        mView.addAttentionSuccess(attentionBean);
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
