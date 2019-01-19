package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
/**
 * 替换手机号的契约类
 */
public interface ReplacePhoneContract {
    interface View extends IBaseView{
        void replacePhoneSuccess(BaseBean baseBean);
        void sendMessageSuccess(BaseBean data);
    }
    interface Presenter extends IBasePresenter{
        void replacePhone(Context context, String user_id, String user_token, String phone, String yzm);
        void sendMessage(Context context, String phone, String yzm, String datetime, String sign);
    }
}
