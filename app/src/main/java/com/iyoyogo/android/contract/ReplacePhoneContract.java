package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;

public interface ReplacePhoneContract {
    interface View extends IBaseView{
        void replacePhoneSuccess(BaseBean baseBean);
        void sendMessageSuccess(BaseBean data);
    }
    interface Presenter extends IBasePresenter{
        void replacePhone(String user_id,String user_token,String phone,String yzm);
        void sendMessage(String phone, String yzm, String datetime, String sign);
    }
}
