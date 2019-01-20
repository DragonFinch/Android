package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.HisFansBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AttentionsBean;

public interface HisHansContract {

    interface View extends IBaseView {
        void getHisHansSuccess(HisFansBean hisFansBean);

        void addAttentionSuccess(AttentionBean attentionBean);
    }

    interface Presenter extends IBasePresenter {

        void getHisHans(Context context, String user_id, String user_token, String his_id, String page, String page_size);

        void addAttention1(Context context,String user_id, String user_token, String target_id);
    }
}
