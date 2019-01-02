package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.contract.AddCollectionContract;
import com.iyoyogo.android.contract.CommendAttentionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class CommendAttentionPresenter extends BasePresenter<CommendAttentionContract.View> implements CommendAttentionContract.Presenter {

    public CommendAttentionPresenter(CommendAttentionContract.View mView) {
        super(mView);
    }

    @Override
    public void getCommendAttention(String user_id, String user_token) {
        DataManager.getFromRemote().setCommendAttention(user_id, user_token)
                .subscribe(new ApiObserver<CommendAttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CommendAttentionBean commendAttentionBean) {
                        mView.getCommendAttentionSuccess(commendAttentionBean);
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
