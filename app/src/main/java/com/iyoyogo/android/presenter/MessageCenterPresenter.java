package com.iyoyogo.android.presenter;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.message.MessageCenterBean;
import com.iyoyogo.android.contract.MessageCenterContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MessageCenterPresenter extends BasePresenter<MessageCenterContract.View> implements MessageCenterContract.Presenter {

    public MessageCenterPresenter(MessageCenterContract.View mView) {
        super(mView);
    }

    @Override
    public void getMessageCenter(String user_id, String user_token) {
        DataManager.getFromRemote().messageCenter(user_id, user_token)
                .subscribe(new ApiObserver<MessageCenterBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(MessageCenterBean messageCenterBean) {
                        MessageCenterBean.DataBean data = messageCenterBean.getData();
                        if (data!=null){
                            mView.getMessageCenterSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        return true;
                    }
                });
    }
}
