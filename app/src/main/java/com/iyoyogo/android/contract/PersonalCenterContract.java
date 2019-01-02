package com.iyoyogo.android.contract;

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

        void addAttention1(AttentionBean.DataBean data);

    }

    interface Presenter extends IBasePresenter {
        void getPersonalCenter(String user_id, String user_token, String his_id);

        void addAttention1(String user_id, String user_token, String target_id);
    }
}
