package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;

public interface CommendAttentionContract {
    /**
     * 首页数据的契约类
     */
    interface View extends IBaseView {
        void getCommendAttentionSuccess(CommendAttentionBean commendAttentionBean);
        void addAttentionSuccess(AttentionBean attentionBean);
        void deleteAttention(AttentionBean attentionBean);
    }

    interface Presenter extends IBasePresenter {

        void getCommendAttention(Context context, String user_id, String user_token);
        void addAttention1(Context context,String user_id, String user_token, String target_id);
        void deleteAttention(Context context,String user_id, String user_token, String target_id);

    }
}
