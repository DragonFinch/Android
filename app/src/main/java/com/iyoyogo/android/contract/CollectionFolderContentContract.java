package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;

import java.util.List;
/**
 * 我的收藏的契约类
 */
public interface CollectionFolderContentContract {
    interface View extends IBaseView {
        void getCollectionFolderContentSuccess(List<CollectionFolderContentBean.DataBean.ListBean> list);
        void removeCollectionContentSuccess(BaseBean baseBean);
        void moveCollectionFolderSuccess(BaseBean baseBean);
        void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean);
    }

    interface Presenter extends IBasePresenter {
        void getCollectionFolderContent(Context context,String user_id, String user_token, int folder_id, int page);
        void removeCollectionContent(Context context,String user_id,String user_token,Integer[]record_ids);
        void moveCollectionFolder(Context context,String user_id,String user_token,Integer[]record_ids,int folder_id);
        void getCollectionFolder(Context context,String user_id,String user_token);
    }
}
