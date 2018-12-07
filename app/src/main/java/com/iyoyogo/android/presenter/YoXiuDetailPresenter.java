package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoXiuDetailPresenter extends BasePresenter<YoXiuDetailContract.View> implements YoXiuDetailContract.Presenter {
    public YoXiuDetailPresenter(YoXiuDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void getDetail(String user_id, String user_token, int id) {
        DataManager.getFromRemote()
                .getDetail(user_id, user_token, id)
                .subscribe(new ApiObserver<YoXiuDetailBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(YoXiuDetailBean yoXiuDetailBean) {
                        YoXiuDetailBean.DataBean data = yoXiuDetailBean.getData();
                        if (data != null) {
                            mView.getDetailSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void getCommentList(String user_id, String user_token, int page, int yo_id, int comment_id) {
        DataManager.getFromRemote()
                .getComment(user_id, user_token, page, yo_id, comment_id)
                .subscribe(new ApiObserver<CommentBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CommentBean commentBean) {
                        CommentBean.DataBean data = commentBean.getData();
                        if (data != null) {
                            mView.getCommentListSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void addComment(String user_id, String user_token, int comment_id, int yo_id, String content) {
        DataManager.getFromRemote()
                .addComment(user_id, user_token, comment_id, yo_id, content)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.addCommentSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

    }

    @Override
    public void addAttention(String user_id, String user_token, int target_id) {
        DataManager.getFromRemote().addAttention(user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        AttentionBean.DataBean data = attentionBean.getData();
                        if (data != null) {
                            mView.addAttentionSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void deleteAttention(String user_id, String user_token, int id) {
        DataManager.getFromRemote().deleteAttention(user_id, user_token, id)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {


                        mView.deleteAttentionSuccess(baseBean);

                    }
                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }


}
