package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.AttentionsBean;

public interface AttentionsContract {

    interface View extends IBaseView {
        void getAttentionsSuccess(AttentionsBean attentionsBean);
    }

    interface Presenter extends IBasePresenter {

        void getAttentions(String user_id, String user_token,String his_id, String page, String page_size);
    }
}
