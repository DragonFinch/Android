package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.LikeBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.NewYoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class NewYoXiuDetailPresenter extends BasePresenter<NewYoXiuDetailContract.View> implements NewYoXiuDetailContract.Presenter {
    public NewYoXiuDetailPresenter(Context context,NewYoXiuDetailContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getDetail(Context context,String user_id, String user_token, int id) {
        DataManager.getFromRemote()
                .getDetail(context,user_id, user_token, id)
                .subscribe(new ApiObserver<YoXiuDetailBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(YoXiuDetailBean yoXiuDetailBean) {
                        YoXiuDetailBean.DataBean data = yoXiuDetailBean.getData();
                        if (data != null) {
                            mView.onDetailSuccess(data);
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
    public void getCommentList(Context context,String user_id, String user_token, int page, int yo_id, int comment_id) {
        DataManager.getFromRemote()
                .getComment(context,user_id, user_token, page, yo_id, comment_id)
                .subscribe(new ApiObserver<CommentBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CommentBean commentBean) {
                        CommentBean.DataBean data = commentBean.getData();
                        if (data != null) {
                            mView.onCommentListSuccess(data);
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
    public void addComment(Context context,String user_id, String user_token, int comment_id, int yo_id, String content) {
        DataManager.getFromRemote()
                .addComment(context,user_id, user_token, comment_id, yo_id, content)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.onAddCommentSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

    }

    @Override
    public void addAttention(Context context,String user_id, String user_token, int target_id) {
        DataManager.getFromRemote().addAttention(context,user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        AttentionBean.DataBean data = attentionBean.getData();
                        if (data != null) {
                            mView.onAddAttentionSuccess(data);
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
    public void deleteCollection(Context context,String user_id, String user_token, int id) {
        DataManager.getFromRemote().deleteCollection(user_id, user_token, id)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.onDeleteCollectionSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void addLike(Context context,String user_id, String user_token, int id, int comment_id) {
        DataManager.getFromRemote().praise(context,user_id, user_token, id, comment_id)
                .subscribe(new ApiObserver<LikeBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(LikeBean baseBean) {
                        mView.onAddLikeSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void report(Context context,String user_id, String user_token, int id, int comment_id, String content) {
        DataManager.getFromRemote().report(context,user_id, user_token, id, comment_id, content)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.onReportSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }


}
