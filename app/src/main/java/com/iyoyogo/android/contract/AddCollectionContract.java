package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;

public interface AddCollectionContract {

    interface View extends IBaseView {
        void getAddCollectionSuccess(AddCollectionBean1 addCollectionBean1);
    }

    interface Presenter extends IBasePresenter {

        void getAddCollection(String user_id, String user_token, String page, String page_size);
    }
}
