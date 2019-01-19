package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.LikeBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;

/**
 * yo秀详情的契约类
 */
public interface NewYoXiuDetailContract {
    interface View extends IBaseView{
        //获取详情成功
        void onDetailSuccess(YoXiuDetailBean.DataBean data);
        //获取评论集合成功
        void onCommentListSuccess(CommentBean.DataBean data);
        //添加评论
        void onAddCommentSuccess(BaseBean baseBean);
        //添加关注
        void onAddAttentionSuccess(AttentionBean.DataBean data);

        void onDeleteCollectionSuccess(BaseBean baseBean);
        void onAddLikeSuccess(LikeBean baseBean);
        void onReportSuccess(BaseBean baseBean);
    }
    interface Presenter extends IBasePresenter{
        void getDetail(String user_id, String user_token, int id);
        void getCommentList(String user_id, String user_token, int page, int yo_id, int comment_id);
        void addComment(String user_id, String user_token, int comment_id, int yo_id, String content);
        void addAttention(String user_id, String user_token, int target_id);
        void deleteCollection(String user_id, String user_token, int id);
        void addLike(String user_id, String user_token, int id,int comment_id);
        void report(String user_id, String user_token, int id,int comment_id,String content);
    }
}
