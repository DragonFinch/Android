package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;

public interface YoXiuDetailContract {
    interface View extends IBaseView{
        void getDetailSuccess(YoXiuDetailBean.DataBean data);
        void getCommentListSuccess(CommentBean.DataBean data);
        void addCommentSuccess(BaseBean baseBean);
    }
    interface Presenter extends IBasePresenter{
        void getDetail(String user_id,String user_token,int id);
        void getCommentList(String user_id,String user_token, int page,int yo_id, int comment_id);
        void addComment(String user_id,String user_token, int comment_id,int yo_id, String content);
    }
}
