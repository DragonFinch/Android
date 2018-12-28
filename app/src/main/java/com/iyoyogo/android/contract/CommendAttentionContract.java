package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;

public interface CommendAttentionContract {

    interface View extends IBaseView {
        void getCommendAttentionSuccess(CommendAttentionBean commendAttentionBean);
    }

    interface Presenter extends IBasePresenter {

        void getCommendAttention(String user_id, String user_token);
    }
}
