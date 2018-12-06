package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;

public interface MineSettingContract {
    interface View extends IBaseView{
        void logoutSuccess();
    }
    interface Presenter extends IBasePresenter{
        void logout(String user_id,String user_token,String addr,String phone_info,String app_version);
    }
}
