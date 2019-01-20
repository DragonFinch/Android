package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.contract.AddCollectionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class AddCollectionPresenter extends BasePresenter<AddCollectionContract.View> implements AddCollectionContract.Presenter {

    public AddCollectionPresenter(Context context, AddCollectionContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getAddCollection(Context context,String user_id, String user_token, String page, String page_size) {
        DataManager.getFromRemote().setAddCollection(context,user_id, user_token, page, page_size)
                .subscribe(new ApiObserver<AddCollectionBean1>(mView, this) {
                    @Override
                    protected void doOnSuccess(AddCollectionBean1 addCollectionBean1) {
                        mView.getAddCollectionSuccess(addCollectionBean1);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void addAttention(Context context,String user_id, String user_token, String target_id) {
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
