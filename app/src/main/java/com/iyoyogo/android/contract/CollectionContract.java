package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.MineCollectionBean;

public interface CollectionContract {
    interface View extends IBaseView {
        void getCollectionSuccess(MineCollectionBean mineCollectionBean);

        void deleteCollectionFolderSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {

        void deleteCollectionFolder(String user_id, String user_token, Integer[] folder_ids);

        void getCollectionFold(String user_id, String user_token);
    }
}
