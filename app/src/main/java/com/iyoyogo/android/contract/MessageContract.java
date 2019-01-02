package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;

import java.util.List;
/**
 * 消息详情的契约类
 */
public interface MessageContract {
    interface View extends IBaseView {
        void getMessageSuccess(List<MessageBean.DataBean.ListBean> list);
        void readMessageSuccess(ReadMessage.DataBean data);
    }

    interface Presenter extends IBasePresenter {
        void getMessage(String user_id,String user_token,int type,int page);
        void readMessage(String user_id,String user_token,String message_id);
    }
}
