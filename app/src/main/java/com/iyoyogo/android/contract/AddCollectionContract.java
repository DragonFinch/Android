package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;

/**
 * 添加关注的契约类
 */
public interface AddCollectionContract {

    interface View extends IBaseView {
        void getAddCollectionSuccess(AddCollectionBean1 addCollectionBean1);

        void addAttentionSuccess(AttentionBean attentionBean);

    }

    interface Presenter extends IBasePresenter {

        void getAddCollection(Context context,String user_id, String user_token, String page, String page_size);

        void addAttention(Context context,String user_id, String user_token, String target_id);
    }
}
