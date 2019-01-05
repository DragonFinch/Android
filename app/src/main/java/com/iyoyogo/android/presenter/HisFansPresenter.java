package com.iyoyogo.android.presenter;


import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.HisFansBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.contract.HisHansContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class HisFansPresenter extends BasePresenter<HisHansContract.View> implements HisHansContract.Presenter {

    public HisFansPresenter(HisHansContract.View mView) {
        super(mView);
    }

    @Override
    public void getHisHans(String user_id, String user_token, String his_id, String page, String page_size) {
        DataManager.getFromRemote().setHisFans(user_id, user_token, his_id, page, page_size)
                .subscribe(new ApiObserver<HisFansBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(HisFansBean hisFansBean) {
                        HisFansBean.DataBean data = hisFansBean.getData();
                        if (data != null) {
                            mView.getHisHansSuccess(hisFansBean);
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
        DataManager.getFromRemote().addAttention1(user_id, user_token, target_id)
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
}
