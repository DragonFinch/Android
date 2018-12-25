package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;

import java.util.List;

public interface CollectionFolderContentContract {
    interface View extends IBaseView {
        void getCollectionFolderContentSuccess(List<CollectionFolderContentBean.DataBean.ListBean> list);
    }

    interface Presenter extends IBasePresenter {
        void getCollectionFolderContent(String user_id, String user_token, int folder_id, int page);
    }
}
