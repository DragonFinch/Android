package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.comment.CommentBean;
/**
 * 回复评论的契约类
 */
public interface ReplyDiscussContract {
    interface View extends IBaseView{
        void getCommentListSuccess(CommentBean.DataBean data);

        void addCommentSuccess(BaseBean baseBean);
    }
    interface Presenter extends IBasePresenter{
        void getCommentList(Context context, String user_id, String user_token, int page, int yo_id, int comment_id);

        void addComment(Context context, String user_id, String user_token, int comment_id, int yo_id, String content);
    }
}
