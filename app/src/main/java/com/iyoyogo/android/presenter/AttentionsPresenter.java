package com.iyoyogo.android.presenter;

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

    public AttentionsPresenter(AttentionsContract.View mView) {
        super(mView);
    }

    @Override
    public void getAttentions(String user_id, String user_token, String his_id, String page, String page_size) {
        DataManager.getFromRemote().setAttentions(user_id, user_token, his_id, page, page_size)
                .subscribe(new ApiObserver<AttentionsBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionsBean attentionsBean) {
                        mView.getAttentionsSuccess(attentionsBean);
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
        DataManager.getFromRemote().addAttention1(user_id, user_token,target_id)
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
