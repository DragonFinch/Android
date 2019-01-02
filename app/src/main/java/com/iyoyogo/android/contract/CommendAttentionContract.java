package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;

public interface CommendAttentionContract {

    interface View extends IBaseView {
        void getCommendAttentionSuccess(CommendAttentionBean commendAttentionBean);
        void addAttentionSuccess(AttentionBean attentionBean);
    }

    interface Presenter extends IBasePresenter {

        void getCommendAttention(String user_id, String user_token);
        void addAttention(String user_id, String user_token, int target_id);

    }
}
