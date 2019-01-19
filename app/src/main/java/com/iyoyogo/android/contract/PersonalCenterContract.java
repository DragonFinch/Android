package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
/**
 * 个人中心的契约类
 */
public interface PersonalCenterContract {
    interface View extends IBaseView {
        void getPersonalCenterSuccess(UserCenterBean.DataBean data);

        void addAttention1(AttentionBean attentionBean);

        void deleteAttention(AttentionBean attentionBean);


    }

    interface Presenter extends IBasePresenter {
        void getPersonalCenter(Context context, String user_id, String user_token, String his_id);

        void addAttention1(Context context, String user_id, String user_token, String target_id);

        void deleteAttention(Context context, String user_id, String user_token, String target_id);

    }
}
