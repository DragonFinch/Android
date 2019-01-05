package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.contract.CollectionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter {
    public CollectionPresenter(CollectionContract.View mView) {
        super(mView);
    }

    @Override
    public void deleteCollectionFolder(String user_id, String user_token, Integer[] folder_ids) {
        DataManager.getFromRemote().deleteCollectionFolder(user_id, user_token, folder_ids)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        int code = baseBean.getCode();
                        if (code == 200) {
                            mView.deleteCollectionFolderSuccess(baseBean);
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
    public void getCollectionFold(String user_id, String user_token) {
        DataManager.getFromRemote().getMineCollection(user_id, user_token)
                .subscribe(new ApiObserver<MineCollectionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MineCollectionBean mineCollectionBean) {
                        int code = mineCollectionBean.getCode();
                        if (code == 200) {
                            mView.getCollectionSuccess(mineCollectionBean);
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
    public void getHisCollectionFold(String user_id, String user_token, String his_id) {
        DataManager.getFromRemote().getHisCollection(user_id, user_token, his_id)
                .subscribe(new ApiObserver<MineCollectionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MineCollectionBean mineCollectionBean) {
                        int code = mineCollectionBean.getCode();
                        if (code == 200) {
                            mView.getCollectionSuccess(mineCollectionBean);
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
