package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;

public interface BindPhoneContract {
    interface View extends IBaseView {
        void sendMessageSuccess(SendMessageBean.DataBean data);

        void loginSuccess(LoginBean.DataBean data);
    }

    interface Presenter extends IBasePresenter {
        void sendMessage(String phone, String yzm, String datetime, String sign);

        /**
         * @param login_addr
         * @param phone_info
         * @param app_veresion
         * @param login_type
         * @param phone
         * @param yzm
         * @param openid
         * @param nickname
         * @param logo
         */
        void login(String login_addr, String phone_info, String app_veresion, int login_type, String phone, String yzm, String openid, String nickname, String logo);

    }
}
