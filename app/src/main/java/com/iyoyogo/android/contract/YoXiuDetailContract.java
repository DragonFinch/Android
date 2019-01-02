package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
/**
 * yo秀详情的契约类
 */
public interface YoXiuDetailContract {
    interface View extends IBaseView{
        //获取详情成功
        void getDetailSuccess(YoXiuDetailBean.DataBean data);
        //获取评论集合成功
        void getCommentListSuccess(CommentBean.DataBean data);
        //添加评论
        void addCommentSuccess(BaseBean baseBean);
        //添加关注
        void addAttentionSuccess(AttentionBean.DataBean data);
        void deleteAttentionSuccess(BaseBean baseBean);
        //创建收藏夹
        void createFolderSuccess(BaseBean baseBean);
        //获取收藏夹列表
        void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean);
        //添加收藏
        void addCollectionSuccess(AddCollectionBean.DataBean data);
        void deleteCollectionSuccess(BaseBean baseBean);
    }
    interface Presenter extends IBasePresenter{
        void getDetail(String user_id,String user_token,int id);
        void getCommentList(String user_id,String user_token, int page,int yo_id, int comment_id);
        void addComment(String user_id,String user_token, int comment_id,int yo_id, String content);
        void addAttention(String user_id,String user_token,int target_id);
        void deleteAttention(String user_id,String user_token,int id);
        void getCollectionFolder(String user_id,String user_token);
        void createCollectionFolder(String user_id, String user_token,String name, int open, String id);
        void addCollection(String user_id, String user_token, int folder_id, int yo_id);
        void deleteCollection(String user_id, String user_token, int id);
    }
}
