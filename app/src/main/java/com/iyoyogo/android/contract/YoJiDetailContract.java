package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;

public interface YoJiDetailContract {
    interface View extends IBaseView {
        void getYoJiDetailSuccess(YoJiDetailBean.DataBean data);

        void getCommentListSuccess(CommentBean.DataBean data);

        void addCommentSuccess(BaseBean baseBean);

        void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean);

        void createFolderSuccess(BaseBean baseBean);


        void addCollectionSuccess(AddCollectionBean.DataBean data);

        void deleteCollectionSuccess(BaseBean baseBean);

        void addAttentionSuccess(AttentionBean.DataBean data);

        void deleteAttentionSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {
        void getYoJiDetail(String user_id, String user_token, int yo_id);

        void addAttention(String user_id, String user_token, int target_id);

        void deleteAttention(String user_id, String user_token, int id);

        void getCommentList(String user_id, String user_token, int page, int yo_id, int comment_id);

        void addComment(String user_id, String user_token, int comment_id, int yo_id, String content);

        void getCollectionFolder(String user_id, String user_token);

        void createCollectionFolder(String user_id, String user_token, String name, int open, String id);

        void addCollection(String user_id, String user_token, int folder_id, int yo_id);

        void deleteCollection(String user_id, String user_token, int id);
    }
}
