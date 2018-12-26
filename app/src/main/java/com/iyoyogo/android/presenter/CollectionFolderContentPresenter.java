package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;
import com.iyoyogo.android.contract.CollectionFolderContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class CollectionFolderContentPresenter extends BasePresenter<CollectionFolderContentContract.View> implements CollectionFolderContentContract.Presenter {
    public CollectionFolderContentPresenter(CollectionFolderContentContract.View mView) {
        super(mView);
    }

    @Override
    public void getCollectionFolderContent(String user_id, String user_token, int folder_id, int page) {
        DataManager.getFromRemote()
                .getContent(user_id, user_token, folder_id, page)
                .subscribe(new ApiObserver<CollectionFolderContentBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CollectionFolderContentBean collectionFolderContentBean) {
                        List<CollectionFolderContentBean.DataBean.ListBean> list = collectionFolderContentBean.getData().getList();
                        if (list != null) {
                            mView.getCollectionFolderContentSuccess(list);
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
    public void removeCollectionContent(String user_id, String user_token, Integer[] record_ids) {
        DataManager.getFromRemote().deleteCollection(user_id, user_token, record_ids)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.removeCollectionContentSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void moveCollectionFolder(String user_id, String user_token, Integer[] record_ids, int folder_id) {
        DataManager.getFromRemote().moveCollectionFolder(user_id, user_token, record_ids, folder_id)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.moveCollectionFolderSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void getCollectionFolder(String user_id, String user_token) {
        DataManager.getFromRemote()
                .getCollectionFolder(user_id, user_token)
                .subscribe(new ApiObserver<CollectionFolderBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CollectionFolderBean collectionFolderBean) {
                        CollectionFolderBean.DataBean data = collectionFolderBean.getData();
                        if (data!=null){
                            mView.getCollectionFolderSuccess(data);
                        }

                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        return true;
                    }
                });
    }
}
