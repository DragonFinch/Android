package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.contract.YoJiDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoJiDetailPresenter extends BasePresenter<YoJiDetailContract.View> implements YoJiDetailContract.Presenter {
    public YoJiDetailPresenter(YoJiDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void getYoJiDetail(String user_id, String user_token, int yo_id) {
        DataManager.getFromRemote().getYoJiDetail(user_id, user_token, yo_id)
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
    public void getCollectionFolder(String user_id, String user_token) {
        DataManager.getFromRemote().getCollectionFolder(user_id, user_token)
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
    public void createCollectionFolder(String user_id, String user_token, String name, int open, String id) {
        DataManager.getFromRemote().create_folder(user_id, user_token, name, open, id)
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
    public void addCollection(String user_id, String user_token, int folder_id, int yo_id) {
        DataManager.getFromRemote()
                .addCollection(user_id, user_token, folder_id, yo_id)
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
    public void deleteCollection(String user_id, String user_token, int id) {
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
