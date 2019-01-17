package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;
import com.iyoyogo.android.contract.MessageContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class MessagePresenter extends BasePresenter<MessageContract.View> implements MessageContract.Presenter {
    public MessagePresenter(MessageContract.View mView) {
        super(mView);
    }


    @Override
    public void getMessage(String user_id, String user_token, int type, int page) {
        DataManager.getFromRemote().getMessage(user_id, user_token, type, page)
                .subscribe(new ApiObserver<MessageBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MessageBean messageBean) {
                        List<MessageBean.DataBean.ListBean> list = messageBean.getData().getList();
                        if (list!=null){
                            mView.getMessageSuccess(list);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        return true;
                    }
                });
    }

    @Override
    public void readMessage(String user_id, String user_token, String message_id) {
        DataManager.getFromRemote().readMessage(user_id,user_token,message_id)
                .subscribe(new ApiObserver<ReadMessage>(mView,this) {
                    @Override
                    protected void doOnSuccess(ReadMessage readMessage) {
                        ReadMessage.DataBean data = readMessage.getData();
                        if (data!=null){
                            mView.readMessageSuccess(data);
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
}
