package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.AttentionsBean;

public interface AttentionsContract {

    interface View extends IBaseView {
        void getAttentionsSuccess(AttentionsBean attentionsBean);

        void addAttentionSuccess(AttentionBean attentionBean);
    }

    interface Presenter extends IBasePresenter {

        void getAttentions(String user_id, String user_token, String his_id, String page, String page_size);

        void addAttention1(String user_id, String user_token, String target_id);
    }
}
