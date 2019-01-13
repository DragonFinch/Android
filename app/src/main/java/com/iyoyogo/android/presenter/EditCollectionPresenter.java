package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.EditCollectionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class EditCollectionPresenter extends BasePresenter<EditCollectionContract.View> implements EditCollectionContract.Presenter {
    public EditCollectionPresenter(EditCollectionContract.View mView) {
        super(mView);
    }

    @Override
    public void createFolder(String user_id, String user_token, String name, int open, String id) {
        DataManager.getFromRemote().create_folder(user_id,user_token,name,open,id)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.createFolderSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void deleteFolder(String user_id, String user_token, int[] folder_ids) {
DataManager.getFromRemote().deleteCollectionFolder(user_id,user_token,folder_ids)
        .subscribe(new ApiObserver<BaseBean>(mView,this) {
            @Override
            protected void doOnSuccess(BaseBean baseBean) {
                mView.deleteFolderSuccess(baseBean);
            }

            @Override
            protected boolean doOnFailure(int code, String message) {
                Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
