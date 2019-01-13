package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
/**
 * 编辑收藏夹的契约类
 */
public interface EditCollectionContract {
    interface View extends IBaseView {
        void createFolderSuccess(BaseBean baseBean);

        void deleteFolderSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {
        void createFolder(String user_id, String user_token, String name, int open, String id);

        void deleteFolder(String user_id, String user_token, int[] folder_ids);
    }
}
