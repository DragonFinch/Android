package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.contract.AddCollectionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class AddCollectionPresenter extends BasePresenter<AddCollectionContract.View> implements AddCollectionContract.Presenter {

    public AddCollectionPresenter(AddCollectionContract.View mView) {
        super(mView);
    }

    @Override
    public void getAddCollection(String user_id, String user_token, String page, String page_size) {
        DataManager.getFromRemote().setAddCollection(user_id, user_token, page, page_size)
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
}
