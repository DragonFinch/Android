package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.contract.YoJiDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoJiDetailPresenter extends BasePresenter<YoJiDetailContract.View> implements YoJiDetailContract.Presenter {
    public YoJiDetailPresenter(Context context,YoJiDetailContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getYoJiDetail(Context context,String user_id, String user_token, int yo_id) {
        DataManager.getFromRemote().getYoJiDetail(context,user_id, user_token, yo_id)
                .subscribe(new ApiObserver<YoJiDetailBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(YoJiDetailBean yoJiDetailBean) {
                        YoJiDetailBean.DataBean data = yoJiDetailBean.getData();
                        if (data != null) {
                            mView.getYoJiDetailSuccess(data);
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
    public void addAttention(Context context,String user_id, String user_token, int target_id) {
        DataManager.getFromRemote().addAttention(context,user_id, user_token, target_id)
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
    public void deleteAttention(Context context,String user_id, String user_token, int id) {
        DataManager.getFromRemote().deleteAttention(context,user_id, user_token, id)
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

    @Override
    public void getCommentList(Context context,String user_id, String user_token, int page, int yo_id, int comment_id) {
        DataManager.getFromRemote()
                .getComment(context,user_id, user_token, page, yo_id, comment_id)
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
    public void addComment(Context context,String user_id, String user_token, int comment_id, int yo_id, String content) {
        DataManager.getFromRemote()
                .addComment(context,user_id, user_token, comment_id, yo_id, content)
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
    public void getCollectionFolder(Context context,String user_id, String user_token) {
        DataManager.getFromRemote().getCollectionFolder(context,user_id, user_token)
                .subscribe(new ApiObserver<CollectionFolderBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(CollectionFolderBean collectionFolderBean) {
                        CollectionFolderBean.DataBean data = collectionFolderBean.getData();
                        if (data != null) {
                            mView.getCollectionFolderSuccess(data);
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
    public void createCollectionFolder(Context context,String user_id, String user_token, String name, int open, String id) {
        DataManager.getFromRemote().create_folder(context,user_id, user_token, name, open, id)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.createFolderSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void addCollection(Context context,String user_id, String user_token, int folder_id, int yo_id) {
        DataManager.getFromRemote()
                .addCollection(context,user_id, user_token, folder_id, yo_id)
                .subscribe(new ApiObserver<AddCollectionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AddCollectionBean addCollectionBean) {
                        AddCollectionBean.DataBean data = addCollectionBean.getData();
                        if (data != null) {
                            mView.addCollectionSuccess(data);
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
                        mView.deleteCollectionSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
