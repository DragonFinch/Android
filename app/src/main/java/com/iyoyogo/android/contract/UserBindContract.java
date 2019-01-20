package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;

public interface UserBindContract {
    interface View extends IBaseView {
        void getUserBindSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {
        void getUserBind(Context context, String user_id, String user_token, int type, String openid);
    }
}
